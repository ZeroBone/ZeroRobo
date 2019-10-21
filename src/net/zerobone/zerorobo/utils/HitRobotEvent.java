package net.zerobone.zerorobo.utils;

/**
 * A net.zerobone.zerorobo.utils.HitRobotEvent is sent to {@link SimpleRobotBehavior#getHitRobotEvents()}
 * when your robot collides with another robot.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public final class HitRobotEvent {

    private robocode.HitRobotEvent ev;

    /**
     * Called by the game to create a new {@link HitRobotEvent} object.
     *
     * @param ev the internal Robocode event
     */
    HitRobotEvent(robocode.HitRobotEvent ev) {
        this.ev = ev;
    }

    /**
     * Returns the bearing to the robot you hit, relative to your robot's
     * heading, in degrees (-180 &lt;= getBearing() &lt; 180)
     *
     * @return the bearing to the robot you hit, in degrees
     */
    public double getBearing() {
        return ev.getBearing();
    }

    /**
     * Returns the amount of energy of the robot you hit.
     *
     * @return the amount of energy of the robot you hit
     */
    public double getEnergy() {
        return ev.getEnergy();
    }

    /**
     * Returns the name of the robot you hit.
     *
     * @return the name of the robot you hit
     */
    public String getName() {
        return ev.getName();
    }

    /**
     * Checks if your robot was moving towards the robot that was hit.
     * <p>
     * If {@link #isMyFault()} returns {@code true} then your robot's movement
     * (including turning) will have stopped and been marked complete.
     * <p>
     * Note: If two robots are moving toward each other and collide, they will
     * each receive two HitRobotEvents. The first will be the one if
     * {@link #isMyFault()} returns {@code true}.
     *
     * @return {@code true} if your robot was moving towards the robot that was
     *         hit; {@code false} otherwise.
     */
    public boolean isMyFault() {
        return ev.isMyFault();
    }

}