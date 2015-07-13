package com.grizzle.redrover;

import android.graphics.Canvas;

public interface Renderable {

    public void update();
    public void draw(Canvas canvas);
}
