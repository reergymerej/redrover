package com.grizzle.redrover;

/**
 * Created by grizzle on 7/14/15.
 */
public class MotionVector {
    private double _x;
    private double _y;

    public MotionVector(double x, double y) {
        _x = x;
        _y = y;
    }

    public MotionVector(int x, int y) {
        _x = (double) x;
        _y = (double) y;
    }

    protected double x() {
        return _x;
    }

    protected double y() {
        return _y;
    }

    protected MotionVector x(int x) {
        x((double) x);
        return this;
    }

    protected MotionVector y(int y) {
        y((double) y);
        return this;
    }

    protected MotionVector x(double x) {
        _x = x;
        return this;
    }

    protected MotionVector y(double y) {
        _y = y;
        return this;
    }

    protected MotionVector add(MotionVector motionVector) {
        _x += motionVector.x();
        _y += motionVector.y();
        return this;
    }
}
