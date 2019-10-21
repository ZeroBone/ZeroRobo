package net.zerobone.zerorobo.utils;

import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class provides methods to control the actions of a robot. To implement
 * your own behavior, override the {@link #start()} and {@link #execute()}
 * methods.
 * <p>
 * The {@link #start()} method is called once at the start of each round. The
 * {@link #execute()} method is called once each tick and should be used to
 * implement an actual behavior. All actions will be queued up and executed when
 * the method returns.
 */
public abstract class SimpleRobotBehaviour {

    private SimpleRobot robot;

    public SimpleRobotBehaviour(SimpleRobot robot) {
        this.robot = robot;
    }

    /**
     * This method is called once at the beginning of a round and can be used to
     * perform any kind of setup. All actions started in this method will be
     * executed after it returns.
     */
    protected abstract void start();

    /**
     * This method is called once during each game tick and should be used to
     * implement the behavior of the robot.
     * All actions queued up will be executed when this method returns.
     */
    protected abstract void execute();

    /**
     * Returns the distance remaining in the robot's current move measured in
     * pixels.
     * <p>
     * This call returns both positive and negative values. Positive values
     * means that the robot is currently moving forwards. Negative values means
     * that the robot is currently moving backwards. If the returned value is 0,
     * the robot currently stands still.
     *
     * @return the distance remaining in the robot's current move measured in
     *         pixels.
     * @see #getTurnRemaining() getTurnRemaining()
     * @see #getGunTurnRemaining() getGunTurnRemaining()
     * @see #getRadarTurnRemaining() getRadarTurnRemaining()
     */
    public final double getDistanceRemaining() {
        return robot.getDistanceRemaining();
    }

    /**
     * Returns the direction that the robot's gun is facing, in degrees.
     * The value returned will be between 0 and 360 (is excluded).
     * <p>
     * Note that the heading in Robocode is like a compass, where 0 means North,
     * 90 means East, 180 means South, and 270 means West.
     *
     * @return the direction that the robot's gun is facing, in degrees.
     * @see #getHeading()
     * @see #getRadarHeading()
     */
    public final double getGunHeading() {
        return robot.getGunHeading();
    }

    /**
     * Returns the angle remaining in the gun's turn, in degrees.
     * <p>
     * This call returns both positive and negative values. Positive values
     * means that the gun is currently turning to the right. Negative values
     * means that the gun is currently turning to the left. If the returned
     * value is 0, the gun is currently not turning.
     *
     * @return the angle remaining in the gun's turn, in degrees
     * @see #getDistanceRemaining() getDistanceRemaining()
     * @see #getTurnRemaining() getTurnRemaining()
     * @see #getRadarTurnRemaining() getRadarTurnRemaining()
     */
    public final double getGunTurnRemaining() {
        return robot.getGunTurnRemaining();
    }

    /**
     * Returns the direction that the robot's body is facing, in degrees.
     * The value returned will be between 0 and 360 (is excluded).
     * <p>
     * Note that the heading in Robocode is like a compass, where 0 means North,
     * 90 means East, 180 means South, and 270 means West.
     *
     * @return the direction that the robot's body is facing, in degrees.
     * @see #getGunHeading()
     * @see #getRadarHeading()
     */
    public final double getHeading() {
        return robot.getHeading();
    }

    /**
     * Returns the direction that the robot's radar is facing, in degrees.
     * The value returned will be between 0 and 360 (is excluded).
     * <p>
     * Note that the heading in Robocode is like a compass, where 0 means North,
     * 90 means East, 180 means South, and 270 means West.
     *
     * @return the direction that the robot's radar is facing, in degrees.
     * @see #getHeading()
     * @see #getGunHeading()
     */
    public final double getRadarHeading() {
        return robot.getRadarHeading();
    }

    /**
     * Returns the angle remaining in the radar's turn, in degrees.
     * <p>
     * This call returns both positive and negative values. Positive values
     * means that the radar is currently turning to the right. Negative values
     * means that the radar is currently turning to the left. If the returned
     * value is 0, the radar is currently not turning.
     *
     * @return the angle remaining in the radar's turn, in degrees
     * @see #getDistanceRemaining() getDistanceRemaining()
     * @see #getGunTurnRemaining() getGunTurnRemaining()
     * @see #getRadarTurnRemaining() getRadarTurnRemaining()
     */
    public final double getRadarTurnRemaining() {
        return robot.getRadarTurnRemaining();
    }

    /**
     * Returns the angle remaining in the robots's turn, in degrees.
     * <p>
     * This call returns both positive and negative values. Positive values
     * means that the robot is currently turning to the right. Negative values
     * means that the robot is currently turning to the left. If the returned
     * value is 0, the robot is currently not turning.
     *
     * @return the angle remaining in the robots's turn, in degrees
     * @see #getDistanceRemaining() getDistanceRemaining()
     * @see #getGunTurnRemaining() getGunTurnRemaining()
     * @see #getRadarTurnRemaining() getRadarTurnRemaining()
     */
    public final double getTurnRemaining() {
        return robot.getTurnRemaining();
    }

    /**
     * Sets the robot to move ahead (forward) by distance measured in pixels
     * when the call returns from {@link #execute()}.
     * <p>
     * This call returns immediately, and will not execute until you return from
     * {@link #execute()}.
     * <p>
     * Note that both positive and negative values can be given as input, where
     * positive values means that the robot is set to move ahead, and negative
     * values means that the robot is set to move back. If 0 is given as input,
     * the robot will stop its movement, but will have to decelerate
     * till it stands still, and will thus not be able to stop its movement
     * immediately, but eventually.
     *
     * @param distance the distance to move measured in pixels.
     *                 If {@code distance} &gt; 0 the robot is set to move ahead.
     *                 If {@code distance} &lt; 0 the robot is set to move back.
     *                 If {@code distance} = 0 the robot is set to stop its movement.
     */
    public final void ahead(double distance) {
        robot.setAhead(distance);
    }

    /**
     * Sets the gun to fire a bullet when the call returns from
     * {@link #execute()}. The bullet will travel in the direction the gun is
     * pointing.
     * <p>
     * This call returns immediately, and will not execute until you return from
     * {@link #execute()}.
     * <p>
     * The specified bullet power is an amount of energy that will be taken from
     * the robot's energy. Hence, the more power you want to spend on the
     * bullet, the more energy is taken from your robot.
     * <p>
     * The bullet will do (4 * power) damage if it hits another robot. If power
     * is greater than 1, it will do an additional 2 * (power - 1) damage.
     * You will get (3 * power) back if you hit the other robot.
     * <p>
     * The specified bullet power should be between
     * 0.1 and 3.
     * <p>
     * Note that the gun cannot fire if the gun is overheated, meaning that
     * {@link #getGunHeat()} returns a value &gt; 0.
     * <p>
     * A event is generated when the bullet hits a robot
     * ({@link BulletHitEvent}), wall ({@link BulletMissedEvent}), or another
     * bullet ({@link BulletHitBulletEvent}).
     * <p>
     * Example:
     * <pre>
     *   net.zerobone.zerorobo.utils.Bullet bullet = null;
     *
     *   // Fire a bullet with maximum power if the gun is ready
     *   if (getGunHeat() == 0) {
     *       bullet = fireBullet(3);
     *   }
     *   ...
     *   // Get the velocity of the bullet
     *   if (bullet != null) {
     *       double bulletVelocity = bullet.getVelocity();
     *   }
     * </pre>
     *
     * @param power the amount of energy given to the bullet, and subtracted
     *              from the robot's energy.
     * @return a {@link Bullet} that contains information about the bullet if it
     *         was actually fired, which can be used for tracking the bullet
     *         after it has been fired. If the bullet was not fired,
     *         {@code null} is returned.
     * @see Bullet
     * @see #getGunHeat() getGunHeat()
     * @see #getGunCoolingRate() getGunCoolingRate()
     */
    public final Bullet fireBullet(double power) {
        robocode.Bullet bullet = robot.setFireBullet(power);
        if (bullet == null) return null;
        return new Bullet(bullet);
    }

    /**
     * Sets the robot's gun to turn right by degrees when the call returns from
     * {@link #execute()}.
     * takes place.
     * <p>
     * This call returns immediately, and will not execute until you return from
     * {@link #execute()}.
     * <p>
     * Note that both positive and negative values can be given as input,
     * where negative values means that the robot's gun is set to turn left
     * instead of right.
     * <p>
     *
     * @param degrees the amount of degrees to turn the robot's gun to the right.
     *                If {@code degrees} &gt; 0 the robot's gun is set to turn right.
     *                If {@code degrees} &lt; 0 the robot's gun is set to turn left.
     *                If {@code degrees} = 0 the robot's gun is set to stop turning.
     */
    public final void turnGun(double degrees) {
        robot.setTurnGunRight(degrees);
    }

    /**
     * Sets the robot's body to turn right by degrees when the call returns from
     * {@link #execute()}.
     * <p>
     * This call returns immediately, and will not execute until you return from
     * {@link #execute()}.
     * <p>
     * Note that both positive and negative values can be given as input,
     * where negative values means that the robot's body is set to turn left
     * instead of right.
     *
     * @param degrees the amount of degrees to turn the robot's body to the right.
     *                If {@code degrees} &gt; 0 the robot is set to turn right.
     *                If {@code degrees} &lt; 0 the robot is set to turn left.
     *                If {@code degrees} = 0 the robot is set to stop turning.
     */
    public final void turn(double degrees) {
        robot.setTurnRight(degrees);
    }

    /**
     * Sets the robot's radar to turn right by degrees when the call returns
     * from {@link #execute()}.
     * <p>
     * This call returns immediately, and will not execute until you return from
     * {@link #execute()}.
     * <p>
     * Note that both positive and negative values can be given as input,
     * where negative values means that the robot's radar is set to turn left
     * instead of right.
     *
     * @param degrees the amount of degrees to turn the robot's radar to the right.
     *                If {@code degrees} &gt; 0 the robot's radar is set to turn right.
     *                If {@code degrees} &lt; 0 the robot's radar is set to turn left.
     *                If {@code degrees} = 0 the robot's radar is set to stop turning.
     */
    public final void turnRadar(double degrees) {
        robot.setTurnRadarRight(degrees);
    }

    /**
     * Returns the height of the current battlefield measured in pixels.
     *
     * @return the height of the current battlefield measured in pixels.
     */
    public final double getBattleFieldHeight() {
        return robot.getBattleFieldHeight();
    }

    /**
     * Returns the width of the current battlefield measured in pixels.
     *
     * @return the width of the current battlefield measured in pixels.
     */
    public final double getBattleFieldWidth() {
        return robot.getBattleFieldWidth();
    }

    /**
     * Returns the robot's current energy.
     *
     * @return the robot's current energy.
     */
    public final double getEnergy() {
        return robot.getEnergy();
    }

    /**
     * Returns the rate at which the gun will cool down, i.e. the amount of heat
     * the gun heat will drop per turn.
     * <p>
     * The gun cooling rate is default 0.1 / turn, but can be changed by the
     * battle setup. So don't count on the cooling rate being 0.1!
     *
     * @return the gun cooling rate
     * @see #getGunHeat()
     * @see #fireBullet(double)
     */
    public final double getGunCoolingRate() {
        return robot.getGunCoolingRate();
    }

    /**
     * Returns the current heat of the gun. The gun cannot fire unless this is
     * 0. (Calls to fire will succeed, but will not actually fire unless
     * getGunHeat() == 0).
     * <p>
     * The amount of gun heat generated when the gun is fired is
     * 1 + (firePower / 5). Each turn the gun heat drops by the amount returned
     * by {@link #getGunCoolingRate()}, which is a battle setup.
     * <p>
     * Note that all guns are "hot" at the start of each round, where the gun
     * heat is 3.
     *
     * @return the current gun heat
     * @see #getGunCoolingRate()
     * @see #fireBullet(double)
     */
    public final double getGunHeat() {
        return robot.getGunHeat();
    }

    /**
     * Returns the game time of the current round, where the time is equal to
     * the current turn in the round.
     * <p>
     * A battle consists of multiple rounds.
     * <p>
     * Time is reset to 0 at the beginning of every round.
     *
     * @return the game time/turn of the current round.
     */
    public final long getTime() {
        return robot.getTime();
    }

    /**
     * Returns the velocity of the robot measured in pixels/turn.
     * <p>
     * The maximum velocity of a robot is 8 pixels / turn.
     *
     * @return the velocity of the robot measured in pixels/turn.
     */
    public final double getVelocity() {
        return robot.getVelocity();
    }

    /**
     * Sets the maximum velocity of the robot measured in pixels/turn if the robot should move slower than 8 pixels/turn.
     * when the call returns from {@link #execute()}.
     *
     * @param newMaxVelocity - the new maximum turn rate of the robot measured in pixels/turn. Valid values are 0 - 8
     */
    public final void setMaxVelocity(double newMaxVelocity){
        robot.setMaxVelocity(newMaxVelocity);
    }

    /**
     * Returns the X position of the robot. (0,0) is at the bottom left of the
     * battlefield.
     *
     * @return the X position of the robot.
     * @see #getY()
     */
    public final double getX() {
        return robot.getX();
    }

    /**
     * Returns the Y position of the robot. (0,0) is at the bottom left of the
     * battlefield.
     *
     * @return the Y position of the robot.
     * @see #getX()
     */
    public final double getY() {
        return robot.getY();
    }

    /**
     * Returns the position of the robot as a net.zerobone.zerorobo.utils.Point. (0,0) is at the bottom left of the
     * battlefield.
     *
     * @return the position of the robot as a net.zerobone.zerorobo.utils.Point.
     * @see #getX()
     * @see #getY()
     */
    public final Point getPoint() {
        return new Point(this.getX(),this.getY());
    }

    /**
     * Sets the color of the robot's body, gun, radar, bullet, and scan arc in
     * the same time.
     * <p>
     * You may only call this method one time per battle. A {@code null}
     * indicates the default (blue) color for the body, gun, radar, and scan
     * arc, but white for the bullet color.
     * <p>
     * Example:
     * <pre>
     *   // Don't forget to import java.awt.Color at the top...
     *   import java.awt.Color;
     *   ...
     *
     *   public void start() {
     *       setColors(null, Color.RED, Color.GREEN, null, new Color(150, 0, 150));
     *       ...
     *   }
     * </pre>
     *
     * @param bodyColor	the new body color
     * @param gunColor	 the new gun color
     * @param radarColor   the new radar color
     * @param bulletColor  the new bullet color
     * @param scanArcColor the new scan arc color
     * @see Color
     */
    public final void setColors(Color bodyColor, Color gunColor, Color radarColor, Color bulletColor, Color scanArcColor) {
        robot.setColors(bodyColor, gunColor, radarColor, bulletColor, scanArcColor);
    }

    /**
     * Returns a vector containing all {@link BulletHitBulletEvent}s currently
     * in the robot's queue.
     * <p>
     * Example:
     * <pre>
     *   for (net.zerobone.zerorobo.utils.BulletHitBulletEvent event : getBulletHitBulletEvents()) {
     *       <i>// do something with the event</i>
     *   }
     * </pre>
     *
     * @return a vector containing all BulletHitBulletEvents currently in the
     *         robot's queue
     * @see BulletHitBulletEvent
     */
    public final Vector<BulletHitBulletEvent> getBulletHitBulletEvents() {
        Vector<BulletHitBulletEvent> res =  Stream.concat(
                robot
                        .getBulletHitBulletEvents()
                        .stream()
                        .map(BulletHitBulletEvent::new),
                robot.bulletHitBulletEventQueue.stream())
                .collect(Collectors.toCollection(Vector::new));
        robot.bulletHitBulletEventQueue = new LinkedList<>();
        return res;
    }

    /**
     * Returns a vector containing all {@link BulletHitEvent}s in the
     * robot's queue.
     * <p>
     * Example:
     * <pre>
     *   for (net.zerobone.zerorobo.utils.BulletHitEvent event: getBulletHitEvents()) {
     *       <i>// do something with the event</i>
     *   }
     * </pre>
     *
     * @return a vector containing all BulletHitEvents currently in the robot's
     *         queue
     * @see BulletHitEvent
     */
    public final Vector<BulletHitEvent> getBulletHitEvents() {
        Vector<BulletHitEvent> res = Stream.concat(
                robot
                        .getBulletHitEvents()
                        .stream()
                        .map(BulletHitEvent::new),
                robot.bulletHitEventQueue.stream())
                .collect(Collectors.toCollection(Vector::new));
        robot.bulletHitEventQueue = new LinkedList<>();
        return res;
    }

    /**
     * Returns a vector containing all {@link BulletMissedEvent}s currently in
     * the robot's queue.
     * <p>
     * Example:
     * <pre>
     *   for (net.zerobone.zerorobo.utils.BulletMissedEvent event : getBulletMissedEvents()) {
     *       <i>// do something with the event</i>
     *   }
     * </pre>
     *
     * @return a vector containing all BulletMissedEvents currently in the
     *         robot's queue
     * @see BulletMissedEvent
     */
    public final Vector<BulletMissedEvent> getBulletMissedEvents() {
        Vector<BulletMissedEvent> res = Stream.concat(
                robot
                        .getBulletMissedEvents()
                        .stream()
                        .map(BulletMissedEvent::new),
                robot.bulletMissedEventQueue.stream())
                .collect(Collectors.toCollection(Vector::new));
        robot.bulletMissedEventQueue = new LinkedList<>();
        return res;
    }

    /**
     * Returns a vector containing all {@link HitByBulletEvent}s currently in
     * the robot's queue.
     * <p>
     * Example:
     * <pre>
     *   for (net.zerobone.zerorobo.utils.HitByBulletEvent event : getHitByBulletEvents()) {
     *       <i>// do something with the event</i>
     *   }
     * </pre>
     *
     * @return a vector containing all HitByBulletEvents currently in the
     *         robot's queue
     * @see HitByBulletEvent
     */
    public final Vector<HitByBulletEvent> getHitByBulletEvents() {
        Vector<HitByBulletEvent> res = Stream.concat(
                robot
                        .getHitByBulletEvents()
                        .stream()
                        .map(HitByBulletEvent::new),
                robot.hitByBulletEventQueue.stream())
                .collect(Collectors.toCollection(Vector::new));
        robot.hitByBulletEventQueue = new LinkedList<>();
        return res;
    }

    /**
     * Returns a vector containing all {@link HitRobotEvent}s currently in the
     * robot's queue.
     * <p>
     * Example:
     * <pre>
     *   for (net.zerobone.zerorobo.utils.HitRobotEvent event : getHitRobotEvents()) {
     *       <i>// do something with the event</i>
     *   }
     * </pre>
     *
     * @return a vector containing all HitRobotEvents currently in the robot's
     *         queue
     * @see HitRobotEvent
     */
    public final Vector<HitRobotEvent> getHitRobotEvents() {
        Vector<HitRobotEvent> res = Stream.concat(
                robot
                        .getHitRobotEvents()
                        .stream()
                        .map(HitRobotEvent::new),
                robot.hitRobotEventQueue.stream())
                .collect(Collectors.toCollection(Vector::new));
        robot.hitRobotEventQueue = new LinkedList<>();
        return res;
    }

    /**
     * Returns a vector containing all {@link HitWallEvent}s currently in the
     * robot's queue.
     * <p>
     * Example:
     * <pre>
     *   for (net.zerobone.zerorobo.utils.HitWallEvent event : getHitWallEvents()) {
     *       <i>// do something with the event</i>
     *   }
     * </pre>
     *
     * @return a vector containing all HitWallEvents currently in the robot's
     *         queue
     * @see HitWallEvent
     */
    public final Vector<HitWallEvent> getHitWallEvents() {
        Vector<HitWallEvent> res = Stream.concat(
                robot
                        .getHitWallEvents()
                        .stream()
                        .map(HitWallEvent::new),
                robot.hitWallEventQueue.stream())
                .collect(Collectors.toCollection(Vector::new));
        robot.hitWallEventQueue = new LinkedList<>();
        return res;
    }

    /**
     * Returns a vector containing all {@link ScannedRobotEvent}s currently in
     * the robot's queue.
     * <p>
     * Example:
     * <pre>
     *   for (net.zerobone.zerorobo.utils.ScannedRobotEvent event : getScannedRobotEvents()) {
     *       <i>// do something with the event</i>
     *   }
     * </pre>
     *
     * @return a vector containing all ScannedRobotEvents currently in the
     *         robot's queue
     * @see ScannedRobotEvent
     */
    public final Vector<ScannedRobotEvent> getScannedRobotEvents() {
        Vector<ScannedRobotEvent> res = Stream.concat(
                robot
                        .getScannedRobotEvents()
                        .stream()
                        .map(ScannedRobotEvent::new),
                robot.scannedRobotEventQueue.stream())
                .collect(Collectors.toCollection(Vector::new));
        robot.scannedRobotEventQueue = new LinkedList<>();
        return res;
    }

    /**
     * Returns a graphics context used for painting graphical items for the
     * robot.
     * <p>
     * This method is very useful for debugging your robot.
     * <p>
     * Note that the robot will only be painted if the "Paint" is enabled on the
     * robot's console window; otherwise the robot will never get painted (the
     * reason being that all robots might have graphical items that must be
     * painted, and then you might not be able to tell what graphical items that
     * have been painted for your robot).
     * <p>
     * Also note that the coordinate system for the graphical context where you
     * paint items fits for the Robocode coordinate system where (0, 0) is at
     * the bottom left corner of the battlefield, where X is towards right and Y
     * is upwards.
     *
     * @return a graphics context used for painting graphical items for the
     *         robot.
     */
    public Graphics2D getGraphics() {
        return robot.getGraphics();
    }

}