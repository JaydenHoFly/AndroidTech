package com.jaydenho.androidtech.widget.view.custom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by hedazhao on 2017/7/14.
 * 注意Android中的坐标系，Y轴方向和直角坐标系的Y轴相反，要换算。
 */

public class CircleMenuView extends ViewGroup
        implements GestureDetector.OnGestureListener {

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

    private GestureDetector mGestureDetector = null;

    private float mPreTouchX;
    private float mPreTouchY;

    private float mCurrentTouchX;
    private float mCurrentTouchY;

    private ObjectAnimator mFlingAnimator = null;

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
        mGestureDetector = new GestureDetector(context, this);
        mFlingAnimator = ObjectAnimator.ofFloat(this, "startAngle", 0);
        mFlingAnimator.setDuration(800);
        mFlingAnimator.setInterpolator(new DecelerateInterpolator());
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
        float angle = mStartAngle %= 360;
        Log.d(TAG, "onLayout, angle: " + angle);
        for (int i = 0; i < mMenuCount; i++) {
            View child = getChildAt(i);
            //根据三角函数，可以计算出每个菜单icon中心点的坐标
            int x = (int) (menuCentreCircleRadius * Math.cos(Math.toRadians(angle))) + mCentrePoint.x;
            //计算出的值是基于直角坐标系的，需要转换成android坐标系
            int y = mCentrePoint.y - (int) (menuCentreCircleRadius * Math.sin(Math.toRadians(angle)));
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
        cancelFling();
        if (mGestureDetector.onTouchEvent(event)) {
            Log.d(TAG, "onTouchEvent, mGestureDetector consumed touch event");
            return true;
        }
        mCurrentTouchX = event.getX();
        mCurrentTouchY = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                float preAngle = getAngle(mCentrePoint, mPreTouchX, mPreTouchY);
                Log.d(TAG, "onTouchEvent preAngle: " + preAngle);
                float currentAngle = getAngle(mCentrePoint, mCurrentTouchX, mCurrentTouchY);
                Log.d(TAG, "onTouchEvent currentAngle: " + currentAngle);
                int preQuadrant = getQuadrant(mCentrePoint, mPreTouchX, mPreTouchY);
                Log.d(TAG, "onTouchEvent preQuadrant: " + preQuadrant);
                int currentQuadrant = getQuadrant(mCentrePoint, mCurrentTouchX, mCurrentTouchY);
                Log.d(TAG, "onTouchEvent currentQuadrant: " + currentQuadrant);
                //转过的角度，逆时针转动为正值，顺时针转动为负值
                float dAngle = 0;
                //根据getAngle方法得出的角度，逆时针，1象限:[0,90],2象限:[90,0],3象限:[0,-90],4象限:[-90,0]
                if (preQuadrant == currentQuadrant) {
                    //如果前后两个触摸点都在同一个象限
                    if (currentQuadrant == 1 || currentQuadrant == 4) {
                        //1,4象限,角度值逆时针递增
                        dAngle = currentAngle - preAngle;
                    } else {
                        //2,3象限,角度值逆时针递减，而逆时针转动dAngle应该是正值，所以交换减
                        dAngle = preAngle - currentAngle;
                    }
                } else {
                    if ((preQuadrant == 1 && currentAngle == 2) || (preQuadrant == 3 && currentAngle == 4)) {
                        dAngle = (90 - Math.abs(currentAngle)) + (90 - Math.abs(preAngle));
                    } else if ((preQuadrant == 2 && currentAngle == 1) || (preQuadrant == 4 && currentAngle == 3)) {
                        dAngle = -((90 - Math.abs(currentAngle)) + (90 - Math.abs(preAngle)));
                    } else if ((preQuadrant == 2 && currentAngle == 3) || (preQuadrant == 4 && currentAngle == 1)) {
                        dAngle = Math.abs(currentAngle) + Math.abs(preAngle);
                    } else if ((preQuadrant == 3 && currentAngle == 2) || (preQuadrant == 1 && currentAngle == 4)) {
                        dAngle = -(Math.abs(currentAngle) + Math.abs(preAngle));
                    }
                }
                Log.d(TAG, "onTouchEvent dAngle: " + dAngle);
                //由于三角函数坐标系的y轴方向和控件坐标系的y轴方向是相反的，
                setStartAngleOffset(dAngle);
                break;
        }
        mPreTouchX = mCurrentTouchX;
        mPreTouchY = mCurrentTouchY;
        return true;
    }

    private void cancelFling() {
        mFlingAnimator.cancel();
    }

    public void setStartAngleOffset(float offsetAngle) {
        setStartAngle(mStartAngle + offsetAngle);
    }

    public void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
        requestLayout();
    }

    /**
     * 以圆中心点建立直角坐标系，根据sin值反向得到触摸点与圆中心点连线和x轴形成的夹角
     */
    private float getAngle(Point centrePoint, float touchX, float touchY) {
        float x = touchX - centrePoint.x;
        //android坐标系的y轴和直角坐标系相反
        float y = touchY - centrePoint.y;
        return getAngle(x, y);
    }

    private float getAngle(float x, float y) {
        y = -y;
        return (float) Math.toDegrees(Math.asin(y / Math.hypot(x, y)));
    }

    private int getQuadrant(Point centrePoint, float x, float y) {
        float dx = x - centrePoint.x;
        float dy = y - centrePoint.y;
        return getQuadrant(dx, dy);
    }

    private int getQuadrant(float x, float y) {
        y = -y;
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling. e1.getX: " + e1.getX() + " e1.getY: " + e1.getY() + " e2.getX: " + e2.getX() + " e2.getY: " + e2.getY());
        Log.d(TAG, "onFling. velocityX: " + velocityX + " velocityY: " + velocityY);
        float flingAngle = calcFlingAngle(e2.getX(), e2.getY(), velocityX, velocityY);
        Log.d(TAG, "onFling, flingAngle: " + flingAngle);
        mFlingAnimator.setFloatValues(mStartAngle, mStartAngle + flingAngle);
        mFlingAnimator.start();
        return true;
    }

    private float calcFlingAngle(float flingX, float flingY, float velocityX, float velocityY) {
        float flingValue = calcFlingValue(velocityX, velocityY);
        int flingDirect = calcFlingDirect(flingX, flingY, velocityX, velocityY);
        Log.d(TAG, "calcFlingAngle. flingValue: " + flingValue + " flingDirect: " + flingDirect);
        return flingDirect * 360 * flingValue / 10000;
    }

    private float calcFlingValue(float velocityX, float velocityY) {
        return (float) Math.hypot(Math.abs(velocityX), Math.abs(velocityY));
    }

    /**
     * 顺时针
     */
    private static final int FLING_DIRECT_CW = -1;
    /**
     * 逆时针
     */
    private static final int FLING_DIRECT_CCW = 1;

    /**
     * @param flingX
     * @param flingY
     * @param velocityX
     * @param velocityY
     * @return 1：逆时针、-1：顺时针。
     */
    private int calcFlingDirect(float flingX, float flingY, float velocityX, float velocityY) {
        //velocity是基于fling的点产生的，也就相当于将在fling点上施加和velocityX，velocityY方向相同的作用力
        //连接圆心和fling点，再基于fling点建立直角坐标系，进行受力分析，根据velocityX和velocityY的合力的方向，与圆心和fling点的连线进行分析，例如fling点在
        //第一象限，合力方向也在自身直角坐标系的第一象限，如果合力方向与x轴的夹角大于圆心和fling点的连线与x轴的夹角，那么圆就应该逆时针转动。
        int flingQuadrant = getQuadrant(mCentrePoint, flingX, flingY);
        int velocityQuadrant = getQuadrant(velocityX, velocityY);
        float flingAngle = Math.abs(getAngle(mCentrePoint, flingX, flingY));
        float velocityAngle = Math.abs(getAngle(velocityX, velocityY));
        if (flingQuadrant == 1) {
            if (velocityQuadrant == 1) {
                return velocityAngle > flingAngle ? FLING_DIRECT_CCW : FLING_DIRECT_CW;
            } else if (velocityQuadrant == 2) {
                return FLING_DIRECT_CCW;
            } else if (velocityQuadrant == 3) {
                return velocityAngle > flingAngle ? FLING_DIRECT_CW : FLING_DIRECT_CCW;
            } else {
                return FLING_DIRECT_CW;
            }
        } else if (flingQuadrant == 2) {
            if (velocityQuadrant == 1) {
                return FLING_DIRECT_CW;
            } else if (velocityQuadrant == 2) {
                return velocityAngle > flingAngle ? FLING_DIRECT_CW : FLING_DIRECT_CCW;
            } else if (velocityQuadrant == 3) {
                return FLING_DIRECT_CCW;
            } else {
                return velocityAngle > flingAngle ? FLING_DIRECT_CCW : FLING_DIRECT_CW;
            }
        } else if (flingQuadrant == 3) {
            if (velocityQuadrant == 1) {
                return velocityAngle > flingAngle ? FLING_DIRECT_CW : FLING_DIRECT_CCW;
            } else if (velocityQuadrant == 2) {
                return FLING_DIRECT_CW;
            } else if (velocityQuadrant == 3) {
                return velocityAngle > flingAngle ? FLING_DIRECT_CCW : FLING_DIRECT_CW;
            } else {
                return FLING_DIRECT_CCW;
            }
        } else {
            if (velocityQuadrant == 1) {
                return FLING_DIRECT_CCW;
            } else if (velocityQuadrant == 2) {
                return velocityAngle > flingAngle ? FLING_DIRECT_CCW : FLING_DIRECT_CW;
            } else if (velocityQuadrant == 3) {
                return FLING_DIRECT_CW;
            } else {
                return velocityAngle > flingAngle ? FLING_DIRECT_CW : FLING_DIRECT_CCW;
            }
        }
    }
}
