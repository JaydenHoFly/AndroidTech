package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jaydenho.androidtech.util.MathUtils;

import java.util.Random;

/**
 * Created by hedazhao on 2017/11/13.
 */

public class Pipe {

    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = 2;

    private static final float UP_PIPE_HEIGHT_MAX_RATIO = 4 / 5F;
    private static final float UP_PIPE_HEIGHT_MIN_RATIO = 2 / 5F;

    private int mGamePanelWidth = 0;
    private int mGamePanelHeight = 0;

    /**
     * 管道的活动区域
     */
    private int mActivityWidth = 0;
    private int mActivityHeight = 0;

    private int mUpPipeMaxHeight = 0;
    private int mUpPipeMinHeight = 0;

    private int x = 0;

    private Bitmap mUpBitmap = null;
    private Bitmap mDownBitmap = null;

    private HalfPipe mUpPipe = null;
    private HalfPipe mDownPipe = null;

    private int mPipeWidth = 0;
    public static final float WIDTH_RATIO = 1 / 7F;

    private static final float GAP_RATIO = 1 / 5F;
    private int mGap = 100;

    private Pipe mLastPipe = null;

    private int mFloorHeight = 0;

    private boolean mIsActive = false;

    public Pipe(int gamePanelWidth, int gamePanelHeight, Bitmap upBitmap, Bitmap downBitmap, Pipe lastPipe) {
        this.mGamePanelWidth = gamePanelWidth;
        this.mGamePanelHeight = gamePanelHeight;
        this.mUpBitmap = upBitmap;
        this.mDownBitmap = downBitmap;
        this.mLastPipe = lastPipe;
        mFloorHeight = (int) ((1 - Floor.FLOOR_Y_POSITION_RATIO) * mGamePanelHeight);
        mActivityWidth = mGamePanelWidth;
        mActivityHeight = mGamePanelHeight - mFloorHeight;

        mUpPipeMaxHeight = (int) (mActivityWidth * UP_PIPE_HEIGHT_MAX_RATIO);
        mUpPipeMinHeight = (int) (mActivityHeight * UP_PIPE_HEIGHT_MIN_RATIO);
        mGap = (int) (mActivityHeight * GAP_RATIO);
        mPipeWidth = (int) (WIDTH_RATIO * mActivityWidth);
        mMaxDeltaGapBetweenNeighborPipe = (int) (DELTA_GAP_BETWEEN_NEIGHBOR_PIPE_MAX_RATIO * mActivityHeight);
        generateAttrs();
    }

    private static final float DELTA_GAP_BETWEEN_NEIGHBOR_PIPE_MAX_RATIO = 1 / 5F;
    private int mMaxDeltaGapBetweenNeighborPipe = 0;

    private void generateAttrs() {
        x = mGamePanelWidth;
        int upHeight = calcUpHeight();
        int downHeight = mActivityHeight - upHeight - mGap;
        mUpPipe = new HalfPipe(DIRECTION_UP, upHeight, mUpBitmap);
        mDownPipe = new HalfPipe(DIRECTION_DOWN, downHeight, mDownBitmap);
    }

    private int calcUpHeight() {
        int upHeight;
        if (mLastPipe != null) {
            boolean isGapHigherThanLastPipe = new Random().nextBoolean();
            int deltaGap = new Random().nextInt(mMaxDeltaGapBetweenNeighborPipe);
            upHeight = MathUtils.plusOrMinus(isGapHigherThanLastPipe, mLastPipe.getUpPipe().getHeight(), deltaGap);
            if (!isUpPipeHeightValid(upHeight)) {
                upHeight = MathUtils.plusOrMinus(!isGapHigherThanLastPipe, mLastPipe.getUpPipe().getHeight(), deltaGap);
            }
        } else {
            upHeight = generateUpPipeInitHeight();
        }
        return upHeight;
    }

    private boolean isUpPipeHeightValid(int upHeight) {
        return upHeight < mUpPipeMaxHeight && upHeight > mUpPipeMinHeight;
    }

    private int generateUpPipeInitHeight() {
        int upHeight;
        upHeight = new Random().nextInt(mUpPipeMaxHeight);
        return upHeight;
    }

    public void draw(Canvas canvas, Paint paint) {
        mUpPipe.draw(canvas, paint);
        mDownPipe.draw(canvas, paint);
    }

    public HalfPipe getUpPipe() {
        return mUpPipe;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public boolean isFinished() {
        return getX() + mPipeWidth < 0;
    }

    private class HalfPipe {
        private int mDirection = DIRECTION_UP;
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
                canvas.drawBitmap(mBitmap, null, new Rect(0, 0, mPipeWidth, mHeight), paint);
            } else {
                canvas.translate(x, mActivityHeight - mHeight);
                canvas.drawBitmap(mBitmap, null, new Rect(0, 0, mPipeWidth, mHeight), paint);
            }
            canvas.restore();
        }
    }

    public static class PipeFactory {
        private int mGamePanelWidth = 0;
        private int mGamePanelHeight = 0;
        private Bitmap mUpBitmap = null;
        private Bitmap mDownBitmap = null;

        private Pipe mLastPipe = null;

        public PipeFactory(int gamePanelWidth, int gamePanelHeight, Bitmap upBitmap, Bitmap downBitmap) {
            mGamePanelWidth = gamePanelWidth;
            mGamePanelHeight = gamePanelHeight;
            mUpBitmap = upBitmap;
            mDownBitmap = downBitmap;
        }

        public Pipe generatePipe() {
            Pipe pipe = new Pipe(mGamePanelWidth, mGamePanelHeight, mUpBitmap, mDownBitmap, mLastPipe);
            mLastPipe = pipe;
            return pipe;
        }
    }
}
