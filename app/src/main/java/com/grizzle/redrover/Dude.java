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

    public Dude(TouchableDrawingSurface touchableDrawingSurface, int x, int y, int scale) {
        super(touchableDrawingSurface, R.drawable.dude1, x, y, scale);
    }

    @Override
    protected void update() {
        super.update();
        this.setySpeed(0);
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
        setySpeed(1);
    }

    @Override
    public void onTouchMove(MotionEvent motionEvent) {
        this.setX((int) motionEvent.getX() - this.touchOffsetX);
        this.setY((int) motionEvent.getY() - this.touchOffsetY);
    }

    @Override
    public void onTouchUp(MotionEvent motionEvent) {
        this.touching = false;
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
