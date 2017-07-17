package com.jaydenho.androidtech.widget.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hedazhao on 2017/7/14.
 */

public class CircleMenuView extends ViewGroup {

    private static final String TAG = "CircleMenuView";
    private int mMenuCount = 0;
    /**
     * 内圆半径,in pixels
     */
    private int mInnerCircleRadius = 100;
    private int mOuterCircleRadius = 200;

    private Paint mPaint = null;

    /**
     * 圆心坐标
     */
    private Point mCentrePoint = null;


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
        mCentrePoint = new Point();
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        setWillNotDraw(false);
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
            measureChild(child, menuWidthHeightMeasureSpec, menuWidthHeightMeasureSpec);
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
        mCentrePoint.set(getWidth() / 2, getHeight() / 2);
        //calc the distance between menu-icon's central and circle's central.
        int ringWidth = mOuterCircleRadius - mInnerCircleRadius;
        //让所有菜单icon的中心点处于一个圆上，该圆半径：mOuterCircleRadius - RingWidth / 2，并且和大小圆是同心圆
        int menuCentreCircleRadius = mOuterCircleRadius - ringWidth / 2;
        //根据三角函数，可以计算出每个菜单icon中心点的坐标
        //相邻菜单的角度
        float singleAngle = mMenuCount == 0 ? 0 : 360 / (mMenuCount);
        Log.d(TAG, "onLayout, singleAngle: " + singleAngle);
        //从0度角开始布局
        float startAngle = 0;
        for (int i = 0; i < mMenuCount; i++) {
            View child = getChildAt(i);
            //交点p1的x轴坐标=圆心的x + p1距离圆心的x轴距离(分正负)
            int x = (int) (menuCentreCircleRadius * Math.cos(Math.toRadians(startAngle))) + mCentrePoint.x;
            int y = (int) (menuCentreCircleRadius * Math.sin(Math.toRadians(startAngle))) + mCentrePoint.y;
            Log.d(TAG, "onLayout, x: " + x + " y: " + y);
            //根据菜单icon的中心点坐标得到菜单icon的layout参数
            setChildFrame(child, x, y);
            startAngle += singleAngle;
        }
    }

    private void setChildFrame(View child, int menuCentreX, int menuCentreY) {
        int left = menuCentreX - child.getMeasuredWidth() / 2;
        int top = menuCentreY - child.getMeasuredHeight() / 2;
        child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        //draw outerCircle
        canvas.drawCircle(mCentrePoint.x, mCentrePoint.y, mOuterCircleRadius, mPaint);
        mPaint.setColor(Color.GREEN);
        //draw innerCircle, can replace with a bitmap
        canvas.drawCircle(mCentrePoint.x, mCentrePoint.y, mInnerCircleRadius, mPaint);

   /*     //move the canvas to center of the view, it's convince for calculate coordinates.
        canvas.translate(getWidth() / 2, getHeight() / 2);*/
    }
}
