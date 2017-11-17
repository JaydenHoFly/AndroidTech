package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.animation.LinearInterpolator;

/**
 * Created by hedazhao on 2017/11/13.
 */

public class Bird implements IGameComponent {
    private static final int WIDTH = 34;
    private static final int HEIGHT = 24;
    private static final int DROP_Y = -5;
    private Point mPositionPoint = null;
    private Bitmap mBitmap = null;

    private int mGamePanelHeight = 0;
    private int mActivityHeight = 0;

    public static final float DROP_Y_RATIO = 1 / 20F;
    private int mDropY = 0;
    private boolean mIsGameAlive = false;
    private FlappyBird mFlappyBird = null;

    public Bird(FlappyBird flappyBird, int gamePanelHeight) {
        this.mFlappyBird = flappyBird;
        mGamePanelHeight = gamePanelHeight;
        mPositionPoint = new Point();

        initAttrs();
        initDrop();
    }

    private void initAttrs() {
        int floorHeight = (int) ((1 - Floor.FLOOR_Y_POSITION_RATIO) * mGamePanelHeight);
        mActivityHeight = mGamePanelHeight - floorHeight;
        mDropY = (int) (DROP_Y_RATIO * mActivityHeight);
        ;
    }

    private void initDrop() {
        mDropAnimator = ObjectAnimator.ofObject(this, "moveY", new IntEvaluator(), DROP_Y);
        mDropAnimator.setInterpolator(new LinearInterpolator());
        mDropAnimator.setDuration(1000);
        mDropAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void setMoveY(int moveY) {
        setY(getY() + moveY);
    }

    public void setX(int x) {
        mPositionPoint.x = x;
    }

    public void setY(int y) {
        mPositionPoint.y = y;
    }

    public int getX() {
        return mPositionPoint.x;
    }

    public int getY() {
        return mPositionPoint.y;
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

    private ObjectAnimator mDropAnimator = null;

    public void startDrop() {
        mDropAnimator.start();
    }

    public void stopDrop() {
        mDropAnimator.end();
    }

    @Override
    public void onGameCreate() {
        mIsGameAlive = true;
    }

    @Override
    public void onGameDestroy() {
        mIsGameAlive = false;
    }

    @Override
    public void onStatusChanged(int status) {
        switch (status) {
            case FlappyBird.STATUS_PREPARE:
                break;
            case FlappyBird.STATUS_RUNNING:
                startDrop();
                break;
            case FlappyBird.STATUS_STOP:
                stopDrop();
                break;
        }
    }
}
