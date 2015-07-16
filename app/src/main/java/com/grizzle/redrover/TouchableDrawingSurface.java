package com.grizzle.redrover;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class TouchableDrawingSurface extends SurfaceView {
    private ArrayList<DrawableObject> drawableObjects = new ArrayList<>();
    private ArrayList<Touchable> touchables = new ArrayList<>();
    private ArrayList<MotionEvent> touchEvents = new ArrayList<>();
    private GameLoopThread gameLoopThread;

    private Paint touchPaint;

    public TouchableDrawingSurface(Context context) {
        super(context);
        createTouchPaint();
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

    private void createTouchPaint() {
        touchPaint = new Paint();
        touchPaint.setColor(Color.CYAN);
        touchPaint.setStrokeWidth(1f);
        touchPaint.setStyle(Paint.Style.STROKE);
    }

    private void createUI() {
        for (int i = 0; i < 6; i++) {
            add(dudeFactory(i * 100, 100));
        }
    }

    private Dude dudeFactory(int x, int y) {
        int[] DUDES = {
            R.drawable.dude1,
            R.drawable.dude2,
            R.drawable.dude3,
            R.drawable.dude4,
            R.drawable.dude5,
            R.drawable.dude6,
            R.drawable.dude7
        };

        Random random = new Random();
        int index = random.nextInt(DUDES.length);

        return new Dude(this, x, y, random.nextInt(2) + 1, DUDES[index]);
    }

    private void add(DrawableObject drawableObject) {
        drawableObjects.add(drawableObject);
        if (drawableObject instanceof Touchable) {
            touchables.add((Touchable) drawableObject);
        }
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            clearCanvas(canvas);

//            DEBUG
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1f);
            canvas.drawRect(0, 0, 50, 50, paint);

            handlePendingTouchEvents(canvas);

            update();

            for (DrawableObject drawableObject : drawableObjects) {
                drawableObject.draw(canvas);
            }
        }
    }

    private void update() {
//        TODO: interogate steering wheel to get direction, use that to steer moving dude
//        int steeringDirection = this.steeringWheel.getDirection();

        for (DrawableObject drawableObject : drawableObjects) {
//            if (drawableObject instanceof Dude) {
//                ((Dude) drawableObject).steer(steeringDirection);
//            }
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
        if (canvas != null) {
            canvas.drawCircle(motionEvent.getX(), motionEvent.getY(), 30f, touchPaint);
            canvas.drawPoint(motionEvent.getX(), motionEvent.getY(), touchPaint);
        }
    }

    private ArrayList<Touchable> getCurrentlyTouching() {
        ArrayList<Touchable> currentlyTouching = new ArrayList<>();

        for (Touchable touchable : touchables) {
            if (touchable.isBeingTouched()) {
                currentlyTouching.add(touchable);
            }
        }

        return currentlyTouching;
    }

    private void handleTouchDown(MotionEvent motionEvent) {
        ArrayList<Touchable> currentlyTouching = getCurrentlyTouching();

        for (Touchable touchable : touchables) {
            if (currentlyTouching.size() == 0 && touchable.isTouching(motionEvent)) {
                touchable.onTouchDown(motionEvent);
//                System.out.println("touching " + touchable.getClass());
            }
        }
    }

    private void handleTouchMove(MotionEvent motionEvent) {
        ArrayList<Touchable> currentlyTouching = getCurrentlyTouching();

        for (Touchable touchable : currentlyTouching) {
            touchable.onTouchMove(motionEvent);
        }
    }

    private void handleTouchUp(MotionEvent motionEvent) {
        ArrayList<Touchable> currentlyTouching = getCurrentlyTouching();

        for (Touchable touchable : currentlyTouching) {
            touchable.onTouchUp(motionEvent);
//            System.out.println("releasing " + touchable.getClass());
        }
    }
}
