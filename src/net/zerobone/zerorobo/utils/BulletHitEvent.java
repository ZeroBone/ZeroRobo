package net.zerobone.zerorobo.utils;

/**
 * This event is returned by {@link SimpleRobotBehavior#getBulletHitEvents()}
 * when one of your bullets has hit another robot.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public final class BulletHitEvent {
    private robocode.BulletHitEvent ev;

    /**
     * Called by the game to create a new {@link BulletHitEvent} object.
     *
     * @param ev the internal event
     */
    BulletHitEvent(robocode.BulletHitEvent ev) {
        this.ev = ev;
    }

    /**
     * Returns the bullet of yours that hit the robot.
     *
     * @return the bullet that hit the robot
     */
    public Bullet getBullet() {
        return new Bullet(ev.getBullet());
    }

    /**
     * Returns the remaining energy of the robot your bullet has hit (after the
     * damage done by your bullet).
     *
     * @return energy the remaining energy of the robot that your bullet has hit
     */
    public double getEnergy() {
        return ev.getEnergy();
    }

    /**
     * Returns the name of the robot your bullet hit.
     *
     * @return the name of the robot your bullet hit.
     */
    public String getName() {
        return ev.getName();
    }
}