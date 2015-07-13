package com.grizzle.redrover;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
    private TouchableDrawingSurface touchableDrawingSurface;
    private int framesPerSecond;
    private boolean running = false;

    public GameLoopThread(TouchableDrawingSurface touchableDrawingSurface, int framesPerSecond) {
        this.touchableDrawingSurface = touchableDrawingSurface;
        this.framesPerSecond = framesPerSecond;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / framesPerSecond;
        long startTime;
        long sleepTime;

        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();

            try {
                canvas = touchableDrawingSurface.getHolder().lockCanvas();
                synchronized (touchableDrawingSurface.getHolder()) {
                    touchableDrawingSurface.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    touchableDrawingSurface.getHolder().unlockCanvasAndPost(canvas);
                }
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);

            try {
                if (sleepTime > 0) {
                    sleep(sleepTime);
                } else {
                    sleep(10);
                }
            } catch (Exception e) {}
        }
    }
}