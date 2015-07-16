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
    private PhysicsVector location = new PhysicsVector(0, 0);
    private PhysicsVector speed = new PhysicsVector(0, 0);

    private boolean constrainToSurfaceView = true;

    public DrawableObject(SurfaceView surfaceView, int drawableResource, int x, int y, int scale) {
        this.surfaceView = surfaceView;
        this.location(x, y);

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
        location.add(speed);

        int x = (int) location.x();
        int y = (int) location.y();

        if (constrainToSurfaceView) {
            int width = surfaceView.getWidth();
            int height = surfaceView.getHeight();

            if (y < 0 || y > height) {
                if (y < 0) {
                    location.y(-y);
                } else {
                    location.y(height - (y - height));
                }
                speed.y( -speed.y() );
            }

            if (x < 0 || x > width) {
                if (x < 0) {
                    location.x(-x);
                } else {
                    location.x(width - (x - width));
                }
                speed.x( -speed.x() );
            }
        }
    }

    protected Rect getBoundingBox() {
        int halfWidth = this.width / 2;
        int halfHeight = this.height / 2;
        PhysicsVector location = location();
        int x = (int) location.x();
        int y = (int) location.y();

        return new Rect(
                x - halfWidth,
                y - halfHeight,
                x + halfWidth,
                y + halfHeight
        );
    }

    protected void setSpeed(PhysicsVector speed) {
        this.speed = speed;
    }

    protected PhysicsVector getSpeed() {
        return speed;
    }

    protected PhysicsVector location(int x, int y) {
        PhysicsVector location = new PhysicsVector(x, y);
        this.location = location;
        return location;
    }

    protected PhysicsVector location() {
        return location;
    }
}
