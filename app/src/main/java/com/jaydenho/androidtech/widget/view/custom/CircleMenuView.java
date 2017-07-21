package com.jaydenho.androidtech.widget.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
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
    /**
     * 绘制时的初始角度，手指移动的角度会不断改变初始角度，从而产生转盘随手指转动的效果
     */
    private float mStartAngle = 0;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

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
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
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
        //相邻菜单的角度
        float perAngle = mMenuCount == 0 ? 0 : 360 / (mMenuCount);
        //从初始角度逆时针开始布局
        Log.d(TAG, "onLayout, mStartAngle: " + mStartAngle);
        float angle = mStartAngle % 360;
        Log.d(TAG, "onLayout, angle: " + angle);
        for (int i = 0; i < mMenuCount; i++) {
            View child = getChildAt(i);
            //根据三角函数，可以计算出每个菜单icon中心点的坐标
            int x = (int) (menuCentreCircleRadius * Math.cos(Math.toRadians(angle))) + mCentrePoint.x;
            int y = (int) (menuCentreCircleRadius * Math.sin(Math.toRadians(angle))) + mCentrePoint.y;
            Log.d(TAG, "onLayout, x: " + x + " y: " + y);
            //根据菜单icon的中心点坐标得到菜单icon的layout参数
            setChildFrame(child, x, y);
            angle += perAngle;
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
    }

    private float mPreTouchX;
    private float mPreTouchY;

    private float mCurrentTouchX;
    private float mCurrentTouchY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mCurrentTouchX = ev.getX();
        mCurrentTouchY = ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                double dx2 = Math.pow(Math.abs(mCurrentTouchX - mPreTouchX), 2);
                double dy2 = Math.pow(Math.abs(mCurrentTouchY - mPreTouchY), 2);
                double d = Math.sqrt(dx2 + dy2);
                if (d > mTouchSlop) {
                    Log.d(TAG, "interceptTouchEvent. d: " + d);
                    return true;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentTouchX = event.getX();
        mCurrentTouchY = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                double currentAngle = getAngle(mCurrentTouchX, mCurrentTouchY);
                Log.d(TAG, "onTouchEvent currentAngle: " + currentAngle);
                double preAngle = getAngle(mPreTouchX, mPreTouchY);
                Log.d(TAG, "onTouchEvent preAngle: " + preAngle);
                float dAngle = (float) (currentAngle - preAngle);
                Log.d(TAG, "onTouchEvent dAngle: " + dAngle);
                //由于三角函数坐标系的y轴方向和控件坐标系的y轴方向是相反的，
                mStartAngle = mStartAngle - dAngle;
                requestLayout();
                break;
        }
        mPreTouchX = mCurrentTouchX;
        mPreTouchY = mCurrentTouchY;
        return true;
    }

    /**
     * 以圆中心点建立直角坐标系，根据sin值反向得到触摸点与圆中心点连线和x轴形成的夹角
     */
    private float getAngle(float touchX, float touchY) {
        double x = touchX - mCentrePoint.x;
        //android坐标系的y轴和直角坐标系相反
        double y = mCentrePoint.y - touchY;
        return (float) Math.toDegrees(Math.asin(y / Math.hypot(x,y)));
    }

    private int getQuadrant(float x, float y) {
        float dx = x - mCentrePoint.x;
        float dy = y - mCentrePoint.y;
        if (dx > 0) {
            if (dy > 0) {
                return 1;
            } else {
                return 4;
            }
        } else {
            if (dy > 0) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}
