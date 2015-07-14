package com.grizzle.redrover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Dude extends DrawableObject implements Touchable {

    private int touchOffsetX;
    private int touchOffsetY;
    private boolean touching = false;
    private MotionEvent lastMoveEvent;

    public Dude(TouchableDrawingSurface touchableDrawingSurface, int x, int y, int scale, int drawableResource) {
        super(touchableDrawingSurface, drawableResource, x, y, scale);
    }

    @Override
    protected void update() {
        super.update();
        this.slowDown();
    }

    private void slowDown() {
        double xSpeed = this.getxSpeed() / 1.2;
        double ySpeed = this.getySpeed() / 1.2;

        this.setxSpeed(Math.max((int) xSpeed, 0));
        this.setySpeed(Math.max((int) ySpeed, 0));
    }

    private void fling(MotionEvent lastMoveEvent) {
        int xDist = (int) lastMoveEvent.getX() - this.getX();
        int yDist = (int) lastMoveEvent.getY() - this.getY();

        this.setxSpeed(xDist);
        this.setySpeed(yDist);
    }

    @Override
    public boolean isTouching(MotionEvent motionEvent) {
        Rect boundingBox = this.getBoundingBox();
        return boundingBox.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }

    @Override
    public void onTouchDown(MotionEvent motionEvent) {
        this.touchOffsetX = (int) motionEvent.getX() - this.getX();
        this.touchOffsetY = (int) motionEvent.getY() - this.getY();
        this.touching = true;
//        setySpeed(1);
    }

    @Override
    public void onTouchMove(MotionEvent motionEvent) {
//        this.setX((int) motionEvent.getX() - this.touchOffsetX);
//        this.setY((int) motionEvent.getY() - this.touchOffsetY);
        lastMoveEvent = motionEvent;
    }

    @Override
    public void onTouchUp(MotionEvent motionEvent) {
        if (lastMoveEvent != null) {
            fling(lastMoveEvent);
        }

        this.touching = false;
        lastMoveEvent = null;
    }

    @Override
    public boolean isBeingTouched() {
        return this.touching;
    }

    public void steer(int direction) {
        int ySpeed = this.getySpeed();
        int xSpeed = this.getxSpeed();

        if (ySpeed > 0) {
            if (Math.abs(xSpeed) < 5) {
                this.setxSpeed(xSpeed + direction);
            }
        }
    }
}
