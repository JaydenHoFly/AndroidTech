package com.jaydenho.androidtech.widget.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hedazhao on 2017/7/14.
 */

public class CircleMenuView extends ViewGroup {

    private static final String TAG = "customView.CircleMenuView";
    private int mMenuCount = 0;
    /**
     * 内圆半径
     */
    private int mInnerCircleRadius = 100;
    private int mOuterCircleRadius = 150;

    private Paint mPaint = null;

    public CircleMenuView(Context context) {
        this(context, null);
    }

    public CircleMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        //根据内外圆半径差计算子控件的宽高
        int ringWidth = mOuterCircleRadius - mInnerCircleRadius;
        int menuWidthHeight = ringWidth - 20;
        int menuWidthHeightMeasureSpec = MeasureSpec.makeMeasureSpec(menuWidthHeight, MeasureSpec.AT_MOST);
        mMenuCount = getChildCount();
        for (int i = 0; i < mMenuCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, menuWidthHeightMeasureSpec, 0, menuWidthHeightMeasureSpec, 0);
        }

        int width = measureWidth;
        int height = measureHeight;
        if (measureWidthMode == MeasureSpec.AT_MOST) {
            width = getPaddingLeft() + getPaddingRight() + mOuterCircleRadius * 2;
        }
        if (measureHeightMode == MeasureSpec.AT_MOST) {
            height = getPaddingTop() + getPaddingBottom() + mOuterCircleRadius * 2;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float degree = mMenuCount == 0 ? 0 : 360 / mMenuCount;
        //calc the distance between menu' central and circle's central.
        int distanceToCenter = (mOuterCircleRadius - mInnerCircleRadius) / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        //draw outerCircle
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mOuterCircleRadius, mPaint);
        mPaint.setColor(Color.GREEN);
        //draw innerCircle, can replace with a bitmap
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mInnerCircleRadius, mPaint);

   /*     //move the canvas to center of the view, it's convince for calculate coordinates.
        canvas.translate(getWidth() / 2, getHeight() / 2);*/
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(super.generateDefaultLayoutParams());
    }
}
