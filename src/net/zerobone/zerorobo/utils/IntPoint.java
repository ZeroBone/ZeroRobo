package net.zerobone.zerorobo.utils;

import java.util.Objects;

public class IntPoint {

    public int x;

    public int y;

    public IntPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(IntPoint point) {
        x += point.x;
        y += point.y;
    }

    public void subtract(IntPoint point) {
        x -= point.x;
        y -= point.y;
    }

    public IntPoint copy() {
        return new IntPoint(x, y);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IntPoint intPoint = (IntPoint) o;

        return x == intPoint.x && y == intPoint.y;

    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "IntPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}