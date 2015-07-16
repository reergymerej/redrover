package com.grizzle.redrover;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by grizzle on 7/15/15.
 */
public class Ball extends DrawableObject implements Touchable {
    private Random random = new Random();
    private float radius;
    final private Paint paint = new Paint();
    private SurfaceView surfaceView;


    public Ball(
            SurfaceView surfaceView,
            int x,
            int y) {
        super(surfaceView, 0, x, y, 1);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        setColor();

        radius = random.nextInt(25) + 20;
    }

    private void setColor() {
        int[] COLORS = {
                Color.RED,
                Color.WHITE,
                Color.BLUE
        };

        paint.setColor(COLORS[random.nextInt(COLORS.length)]);
    }

    @Override
    protected void createBitmap(int drawableResource) {
    }

    private void slowDown() {
        PhysicsVector speed = this.getSpeed();

        speed.multiplyScalar(0.9);

        if (Math.abs(speed.x()) < 1) {
            speed.x(0);
        }

        if (Math.abs(speed.y()) < 1) {
            speed.y(0);
        }
    }

    @Override
    protected void update() {
        super.update();
        this.slowDown();
    }

    @Override
    protected void draw(Canvas canvas) {
        update();

        canvas.drawCircle(
                (float) location.x(),
                (float) location.y(),
                radius,
                paint);
    }

    @Override
    public boolean isTouching(MotionEvent motionEvent) {
        double xDist = Math.abs(motionEvent.getX() - location.x());
        double yDist = Math.abs(motionEvent.getY() - location.y());

        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2)) <= radius;
    }

    @Override
    public void onTouchDown(MotionEvent motionEvent) {
        Random random = new Random();
        setSpeed(new PhysicsVector(random.nextInt(200) - 100, random.nextInt(200) - 100));
    }

    @Override
    public void onTouchMove(MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(MotionEvent motionEvent) {

    }

    @Override
    public boolean isBeingTouched() {
        return false;
    }
}
