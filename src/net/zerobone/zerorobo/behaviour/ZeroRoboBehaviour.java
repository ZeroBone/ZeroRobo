package net.zerobone.zerorobo.behaviour;

import net.zerobone.zerorobo.ZeroRobo;
import net.zerobone.zerorobo.utils.*;
import net.zerobone.zerorobo.utils.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ZeroRoboBehaviour extends SimpleRobotBehaviour {

    private double futureX = 0;
    private double futureY = 0;

    private static final IntPoint quadLayout = new IntPoint(4, 3);

    private Point targetPosition = null;
    private IntPoint targetQuad = null;

    private String trackingTankName = null;

    // enemy information
    private Point enemyPosition;
    private double enemyVelocity;
    private double enemyHeading;
    private double distanceToEnemy;

    // private int lostEnemyCounter;

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

            go(targetPosition.getX(), targetPosition.getY());

        }

        if (trackingTankName != null) {
            processShooting();
        }

    }

    private void processShooting() {

        double distance = enemyPosition.subtract(getX(), getY()).length();

        double firePower = Math.min(500 / distance, 3);

        // calculate speed of bullet: 20 and 3 are from the robocode wiki
        double bulletSpeed = 20 - firePower * 3;

        long time = (long)(distanceToEnemy / bulletSpeed);

        futureX = getFutureX((int)enemyPosition.getX(), enemyHeading, enemyVelocity, time);
        futureY = getFutureY((int)enemyPosition.getY(), enemyHeading, enemyVelocity, time);

        double absoluteDegree = absoluteBearing(getX(), getY(), futureX, futureY);

        turnGun(Utils.normalRelativeAngle(absoluteDegree - getGunHeading()));

        if (getGunHeat() <= 1e-5 && Math.abs(getGunTurnRemaining()) < 10) {

            fireBullet(firePower);

        }

    }

    private void updateShooting(ScannedRobotEvent event, Point newEnemyPosition) {

        enemyPosition = newEnemyPosition;

        enemyVelocity = event.getVelocity();

        enemyHeading = event.getHeading();

        distanceToEnemy = event.getDistance();

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
        // lostEnemyCounter = 0;

        // find the coordinates of the enemy
        Point myPosition = new Point(getX(), getY());
        Point newEnemyPosition = calculateEnemyPosition(event);

        updateShooting(event, newEnemyPosition);

        IntPoint myQuad = getRobotQuad(myPosition);
        IntPoint enemyQuad = getRobotQuad(newEnemyPosition);

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

        // System.out.println(targetQuad);

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

        g.setColor(new Color(229, 0, 255, 0x80));

        g.fillArc((int)futureX, (int)futureY, 15, 15, 0, 360);

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

                    IntPoint possibleTarget = new IntPoint(x, y);

                    if (possibleTarget.equals(enemyQuad) || possibleTarget.equals(myQuad)) {
                        continue;
                    }

                    possibleQuads.add(possibleTarget);

                }
            }

        }

        return possibleQuads;

    }

    private void go(double x, double y) {

        // Calculate the difference bettwen the current position and the target position.
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

    private static double getFutureX(int x, double heading, double velocity, long time) {
        return x + Math.sin(Math.toRadians(heading)) * velocity * time;
    }

    private static double getFutureY(int y, double heading, double velocity, long time) {
        return y + Math.cos(Math.toRadians(heading)) * velocity * time;
    }

    // computes the absolute bearing between two points
    private static double absoluteBearing(double x1, double y1, double x2, double y2) {

        double xo = x2-x1;
        double yo = y2-y1;

        // double hyp = Point2D.distance(x1, y1, x2, y2);
        double hyp = Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));

        double arcSin = Math.toDegrees(Math.asin(xo / hyp));
        double bearing = 0;

        if (xo > 0 && yo > 0) { // both pos: lower-Left
            bearing = arcSin;
        } else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
            bearing = 360 + arcSin; // arcsin is negative here, actually 360 - ang
        } else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
            bearing = 180 - arcSin;
        } else if (xo < 0 && yo < 0) { // both neg: upper-right
            bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
        }

        return bearing;

    }

}