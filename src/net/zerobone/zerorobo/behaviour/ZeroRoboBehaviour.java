package net.zerobone.zerorobo.behaviour;

import net.zerobone.zerorobo.ZeroRobo;
import net.zerobone.zerorobo.utils.*;
import net.zerobone.zerorobo.utils.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ZeroRoboBehaviour extends SimpleRobotBehaviour {

    private static final IntPoint quadLayout = new IntPoint(3, 3);

    private Point targetPosition = null;
    private IntPoint targetQuad = null;

    private String trackingTankName = null;

    private int lostEnemyCounter;

    public ZeroRoboBehaviour(ZeroRobo robo) {
        super(robo);
    }

    @Override
    protected void start() {

        // set colors

        Color bodyColor = new Color(255, 35, 33);
        Color gunColor = new Color(240, 235, 170);
        Color radarColor = new Color(105, 0, 213);
        Color bulletColor = new Color(231, 96, 0);
        Color scanArcColor = new Color(57, 0,111);

        setColors(bodyColor, gunColor, radarColor, bulletColor, scanArcColor);

    }

    @Override
    protected void execute() {

        for (ScannedRobotEvent event : getScannedRobotEvents()) {
            onRobotScannedEvent(event);
        }

        // double

        // turnRadar(10);
        turnRadar(Double.POSITIVE_INFINITY);

        if (targetPosition != null) {

            // goTo(targetPosition.getX(), targetPosition.getY());
            go(targetPosition.getX(), targetPosition.getY());

            // System.out.println(targetPosition);

            // ahead(Double.POSITIVE_INFINITY);
        }
        else {
            // goTo(getBattleFieldWidth() / 2, getBattleFieldHeight() / 2);
        }

    }

    private void onRobotScannedEvent(ScannedRobotEvent event) {

        if (trackingTankName != null && !trackingTankName.equals(event.getName())) {
            // we are concentrating on tracking another tank
            // so ignore events from other tanks
            return;
        }

        if (trackingTankName == null) {
            trackingTankName = event.getName();
        }

        // we found the target, so reset the counter
        lostEnemyCounter = 0;

        // find the coordinates of the enemy
        Point myPosition = new Point(getX(), getY());
        Point enemyPosition = calculateEnemyPosition(event);

        IntPoint myQuad = getRobotQuad(myPosition);
        IntPoint enemyQuad = getRobotQuad(enemyPosition);

        // System.out.println(myQuad + " " + enemyQuad);

        if (!myQuad.equals(targetQuad) && targetQuad != null) {
            return;
        }

        ArrayList<IntPoint> nextQuads = nextQuad(myQuad, enemyQuad);

        IntPoint nextQuad;

        if (!nextQuads.isEmpty())  {

            for (int i = 0; i < nextQuads.size(); i++) {

                if (nextQuads.get(i).equals(targetQuad)) {
                    nextQuads.remove(i);
                    break;
                }

            }

        }

        if (nextQuads.isEmpty()) {
            nextQuad = new IntPoint(0, 0);
        }
        else {
            nextQuad = nextQuads.get(new Random().nextInt(nextQuads.size()));
        }

        setTargetQuad(nextQuad);

    }

    private void setTargetQuad(IntPoint targetQuad) {

        this.targetQuad = targetQuad;

        targetPosition = getCenterOfQuad(this.targetQuad);

        System.out.println(targetQuad);

    }

    public void onPaint(Graphics2D g) {

        if (targetPosition != null) {

            g.setColor(new Color(0xff, 0, 0, 255));

            // g.drawArc((int)targetPosition.getX(), (int)targetPosition.getY(), 16, 16, 0, 360);
            g.fillRect((int)targetPosition.getX(), (int)targetPosition.getY(), 16, 16);

        }

        g.setColor(new Color(0xff, 253, 6, 0x80));

        for (int y = 0; y < quadLayout.y; y++) {
            for (int x = 0; x < quadLayout.x; x++) {

                Point center = getCenterOfQuad(new IntPoint(x, y));

                g.fillRect((int)center.getX(), (int)center.getY(), 16, 16);

            }
        }

    }

    private Point calculateEnemyPosition(ScannedRobotEvent event) {

        double angle = Math.toRadians((getHeading() + event.getBearing()) % 360);

        return new Point(
            (int)(getX() + Math.sin(angle) * event.getDistance()),
            (int)(getY() + Math.cos(angle) * event.getDistance())
        );

    }

    private IntPoint getRobotQuad(Point robotPosition) {

        double cellWidth = getBattleFieldWidth() / ZeroRoboBehaviour.quadLayout.x;
        double cellHeight = getBattleFieldHeight() / ZeroRoboBehaviour.quadLayout.y;

        return new IntPoint(
            (int)(robotPosition.getX() / cellWidth),
            (int)(robotPosition.getY() / cellHeight)
        );

    }

    private Point getCenterOfQuad(IntPoint quad) {

        int cellWidth = (int)getBattleFieldWidth() / ZeroRoboBehaviour.quadLayout.x;
        int cellHeight = (int)getBattleFieldHeight() / ZeroRoboBehaviour.quadLayout.y;

        return new Point(
            quad.x * cellWidth + cellWidth / 2.,
            quad.y * cellHeight + cellHeight / 2.
        );

    }

    private ArrayList<IntPoint> nextQuad(IntPoint myQuad, IntPoint enemyQuad) {

        ArrayList<IntPoint> possibleQuads = new ArrayList<>();

        IntPoint diff = myQuad.copy();
        diff.subtract(enemyQuad);

        // System.out.println(enemyQuad + " " + quadLayout + " " + myQuad);

        {
            int x = myQuad.x - 1;
            if (x < 0) x = 0;

            int startY = myQuad.y - 1;
            if (startY < 0) startY = 0;

            int maxX = myQuad.x + 1;
            if (maxX >= ZeroRoboBehaviour.quadLayout.x) maxX = ZeroRoboBehaviour.quadLayout.x - 1;

            int maxY = myQuad.y + 1;
            if (maxY >= ZeroRoboBehaviour.quadLayout.y) maxY = ZeroRoboBehaviour.quadLayout.y - 1;

            // System.out.println("maxX = " + maxX + " maxY = " + maxY);

            for (; x <= maxX; x++) {
                for (int y = startY; y <= maxY; y++) {

                    // System.out.println("x = " + x + " y = " + y);

                    // int directionX = x - myQuad.x;
                    // int directionY = y - myQuad.y;

                    /*if (intSignum(directionX) == intSignum(diff.x) && intSignum(directionY) == intSignum(diff.y)) {
                        // we never go in the direction of the enemy
                        continue;
                    }*/

                    IntPoint possibleTarget = new IntPoint(x, y);

                    if (possibleTarget.equals(enemyQuad) || possibleTarget.equals(myQuad)) {
                        continue;
                    }

                    // System.out.println("Adding " + new IntPoint(x, y));

                    possibleQuads.add(possibleTarget);

                }
            }

        }

        System.out.println("nextQuad() ===================");

        for (IntPoint element : possibleQuads) {
            System.out.println(element);
        }

        // System.out.println(possibleQuads.size());

        // return possibleQuads.elementAt(new Random().nextInt(possibleQuads.size()));
        // return possibleQuads.lastElement();
        return possibleQuads;

    }

    private static boolean intSignum(int v) {
        return v > 0;
    }

    private void goTo(double x, double y) {
        /* Transform our coordinates into a vector */
        x -= getX();
        y -= getY();

        /* Calculate the angle to the target position */
        double angleToTarget = Math.atan2(x, y);

        /* Calculate the turn required get there */
        double targetAngle = Utils.normalRelativeAngle(angleToTarget - Math.toRadians(getHeading()));

        /*
         * The Java Hypot method is a quick way of getting the length
         * of a vector. Which in this case is also the distance between
         * our robot and the target location.
         */
        double distance = Math.hypot(x, y);

        /* This is a simple method of performing set front as back */
        double turnAngle = Math.atan(Math.tan(targetAngle));

        turn(Math.toDegrees(turnAngle));
        // setTurnRightRadians(turnAngle);

        if(targetAngle == turnAngle) {
            ahead(distance);
        }
        else {
            ahead(-distance);
        }
    }

    private void go(double x, double y) {
        /* Calculate the difference bettwen the current position and the target position. */
        x = x - getX();
        y = y - getY();

        double headingRadians = Math.toRadians(getHeading());

        /* Calculate the angle relative to the current heading. */
        double goAngle = Utils.normalRelativeAngle(Math.atan2(x, y) - headingRadians);

        /*
         * Apply a tangent to the turn this is a cheap way of achieving back to front turn angle as tangents period is PI.
         * The output is very close to doing it correctly under most inputs. Applying the arctan will reverse the function
         * back into a normal value, correcting the value. The arctan is not needed if code size is required, the error from
         * tangent evening out over multiple turns.
         */
        turn(Math.toDegrees(Math.atan(Math.tan(goAngle))));

        /*
         * The cosine call reduces the amount moved more the more perpendicular it is to the desired angle of travel. The
         * hypot is a quick way of calculating the distance to move as it calculates the length of the given coordinates
         * from 0.
         */
        ahead(Math.cos(goAngle) * Math.hypot(x, y));

    }

}