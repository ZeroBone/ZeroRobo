package net.zerobone.zerorobo.utils;

/**
 * This event is sent to {@link SimpleRobotBehaviour#getBulletMissedEvents()}
 * when one of your bullets has missed, i.e. when the bullet has reached the
 * border of the battlefield.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public final class BulletMissedEvent {
    private robocode.BulletMissedEvent ev;

    /**
     * Called by the game to create a new {@link BulletMissedEvent} object.
     *
     * @param ev the internal Robocode event
     */
    BulletMissedEvent(robocode.BulletMissedEvent ev) {
        this.ev = ev;
    }

    /**
     * Returns the bullet that missed.
     *
     * @return the bullet that missed
     */
    public Bullet getBullet() {
        return new Bullet(ev.getBullet());
    }

}