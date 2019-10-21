package net.zerobone.zerorobo.utils;

/**
 * A net.zerobone.zerorobo.utils.ScannedRobotEvent is sent to
 * {@link SimpleRobotBehaviour#getScannedRobotEvents()} when you scan a robot.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
final class ScannedRobotEvent {
    private robocode.ScannedRobotEvent ev;

    /**
     * Called by the game to create a new {@link ScannedRobotEvent} object.
     *
     * @param ev the internal Robocode event
     */
    ScannedRobotEvent(robocode.ScannedRobotEvent ev) {
        this.ev = ev;
    }

    /**
     * Returns the bearing to the robot you scanned, relative to your robot's
     * heading, in degrees (-180 &lt;= getBearing() &lt; 180)
     *
     * @return the bearing to the robot you scanned, in degrees
     */
    public double getBearing() {
        return ev.getBearing();
    }

    /**
     * Returns the distance to the robot (your center to his center).
     *
     * @return the distance to the robot.
     */
    public double getDistance() {
        return ev.getDistance();
    }

    /**
     * Returns the energy of the robot.
     *
     * @return the energy of the robot
     */
    public double getEnergy() {
        return ev.getEnergy();
    }

    /**
     * Returns the heading of the robot, in degrees (0 &lt;= getHeading() &lt; 360)
     *
     * @return the heading of the robot, in degrees
     */
    public double getHeading() {
        return ev.getHeading();
    }

    /**
     * Returns the name of the robot.
     *
     * @return the name of the robot
     */
    public String getName() {
        return ev.getName();
    }

    /**
     * Returns the velocity of the robot.
     *
     * @return the velocity of the robot
     */
    public double getVelocity() {
        return ev.getVelocity();
    }

}