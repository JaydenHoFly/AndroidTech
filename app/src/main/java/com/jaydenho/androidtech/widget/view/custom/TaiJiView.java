package com.jaydenho.androidtech.widget.view.custom;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

/**
 * Created by hedazhao on 2017/7/13.
 */

public class TaiJiView extends View
        implements ViewTreeObserver.OnGlobalLayoutListener {

    private int mRadius = 200;
    private int mArcRadius = mRadius / 2;
    private int mPointRadius = mRadius / 8;

    private Paint mBlackPaint = null;
    private Paint mWhitePaint = null;

    private RectF mBigArcRecF = null;
    private int mRotate = 0;
    private ObjectAnimator mRotateAnimator = null;

    public TaiJiView(Context context) {
        this(context, null);
    }

    public TaiJiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaiJiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBlackPaint = new Paint();
        mBlackPaint.setColor(Color.BLACK);
        mBlackPaint.setAntiAlias(true);
        mBlackPaint.setStyle(Paint.Style.FILL);

        mWhitePaint = new Paint();
        mWhitePaint.setColor(Color.WHITE);
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setStyle(Paint.Style.FILL);

        mBigArcRecF = new RectF();

        mRotateAnimator = ObjectAnimator.ofObject(this, "JRotate", new IntEvaluator(), 0, 360);
        mRotateAnimator.setInterpolator(new LinearInterpolator());
        mRotateAnimator.setDuration(2000);
        mRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void setJRotate(int rotate) {
        this.mRotate = rotate;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        canvas.restore();
        canvas.rotate(mRotate, centerX, centerY);

        mBigArcRecF.set(centerX - mRadius, centerY - mRadius, centerX + mRadius, centerY + mRadius);
        canvas.drawArc(mBigArcRecF, 90, 180, false, mWhitePaint);

        canvas.drawArc(mBigArcRecF, 270, 180, false, mBlackPaint);

        canvas.drawCircle(centerX, centerY - mArcRadius, mArcRadius, mBlackPaint);

        canvas.drawCircle(centerX, centerY + mArcRadius, mArcRadius, mWhitePaint);

        canvas.drawCircle(centerX, centerY - mArcRadius, mPointRadius, mWhitePaint);

        canvas.drawCircle(centerX, centerY + mArcRadius, mPointRadius, mBlackPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        mRotateAnimator.cancel();
    }

    @Override
    public void onGlobalLayout() {
        mRotateAnimator.start();
    }
}
