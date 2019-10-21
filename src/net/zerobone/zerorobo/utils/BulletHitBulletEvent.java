package net.zerobone.zerorobo.utils;

/**
 * This event is sent to {@link SimpleRobotBehavior#getBulletHitBulletEvents()}.
 * when one of your bullets has hit another bullet.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public final class BulletHitBulletEvent {
    private robocode.BulletHitBulletEvent ev;

    /**
     * Called by the game to create a new {@link BulletHitBulletEvent} object.
     *
     * @param ev the internal event
     */
    BulletHitBulletEvent(robocode.BulletHitBulletEvent ev) {
        this.ev = ev;
    }

    /**
     * Returns your bullet that hit another bullet.
     *
     * @return your bullet
     */
    public Bullet getBullet() {
        return new Bullet(ev.getBullet());
    }

    /**
     * Returns the bullet that was hit by your bullet.
     *
     * @return the bullet that was hit
     */
    public Bullet getHitBullet() {
        return new Bullet(ev.getHitBullet());
    }

}