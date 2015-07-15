package com.grizzle.redrover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.Random;

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
        MotionVector speed = this.getSpeed();

        double xSpeed = speed.x() * 0.9;
        double ySpeed = speed.y() * 0.9;

        if (Math.abs(xSpeed) < 1) {
            xSpeed = 0;
        }

        if (Math.abs(ySpeed) < 1) {
            ySpeed = 0;
        }

        speed.x(xSpeed).y(ySpeed);
    }

//    private void fling(MotionEvent lastMoveEvent) {
//        int xDist = (int) lastMoveEvent.getX() - this.getX();
//        int yDist = (int) lastMoveEvent.getY() - this.getY();
//
//        this.setxSpeed(xDist);
//        this.setySpeed(yDist);
//    }

    private void startMoving() {
        Random random = new Random();
        setSpeed(new MotionVector(random.nextInt(100) - 50, random.nextInt(100) - 50));
    }

    @Override
    public boolean isTouching(MotionEvent motionEvent) {
        Rect boundingBox = this.getBoundingBox();
        return boundingBox.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }

    @Override
    public void onTouchDown(MotionEvent motionEvent) {
        this.touchOffsetX = (int) (motionEvent.getX() - location().x());
        this.touchOffsetY = (int) (motionEvent.getY() - location().x());
        this.touching = true;
        this.startMoving();
    }

    @Override
    public void onTouchMove(MotionEvent motionEvent) {
//        this.setX((int) motionEvent.get_x() - this.touchOffsetX);
//        this.setY((int) motionEvent.get_y() - this.touchOffsetY);
//        lastMoveEvent = motionEvent;
    }

    @Override
    public void onTouchUp(MotionEvent motionEvent) {
//        if (lastMoveEvent != null) {
//            fling(lastMoveEvent);
//        }

        this.touching = false;
//        lastMoveEvent = null;
    }

    @Override
    public boolean isBeingTouched() {
        return this.touching;
    }

//    public void steer(int direction) {
//        int ySpeed = this.getySpeed();
//        int xSpeed = this.getxSpeed();
//
//        if (ySpeed > 0) {
//            if (Math.abs(xSpeed) < 5) {
//                this.setxSpeed(xSpeed + direction);
//            }
//        }
//    }
}
