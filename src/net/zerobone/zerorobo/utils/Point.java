package net.zerobone.zerorobo.utils;

/**
 * An immutable vector in two dimensional space over doubles.
 *
 * The vector contains an x- and y-coordinate and provides various methods to operate on them.
 * The output of the methods is chosen so that the results are easy to use in Robocode. Specifically this means that
 * angles are measured from the ordinate axis in a clockwise manner and that angles returned may be negative. All angles
 * are in degrees.
 *
 * @author Tobias Zimmermann (original)
 * @author Billy Joe Franks (adaptation)
 * @author Julian "Jules" Stieß (adaptation)
 */
public class Point {

    /**
     * The x coordinate of the vector. Guaranteed to be finite.
     */
    private double x;

    /**
     * The y coordinate of the vector. Guaranteed to be finite.
     */
    private double y;

    /**
     * Creates a new vector from given finite x and y coordinates.
     *
     * @param x The x coordinate of the newly constructed vector.
     * @param y The y coordinate of the newly constructed vector.
     */
    public Point(double x, double y) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("The x-coordinate of vector must be a finite number.");
        }

        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("The y-coordinate of vector must be a finite number.");
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new vector from its orientation and length.
     *
     * @param phi    The orientation of the new vector, measured from the y-axis, in degrees.
     * @param length The length of the new vector.
     * @return       The position vector for the given polar coordinates.
     */
    public static Point fromPolarCoordinates(double phi, double length) {
        phi = Math.toRadians(phi);
        return new Point(Math.sin(phi) * length, Math.cos(phi) * length);
    }

    /**
     * Returns the x coordinate of the vector. Guaranteed to be finite.
     *
     * @return The x coordinate of the vector.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the vector. Guaranteed to be finite.
     *
     * @return The y coordinate of the vector.
     */
    public double getY() {
        return y;
    }
    /**
     * Returns a new vector with the given finite x coordinate
     *
     * @param newX The x coordinate of the new vector.
     * @return The new vector
     */
    public Point withX(double newX) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("The x-coordinate of vector must be a finite number.");
        }
        return new Point(newX,y);
    }

    /**
     * Returns a new vector with the given finite y coordinate.
     *
     * @param newY The y coordinate of the new vector.
     * @return The new vector
     */
    public Point withY(double newY) {
        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("The x-coordinate of vector must be a finite number.");
        }

        return new Point(x,newY);
    }

    /**
     * Adds the given finite x and y values to this vector and stores the result in a new vector.
     *
     * @param x The finite value to add to the x-coordinate.
     * @param y The finite value to add to the y-coordinate,
     * @return  The sum vector.
     */
    public Point add(double x, double y) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("The x-coordinate of vector must be a finite number.");
        }

        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("The y-coordinate of vector must be a finite number.");
        }

        return new Point(this.x + x, this.y + y);
    }

    /**
     * Adds the given vector to this vector by adding each coordinate and stores the result in a new vector.
     *
     * @param other The vector to add to this one.
     * @return      The sum vector.
     */
    public Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtracts the given finite x and y values to this vector and stores the result in a new vector.
     *
     * @param x The finite value to subtract from the x-coordinate
     * @param y The finite value to subtract from the y-coordinate
     * @return  The difference vector.
     */
    public Point subtract(double x, double y) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("The x-coordinate of vector must be a finite number.");
        }

        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("The y-coordinate of vector must be a finite number.");
        }

        return new Point(this.x - x, this.y - y);
    }

    /**
     * Subtracts the given vector from this one by subtracting each coordinate and stores the result in a new vector.
     *
     * @param other The vector to subtract from this one.
     * @return      The difference vector.
     */
    public Point subtract(Point other) {
        return new Point(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiplies the vector by a given finite scalar and stores the result in a new vector.
     *
     * @param scalar The finite scalar by which to multiply.
     * @return       The result vector.
     */
    public Point multiply(double scalar) {
        return new Point(x * scalar, y * scalar);
    }

    /**
     * Returns the length of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns the square of the length of the vector.
     *
     * This method is provided for performance and precision reasons. The length is calculated with the Pythagorean
     * theorem, which means a square root is used, which costs both CPU time and potentially precision, if the result
     * cannot be represented exactly.
     *
     * @return The square of the length of the vector.
     */
    public double lengthSquared() {
        return x * x + y * y;
    }

    /**
     * Normalizes the vector to length one and returns the result as a new vector.
     *
     * @return The normalized vector.
     */
    public Point normalize() {
        double len = length();
        if (len == 0) {
            throw new IllegalStateException("Cannot normalize a null vector.");
        }

        return multiply(1 / len);
    }

    /**
     * Returns a new vector with the same orientation as this one and the specified length.
     *
     * @param desiredLength The length the vector should have.
     * @return The vector with the desired length.
     */
    public Point withLength(double desiredLength) {
        double len = length();
        if (len == 0) {
            if (desiredLength == 0) {
                return this;
            }

            throw new IllegalStateException("Cannot create a null vector of non-zero length.");
        }

        return multiply(desiredLength / len);
    }

    /**
     * Returns the angle between the y-axis and this vector in degrees.
     *
     * The angle is measured in the range from -Pi to Pi.
     *
     * @return The angle between the y-axis and this vector in degrees.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public double angle() {
        return Math.toDegrees(Math.atan2(x, y));
    }

    /**
     * Returns the angle between this vector and the one specified by the given coordinates in degrees.
     *
     * The angle is measured in the range from -Pi to Pi.
     *
     * @param x The x-coordinate of the vector to which to measure the angle to.
     * @param y The y-coordinate of the vector to which to measure the angle to.
     * @return  The angle between the vectors in degrees.
     */
    public double angleFrom(double x, double y) {
        return Math.toDegrees(Math.atan2(this.x - x, this.y - y));
    }

    /**
     * Returns the ange between this vector and the one given as a parameter measured in degrees.
     *
     * The angle is measured in the range from -Pi to Pi. Returns {@code NaN} if the given vector is {@code null}.
     *
     * @param other The vector to which to measure the angle to.
     * @return      The angle between the vectors in degrees.
     */
    public double angleFrom(Point other) {
        if (other == null) {
            return Double.NaN;
        }

        return Math.toDegrees(Math.atan2(x - other.x, y - other.y));
    }

    /**
     * Returns the dot product between this vector and the one given as a parameter.
     *
     * @param other The other vector in the dot product.
     * @return      The dot product in double precision.
     */
    public double dotProduct(Point other) {
        return x * other.x + y * other.y;
    }

    /**
     * Checks if this vector and the given vector point in either the same or exact opposite directions.
     *
     * @param other The vector to check this vector against.
     * @return      {@code true} if this vector and the given vector point in the same or opposite directions.
     */
    public boolean isSameDirectionAs(Point other) {
        if (other == null) {
            return false;
        }

        if (other.x == 0) {
            return x == 0 && (y == 0 && other.y == 0 || y != 0 && other.y != 0);
        } else if (x == 0) {
            return false;
        } else {
            double xFactor = x / other.x;
            return Double.compare(y, other.y * xFactor) == 0;
        }
    }

    /**
     * Checks if this vector and the given vector point in the exact same direction and have the same heading.
     *
     * @param other The vector to check this vector against.
     * @return      {@code true} if this vector and the given vector have the same direction and heading.
     */
    public boolean isSameDirectionAndHeadingAs(Point other) {
        if (other == null) {
            return false;
        }

        if (other.x == 0) {
            return x == 0 && Math.signum(y) == Math.signum(other.y);
        } else if (x == 0) {
            return false;
        } else {
            double xFactor = x / other.x;
            return Double.compare(y, other.y * xFactor) == 0 && Math.signum(x) == Math.signum(other.x);
        }
    }

    /**
     * Returns the distance between this vector and the given one.
     *
     * @param other the vector to calculate the distance to
     * @return the distance between this vector and the given one
     */
    public double distance(Point other) {
        double xDiff = x - other.x;
        double yDiff = y - other.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    /**
     * Returns the square of the distance between this vector and the given one.
     *
     * This method is provided for performance and precision reasons. The length is calculated with the Pythagorean
     * theorem, which means a square root is used, which costs both CPU time and potentially precision, if the result
     * cannot be represented exactly.
     *
     * @param other the vector to calculate the distance to
     * @return the square of the distance between this vector and the given one
     */
    public double distanceSq(Point other) {
        double xDiff = x - other.x;
        double yDiff = y - other.y;
        return xDiff * xDiff + yDiff * yDiff;
    }

    /**
     * Tests this vector of equality to the given vector.
     *
     * Vectors are equal if and only if both the x- and the y-coordinate are equal. To account for double imprecision,
     * the numbers are compared with {@link Double#compare(double, double)} which allows slight differences.
     * Returns {@code false} if the other vector is {@code null}.
     * If the other object is not a vector, returns {@code false}.
     *
     * @param other The vector to compare this vector to.
     * @return      {@code true} if this vector and the other are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Point)) {
            return false;
        }

        Point vec = (Point)other;
        return Double.compare(x, vec.x) == 0 && Double.compare(y, vec.y) == 0;
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return A string representation of this vector.
     */
    @Override
    public String toString() {
        return "<" + x + ", " + y + '>';
    }

}