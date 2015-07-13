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
    final int RADIUS = 75;

    public SteeringWheel(int x, int y) {
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, RADIUS, paint);
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
    }

    @Override
    public void onTouchMove(MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(MotionEvent motionEvent) {
        paint.setStyle(Paint.Style.STROKE);
    }
}
