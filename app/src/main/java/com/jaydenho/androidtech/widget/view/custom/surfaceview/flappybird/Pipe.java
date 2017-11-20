package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.jaydenho.androidtech.util.MathUtils;

import java.util.Random;

/**
 * Created by hedazhao on 2017/11/13.
 */

public class Pipe implements IGameComponent {

    private static final String TAG = "flappy.pipe";
    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = 2;

    private static final float UP_PIPE_HEIGHT_MAX_RATIO = 4 / 5F;
    private static final float UP_PIPE_HEIGHT_MIN_RATIO = 2 / 5F;

    private int mUpPipeMaxHeight = 0;
    private int mUpPipeMinHeight = 0;

    private int x = 0;
    private int y = 0;

    private Bitmap mUpBitmap = null;
    private Bitmap mDownBitmap = null;

    private HalfPipe mUpPipe = null;
    private HalfPipe mDownPipe = null;

    private int mPipeWidth = 0;
    public static final float WIDTH_RATIO = 1 / 7F;

    private static final float GAP_RATIO = 1 / 5F;
    private int mGap = 0;

    private Pipe mLastPipe = null;

    private FlappyBird mFlappyBird = null;

    private static final float DELTA_GAP_BETWEEN_NEIGHBOR_PIPE_MAX_RATIO = 1 / 5F;
    private int mMaxDeltaGapBetweenNeighborPipe = 0;

    private final Rect[] mBodies = new Rect[]{new Rect(), new Rect()};

    public Pipe(FlappyBird flappyBird, Bitmap upBitmap, Bitmap downBitmap, Pipe lastPipe) {
        this.mFlappyBird = flappyBird;
        this.mUpBitmap = upBitmap;
        this.mDownBitmap = downBitmap;
        this.mLastPipe = lastPipe;

        mUpPipeMaxHeight = (int) (flappyBird.getGameActivityRect().height() * UP_PIPE_HEIGHT_MAX_RATIO);
        mUpPipeMinHeight = (int) (flappyBird.getGameActivityRect().height() * UP_PIPE_HEIGHT_MIN_RATIO);
        mGap = (int) (flappyBird.getGameActivityRect().height() * GAP_RATIO);
        mPipeWidth = (int) (WIDTH_RATIO * flappyBird.getGameActivityRect().width());
        mMaxDeltaGapBetweenNeighborPipe = (int) (DELTA_GAP_BETWEEN_NEIGHBOR_PIPE_MAX_RATIO * flappyBird.getGameActivityRect().height());
        generateAttrs();
    }

    private void generateAttrs() {
        x = mFlappyBird.getGameActivityRect().width();
        int upHeight = calcUpHeight();
        int downHeight = mFlappyBird.getGameActivityRect().height() - upHeight - mGap;
        Log.d(TAG, "upHeight: " + upHeight + " downHeight: " + downHeight);
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
                Log.d(TAG, "!isUpPipeHeightValid. upHeight: " + upHeight);
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
        return new Random().nextInt(mUpPipeMaxHeight);
    }

    public void draw(Canvas canvas, Paint paint) {
        mUpPipe.draw(canvas, paint);
        mDownPipe.draw(canvas, paint);
    }

    public HalfPipe getUpPipe() {
        return mUpPipe;
    }

    public HalfPipe getDownPipe() {
        return mDownPipe;
    }

    public void setX(int x) {
        this.x = x;
        calcBodies();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        calcBodies();
    }

    private void calcBodies() {
        mBodies[0].set(x, y, x + mPipeWidth, y + getUpPipe().getHeight());
        mBodies[1].set(x, y + getUpPipe().getHeight() + mGap, x + mPipeWidth, y + getUpPipe().getHeight() + mGap + getDownPipe().getHeight());
    }

    public boolean isFinished() {
        return getX() + mPipeWidth < 0;
    }

    @Override
    public void onGameCreate() {

    }

    @Override
    public void onGameDestroy() {

    }

    @Override
    public void onStatusChanged(@FlappyBird.Status int status) {

    }

    @Override
    public Rect[] getBodies() {
        return mBodies;
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
                canvas.translate(x, mFlappyBird.getGameActivityRect().height() - mHeight);
                canvas.drawBitmap(mBitmap, null, new Rect(0, 0, mPipeWidth, mHeight), paint);
            }
            canvas.restore();
        }
    }

    public static class PipeFactory {
        private Bitmap mUpBitmap = null;
        private Bitmap mDownBitmap = null;

        private Pipe mLastPipe = null;

        private FlappyBird mFlappyBird = null;

        public PipeFactory(FlappyBird flappyBird, Bitmap upBitmap, Bitmap downBitmap) {
            mFlappyBird = flappyBird;
            mUpBitmap = upBitmap;
            mDownBitmap = downBitmap;
        }

        public Pipe generatePipe() {
            Pipe pipe = new Pipe(mFlappyBird, mUpBitmap, mDownBitmap, mLastPipe);
            mLastPipe = pipe;
            return pipe;
        }
    }
}
