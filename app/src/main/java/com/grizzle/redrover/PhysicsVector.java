package com.grizzle.redrover;

/**
 * Created by grizzle on 7/14/15.
 */
public class PhysicsVector {
    private double _x;
    private double _y;

    public PhysicsVector(double x, double y) {
        _x = x;
        _y = y;
    }

    public PhysicsVector(int x, int y) {
        _x = (double) x;
        _y = (double) y;
    }

    protected double x() {
        return _x;
    }

    protected double y() {
        return _y;
    }

    protected PhysicsVector x(int x) {
        x((double) x);
        return this;
    }

    protected PhysicsVector y(int y) {
        y((double) y);
        return this;
    }

    protected PhysicsVector x(double x) {
        _x = x;
        return this;
    }

    protected PhysicsVector y(double y) {
        _y = y;
        return this;
    }

    protected PhysicsVector add(PhysicsVector physicsVector) {
        _x += physicsVector.x();
        _y += physicsVector.y();
        return this;
    }
}
