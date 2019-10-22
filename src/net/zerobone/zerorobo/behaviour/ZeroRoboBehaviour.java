package net.zerobone.zerorobo.behaviour;

import net.zerobone.zerorobo.ZeroRobo;
import net.zerobone.zerorobo.utils.ScannedRobotEvent;
import net.zerobone.zerorobo.utils.SimpleRobotBehaviour;

import java.awt.Color;
import java.util.Vector;

public class ZeroRoboBehaviour extends SimpleRobotBehaviour {

    private boolean radarGoingRight;

    private int lostEnemyCounter = 0;
    private String trackingTankName = null;

    public ZeroRoboBehaviour(ZeroRobo robo) {
        super(robo);
    }

    @Override
    protected void start() {

        radarGoingRight = true;

        // colors

        Color bodyColor = new Color(255, 35, 33);
        Color gunColor = new Color(0, 255, 149);
        Color radarColor = new Color(105, 0, 213);
        Color bulletColor = new Color(231, 96, 0);
        Color scanArcColor = new Color(57, 0,111);

        setColors(bodyColor, gunColor, radarColor, bulletColor, scanArcColor);

    }

    @Override
    protected void execute() {

        Vector<ScannedRobotEvent> events = getScannedRobotEvents();

        if (events.size() == 0) {

            lostEnemyCounter++;

            if (lostEnemyCounter > 10) {
                turnRadar(radarGoingRight ? 10 : -10);
            }
            else if (lostEnemyCounter > 2) {
                // try looking slowly for the enemy in the opposite direction
                turnRadar(radarGoingRight ? -2 : 2);
            }

            return;

        }

        for (ScannedRobotEvent event : events) {
            onRobotScannedEvent(event);
        }

    }

    private double optimalTurnDirection(double from, double to, double modulo) {

        if (Math.abs(from - to) > 180) {
            // turning left

            return modulo;
        }

        return -modulo;

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

        double enemyHeading = event.getHeading();

        double radarHeading = getRadarHeading();

        double turnDegree = optimalTurnDirection(radarHeading, enemyHeading, 5 / event.getDistance());

        System.out.println(turnDegree);

        turnRadar(turnDegree);

    }

}