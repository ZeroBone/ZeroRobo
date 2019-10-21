package net.zerobone.zerorobo.utils;

import robocode.AdvancedRobot;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a robot that can fight in a Robocode match. Its actual
 * behavior is defined by a {@link SimpleRobotBehavior}. Inherit from this class
 * and set the member {@link #behavior} to use it.
 */
public abstract class SimpleRobot extends AdvancedRobot {

    /**
     * The behavior for this robot. All actions performed by this robot come
     * from here.
     */
    protected SimpleRobotBehavior behavior = null;

    Queue<BulletHitBulletEvent> bulletHitBulletEventQueue = new LinkedList<>();
    Queue<BulletHitEvent> bulletHitEventQueue = new LinkedList<>();
    Queue<BulletMissedEvent> bulletMissedEventQueue = new LinkedList<>();
    Queue<HitByBulletEvent> hitByBulletEventQueue = new LinkedList<>();
    Queue<HitRobotEvent> hitRobotEventQueue = new LinkedList<>();
    Queue<HitWallEvent> hitWallEventQueue = new LinkedList<>();
    Queue<ScannedRobotEvent> scannedRobotEventQueue = new LinkedList<>();

    @Override
    public void run() {

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        behavior.start();
        execute();

        while (true) {
            behavior.execute();
            execute();
        }

    }

    @Override
    public void onBulletHitBullet(robocode.BulletHitBulletEvent ex) {
        bulletHitBulletEventQueue.add(new BulletHitBulletEvent(ex));
    }

    @Override
    public void onBulletHit(robocode.BulletHitEvent ex) {
        bulletHitEventQueue.add(new BulletHitEvent(ex));
    }

    @Override
    public void onBulletMissed(robocode.BulletMissedEvent ex) {
        bulletMissedEventQueue.add(new BulletMissedEvent(ex));
    }

    @Override
    public void onHitByBullet(robocode.HitByBulletEvent ex) {
        hitByBulletEventQueue.add(new HitByBulletEvent(ex));
    }

    @Override
    public void onHitRobot(robocode.HitRobotEvent ex) {
        hitRobotEventQueue.add(new HitRobotEvent(ex));
    }

    @Override
    public void onHitWall(robocode.HitWallEvent ex) {
        hitWallEventQueue.add(new HitWallEvent(ex));
    }

    @Override
    public void onScannedRobot(robocode.ScannedRobotEvent ex) {
        scannedRobotEventQueue.add(new ScannedRobotEvent(ex));
    }
}
