package com.grizzle.redrover;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by grizzle on 7/12/15.
 */
public class SteeringWheel implements Renderable, Touchable {
    private Paint paint;
    private int x;
    private int y;
    private final int RADIUS = 75;
    private int touchX;
    private int direction;
    private boolean touching = false;

    private Paint indicatorPaint;

    public SteeringWheel(int x, int y) {
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);

        indicatorPaint = new Paint();
        indicatorPaint.setColor(Color.MAGENTA);
        indicatorPaint.setStrokeWidth(2f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, RADIUS, paint);

        if (touching) {
            if (direction < 0) {
                canvas.drawCircle(x - 50, y, 50, indicatorPaint);
            } else {
                canvas.drawCircle(x + 50, y, 50, indicatorPaint);
            }
        }
    }

    @Override
    public boolean isTouching(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        int xDist = Math.abs(x - this.x);
        int yDist = Math.abs(y - this.y);
        System.out.println(xDist + ", " + yDist);

        return xDist <= RADIUS && yDist <= RADIUS;
    }

    @Override
    public void onTouchDown(MotionEvent motionEvent) {
        paint.setStyle(Paint.Style.FILL);
        touching = true;
        touchX = (int) motionEvent.getX();
    }

    @Override
    public void onTouchMove(MotionEvent motionEvent) {
        int xDist = touchX - (int) motionEvent.getX();
        if (xDist > 0) {
            direction = -1;
        } else {
            direction = 1;
        }
    }

    @Override
    public void onTouchUp(MotionEvent motionEvent) {
        paint.setStyle(Paint.Style.STROKE);
        touching = false;
        touchX = 0;
    }

    @Override
    public boolean isBeingTouched() {
        return this.touching;
    }

    public int getDirection() {
        return direction;
    }
}
