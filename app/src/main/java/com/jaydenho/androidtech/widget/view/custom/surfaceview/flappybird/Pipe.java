package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by hedazhao on 2017/11/13.
 */

public class Pipe {

    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = 2;

    private static final float UP_PIPE_HEIGHT_MAX_RATIO = 3 / 5F;
    private static final float DOWN_PIPE_HEIGHT_MAX_RATIO = 3 / 5F;

    private int mGamePanelWidth = 0;
    private int mGamePanelHeight = 0;

    private int mUpPipeMaxHeight = 0;
    private int mDownPipeMaxHeight = 0;

    private int x = 0;

    private Bitmap mUpBitmap = null;
    private Bitmap mDownBitmap = null;

    private HalfPipe mUpPipe = null;
    private HalfPipe mDownPipe = null;

    private static final float GAP_RATIO = 1 / 5F;
    private int mGap = 0;

    private boolean mIsActive = false;

    public Pipe(int gamePanelWidth, int gamePanelHeight) {
        this.mGamePanelWidth = gamePanelWidth;
        this.mGamePanelHeight = gamePanelHeight;
        mUpPipeMaxHeight = (int) (mGamePanelHeight * UP_PIPE_HEIGHT_MAX_RATIO);
        mDownPipeMaxHeight = (int) (mGamePanelHeight * DOWN_PIPE_HEIGHT_MAX_RATIO);
        mGap = (int) (mGamePanelHeight * GAP_RATIO);
        generateAttrs();
    }

    private void generateAttrs() {
        x = mGamePanelWidth;
        int upHeight = new Random().nextInt(mUpPipeMaxHeight);
        int downHeight = mGamePanelHeight - upHeight - mGap;
        mUpPipe = new HalfPipe(DIRECTION_UP, upHeight, mUpBitmap);
        mDownPipe = new HalfPipe(DIRECTION_DOWN, downHeight, mDownBitmap);
    }

    public void draw(Canvas canvas, Paint paint) {
        mUpPipe.draw(canvas, paint);
        mDownPipe.draw(canvas, paint);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    private class HalfPipe {
        private int mDirection = DIRECTION_UP;
        private int mWidth = 50;
        private int mHeight = 0;
        private Bitmap mBitmap = null;

        public HalfPipe(int direction, int height, Bitmap bitmap) {
            mDirection = direction;
            mHeight = height;
            mBitmap = bitmap;
        }

        public int getDirection() {
            return mDirection;
        }

        public boolean isUpPipe() {
            return getDirection() == DIRECTION_UP;
        }

        public int getHeight() {
            return mHeight;
        }

        public void setHeight(int height) {
            mHeight = height;
        }

        public void setBitmap(Bitmap bitmap) {
            mBitmap = bitmap;
        }

        public void draw(Canvas canvas, Paint paint) {
            canvas.save(Canvas.MATRIX_SAVE_FLAG);
            if (isUpPipe()) {
                canvas.translate(x, 0);
                canvas.drawBitmap(mBitmap, null, new Rect(0, 0, mWidth, mHeight), paint);
            } else {
                canvas.translate(x, mGamePanelHeight - Floor.FLOOR_Y_POSITION_RATIO * mGamePanelHeight - mHeight);
                canvas.drawBitmap(mBitmap, null, new Rect(0, 0, mWidth, mHeight), paint);
            }
            canvas.restore();
        }
    }
}
