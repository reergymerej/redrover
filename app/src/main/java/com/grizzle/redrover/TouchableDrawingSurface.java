package com.grizzle.redrover;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class TouchableDrawingSurface extends SurfaceView {
    private ArrayList<Renderable> renderables = new ArrayList<>();
    private ArrayList<Touchable> touchables = new ArrayList<>();
    private ArrayList<MotionEvent> touchEvents = new ArrayList<>();
    private GameLoopThread gameLoopThread;
    private Touchable currentlyTouching;

    public TouchableDrawingSurface(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this, 30);

//        TODO: move this into its own class
        getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createUI();

                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }

    private void createUI() {
        add(new Dude(this, 100, 100));
    }

    private void add(Renderable renderable) {
        renderables.add(renderable);
        if (renderable instanceof Touchable) {
            touchables.add((Touchable) renderable);
        }
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }

    public void draw(Canvas canvas) {
        clearCanvas(canvas);

        handlePendingTouchEvents(canvas);

        for (Renderable renderable : renderables) {
            renderable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.touchEvents.add(event);
        return true;
    }

//    TODO: remove canvas from signature once we don't need to show touch indicator any more
    private void handlePendingTouchEvents(Canvas canvas) {

        for (MotionEvent event : touchEvents) {
//            DEBUG
            showTouch(event, canvas);

            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    handleTouchDown(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    handleTouchMove(event);
                    break;
                case MotionEvent.ACTION_UP:
                    handleTouchUp(event);
                    break;
            }
        }

        this.touchEvents.clear();
    }

    private void showTouch(MotionEvent motionEvent, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(motionEvent.getX(), motionEvent.getY(), 30f, paint);
    }

    private void handleTouchDown(MotionEvent motionEvent) {
        for (Touchable touchable : touchables) {
            if ((currentlyTouching != null && currentlyTouching != touchable) || touchable.isTouching(motionEvent)) {
                touchable.onTouchDown(motionEvent);
                currentlyTouching = touchable;
            }
        }

    }

    private void handleTouchMove(MotionEvent motionEvent) {
        if (currentlyTouching != null) {
            currentlyTouching.onTouchMove(motionEvent);
        }
    }

    private void handleTouchUp(MotionEvent motionEvent) {
        if (currentlyTouching != null) {
            currentlyTouching.onTouchUp(motionEvent);
            currentlyTouching = null;
        }
    }
}
