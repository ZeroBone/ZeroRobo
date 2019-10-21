package net.zerobone.zerorobo.utils;

/**
 * Represents a bullet. This is returned from
 * {@link SimpleRobotBehavior#fireBullet(double)} and all the bullet-related
 * events.
 *
 * @see SimpleRobotBehavior#fireBullet(double)
 * @see BulletHitEvent
 * @see BulletMissedEvent
 * @see BulletHitBulletEvent
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class Bullet {

    private robocode.Bullet bullet;

    /*
     * Called by the game to create a new {@code net.zerobone.zerorobo.utils.Bullet} object.
     *
     * @param bullet The internal net.zerobone.zerorobo.utils.Bullet
     */
    public Bullet(robocode.Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return bullet.equals(((Bullet) obj).bullet);
    }

    @Override
    public int hashCode() {
        return bullet.hashCode();
    }

    /**
     * Returns the direction the bullet is/was heading, in degrees
     * (0 &lt;= getHeading() &lt; 360). This is not relative to the direction you are
     * facing.
     *
     * @return the direction the bullet is/was heading, in degrees
     */
    public double getHeading() {
        return bullet.getHeading();
    }

    /**
     * Returns the name of the robot that fired this bullet.
     *
     * @return the name of the robot that fired this bullet
     */
    public String getName() {
        return bullet.getName();
    }

    /**
     * Returns the power of this bullet.
     * <p>
     * The bullet will do (4 * power) damage if it hits another robot.
     * If power is greater than 1, it will do an additional 2 * (power - 1)
     * damage. You will get (3 * power) back if you hit the other robot.
     *
     * @return the power of the bullet
     */
    public double getPower() {
        return bullet.getPower();
    }

    /**
     * Returns the velocity of this bullet. The velocity of the bullet is
     * constant once it has been fired.
     *
     * @return the velocity of the bullet
     */
    public double getVelocity() {
        return bullet.getVelocity();
    }

    /**
     * Returns the name of the robot that this bullet hit, or {@code null} if
     * the bullet has not hit a robot.
     *
     * @return the name of the robot that this bullet hit, or {@code null} if
     *         the bullet has not hit a robot.
     */
    public String getVictim() {
        return bullet.getVictim();
    }

    /**
     * Returns the X position of the bullet.
     *
     * @return the X position of the bullet
     */
    public double getX() {
        return bullet.getX();
    }

    /**
     * Returns the Y position of the bullet.
     *
     * @return the Y position of the bullet
     */
    public double getY() {
        return bullet.getY();
    }

    /**
     * Checks if this bullet is still active on the battlefield.
     *
     * @return {@code true} if the bullet is still active on the battlefield;
     *         {@code false} otherwise
     */
    public boolean isActive() {
        return bullet.isActive();
    }

}