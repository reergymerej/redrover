package com.grizzle.redrover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Dude implements Renderable, Touchable {
    private TouchableDrawingSurface touchableDrawingSurface;
    private int x;
    private int y;
    private Bitmap bitmap;
    private int DRAWABLE_RESOURCE = R.drawable.dude1;
    private Rect drawPortionOfBitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private double size = 2.5;
    private int width;
    private int height;

    private int touchOffsetX;
    private int touchOffsetY;
    private boolean touching = false;
    private int ySpeed = 0;
    private int xSpeed = 0;

    public Dude(TouchableDrawingSurface touchableDrawingSurface, int x, int y) {
        this.touchableDrawingSurface = touchableDrawingSurface;
        this.x = x;
        this.y = y;

        createBitmap();
    }

    private void createBitmap() {
        this.bitmap = BitmapFactory.decodeResource(touchableDrawingSurface.getResources(), DRAWABLE_RESOURCE);

//        convert sprite into plain image
        this.bitmapWidth = this.bitmap.getWidth() / 3;
        this.bitmapHeight = this.bitmap.getHeight() / 4;
        drawPortionOfBitmap = new Rect(0, 0, this.bitmapWidth, this.bitmapHeight);

        this.width = (int) (this.bitmapWidth * size);
        this.height = (int) (this.bitmapHeight * size);
    }

    private Rect getBoundingBox() {
        int halfWidth = this.width / 2;
        int halfHeight = this.height / 2;

        return new Rect(
                this.x - halfWidth,
                this.y - halfHeight,
                this.x + halfWidth,
                this.y + halfHeight
        );
    }

    @Override
    public void update() {
        this.y += this.ySpeed;
        this.x += this.xSpeed;
        this.xSpeed = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        update();

//        DEBUG
        showBoundingBox(canvas);

        canvas.drawBitmap(
                this.bitmap,
                this.drawPortionOfBitmap,
                this.getBoundingBox(),
                null);
    }

    private void showBoundingBox(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1f);
        canvas.drawRect(this.getBoundingBox(), paint);
    }

    @Override
    public boolean isTouching(MotionEvent motionEvent) {
        Rect boundingBox = this.getBoundingBox();
        return boundingBox.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }

    @Override
    public void onTouchDown(MotionEvent motionEvent) {
        this.touchOffsetX = (int) motionEvent.getX() - this.x;
        this.touchOffsetY = (int) motionEvent.getY() - this.y;
        this.touching = true;
        this.ySpeed = 1;
    }

    @Override
    public void onTouchMove(MotionEvent motionEvent) {
        this.x = (int) motionEvent.getX() - this.touchOffsetX;
        this.y = (int) motionEvent.getY() - this.touchOffsetY;
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
        if (this.ySpeed > 0) {
            if (Math.abs(this.xSpeed) < 5) {
                this.xSpeed += direction;
            }
        }
    }
}
