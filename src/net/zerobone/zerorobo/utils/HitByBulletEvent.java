package net.zerobone.zerorobo.utils;

/**
 * A net.zerobone.zerorobo.utils.HitByBulletEvent is sent to
 * {@link SimpleRobotBehavior#getHitByBulletEvents()} when your robot has been
 * hit by a bullet. You can use the information contained in this event to
 * determine what to do.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public final class HitByBulletEvent {
    private robocode.HitByBulletEvent ev;

    /**
     * Called by the game to create a new {@link HitByBulletEvent} object.
     *
     * @param ev the internal Robocode event
     */
    HitByBulletEvent(robocode.HitByBulletEvent ev) {
        this.ev = ev;
    }

    /**
     * Returns the bearing to the bullet, relative to your robot's heading,
     * in degrees (-180 &lt; getBearing() &lt;= 180).
     * <p>
     * If you were to {@code turn(event.getBearing())}, you would be facing the
     * direction the bullet came from. The calculation used here is:
     * (bullet's heading in degrees + 180) - (your heading in degrees)
     *
     * @return the bearing to the bullet, in degrees
     */
    public double getBearing() {
        return ev.getBearing();
    }

    /**
     * Returns the bullet that hit your robot.
     *
     * @return the bullet that hit your robot
     */
    public Bullet getBullet() {
        return new Bullet(ev.getBullet());
    }

    /**
     * Returns the heading of the bullet when it hit you, in degrees
     * (0 &lt;= getHeading() &lt; 360).
     * <p>
     * Note: This is not relative to the direction you are facing. The robot
     * that fired the bullet was in the opposite direction of getHeading() when
     * it fired the bullet.
     *
     * @return the heading of the bullet, in degrees
     */
    public double getHeading() {
        return ev.getHeading();
    }

    /**
     * Returns the name of the robot that fired the bullet.
     *
     * @return the name of the robot that fired the bullet
     */
    public String getName() {
        return ev.getName();
    }

    /**
     * Returns the power of this bullet. The damage you take (in fact, already
     * took) is 4 * power, plus 2 * (power-1) if power &gt; 1. The robot that fired
     * the bullet receives 3 * power back.
     *
     * @return the power of the bullet
     */
    public double getPower() {
        return ev.getPower();
    }

    /**
     * Returns the velocity of this bullet.
     *
     * @return the velocity of the bullet
     */
    public double getVelocity() {
        return ev.getVelocity();
    }

}