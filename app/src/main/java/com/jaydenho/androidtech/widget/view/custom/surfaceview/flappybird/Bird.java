package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by hedazhao on 2017/11/13.
 */

public class Bird implements IGameComponent {
    private static final String TAG = "flappy.bird";
    private int mBirdWidth = 0;
    private int mBirdHeight = 0;
    private Point mCenterPoint = null;
    private Bitmap mBitmap = null;

    private final Rect[] mBodies = new Rect[]{new Rect()};

    public static final float DROP_Y_RATIO = 1 / 20F;
    private FlappyBird mFlappyBird = null;

    public Bird(FlappyBird flappyBird, Bitmap birdBitmap, int width, int height) {
        mFlappyBird = flappyBird;
        mBitmap = birdBitmap;
        mBirdWidth = width;
        mBirdHeight = height;
        mCenterPoint = new Point();
    }

    /**
     * @param moveY positive will let bird move up. negative is opposite
     */
    public void setMoveY(int moveY) {
        setY(getY() - moveY);
    }

    public void setX(int x) {
        Log.d(TAG, "x: " + x);
        mCenterPoint.x = x;
        calcBodies();
    }

    public void setY(int y) {
        Log.d(TAG, "y: " + y);
        mCenterPoint.y = y;
        calcBodies();
    }

    private void calcBodies() {
        mBodies[0].set(mCenterPoint.x - mBirdWidth / 2, mCenterPoint.y - mBirdHeight / 2, mCenterPoint.x + mBirdWidth / 2, mCenterPoint.y + mBirdHeight / 2);
    }

    public int getX() {
        return mCenterPoint.x;
    }

    public int getY() {
        return mCenterPoint.y;
    }

    @Override
    public Rect[] getBodies() {
        return mBodies;
    }

    public void set(int x, int y) {
        setX(x);
        setY(y);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmap, null, new Rect(mCenterPoint.x, mCenterPoint.y, mCenterPoint.x + mBirdWidth, mCenterPoint.y + mBirdHeight), paint);
    }

    @Override
    public void onGameCreate() {

    }

    @Override
    public void onGameDestroy() {

    }

    @Override
    public void onStatusChanged(int status) {
        switch (status) {
            case FlappyBird.STATUS_PREPARE:
                break;
            case FlappyBird.STATUS_RUNNING:
                break;
            case FlappyBird.STATUS_STOP:
                break;
        }
    }
}
