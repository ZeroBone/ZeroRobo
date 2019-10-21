package net.zerobone.zerorobo.utils;

/**
 * A net.zerobone.zerorobo.utils.HitWallEvent is sent to {@link SimpleRobotBehaviour#getHitWallEvents()}
 * when you collide a wall.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
final class HitWallEvent {
    private robocode.HitWallEvent ev;

    /**
     * Called by the game to create a new {@link HitWallEvent} object.
     *
     * @param ev the internal Robocode event.
     */
    HitWallEvent(robocode.HitWallEvent ev) {
        this.ev = ev;
    }

    /**
     * Returns the bearing to the wall you hit, relative to your robot's
     * heading, in degrees (-180 &lt;= getBearing() &lt; 180)
     *
     * @return the bearing to the wall you hit, in degrees
     */
    public double getBearing() {
        return ev.getBearing();
    }

}