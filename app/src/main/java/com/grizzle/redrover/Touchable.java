package com.grizzle.redrover;

import android.view.MotionEvent;

public interface Touchable {

    public boolean isTouching(MotionEvent motionEvent);
    public void onTouchDown(MotionEvent motionEvent);
    public void onTouchMove(MotionEvent motionEvent);
    public void onTouchUp(MotionEvent motionEvent);
}
