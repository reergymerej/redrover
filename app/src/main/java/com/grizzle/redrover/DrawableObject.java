package com.grizzle.redrover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;

/**
 * Created by grizzle on 7/13/15.
 */
public class DrawableObject {
    private SurfaceView surfaceView;

//    bitmap
    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private Rect drawPortionOfBitmap;

    private int scale;
    private int width;
    private int height;

//    position & speed
    private int x;
    private int y;
    private int xSpeed = 0;
    private int ySpeed = 0;

    private boolean constrainToSurfaceView = true;

    public DrawableObject(SurfaceView surfaceView, int drawableResource, int x, int y, int scale) {
        this.surfaceView = surfaceView;
        this.x = x;
        this.y = y;
        this.scale = scale;
        createBitmap(drawableResource);
    }

    private void createBitmap(int drawableResource) {
        this.bitmap = BitmapFactory.decodeResource(surfaceView.getResources(), drawableResource);

//        convert sprite into plain image
        this.bitmapWidth = this.bitmap.getWidth() / 3;
        this.bitmapHeight = this.bitmap.getHeight() / 4;
        drawPortionOfBitmap = new Rect(0, 0, this.bitmapWidth, this.bitmapHeight);

        this.width = (int) (this.bitmapWidth * scale);
        this.height = (int) (this.bitmapHeight * scale);
    }

    private void showBoundingBox(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1f);
        canvas.drawRect(this.getBoundingBox(), paint);
    }

    protected void draw(Canvas canvas) {
        update();

//        DEBUG
        showBoundingBox(canvas);

        canvas.drawBitmap(
                this.bitmap,
                this.drawPortionOfBitmap,
                this.getBoundingBox(),
                null);
    }

    protected void update() {
        this.y += this.ySpeed;
        this.x += this.xSpeed;

        if (constrainToSurfaceView) {
            int width = surfaceView.getWidth();
            int height = surfaceView.getHeight();

            if (y < 0 || y > height) {
                if (y < 0) {
                    setY(-y);
                } else {
                    setY(height - (y - height));
                }
                setySpeed(-getySpeed());
            }

            if (x < 0 || x > width) {

                if (x < 0) {
                    setX(-x);
                } else {
                    setX(width - (x - width));
                }
                setxSpeed(-getySpeed());
            }
        }
    }

    protected Rect getBoundingBox() {
        int halfWidth = this.width / 2;
        int halfHeight = this.height / 2;

        return new Rect(
                this.x - halfWidth,
                this.y - halfHeight,
                this.x + halfWidth,
                this.y + halfHeight
        );
    }

    protected int getxSpeed() {
        return this.xSpeed;
    }

    protected int getySpeed() {
        return this.ySpeed;
    }

    protected void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    protected void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    protected int getX() {
        return this.x;
    }

    protected int getY() {
        return this.y;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }
}
