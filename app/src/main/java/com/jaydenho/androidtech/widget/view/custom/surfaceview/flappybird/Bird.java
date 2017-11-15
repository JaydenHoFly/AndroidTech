package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by hedazhao on 2017/11/13.
 */

public class Bird {
    private static final int WIDTH = 34;
    private static final int HEIGHT = 24;
    private Point mPositionPoint = null;
    private Bitmap mBitmap = null;

    public Bird() {
        mPositionPoint = new Point();
    }

    public void setX(int x) {
        mPositionPoint.x = x;
    }

    public void setY(int y) {
        mPositionPoint.y = y;
    }

    public void set(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmap, mPositionPoint.x, mPositionPoint.y, paint);
    }
}
