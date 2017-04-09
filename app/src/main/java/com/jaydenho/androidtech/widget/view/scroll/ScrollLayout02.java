package com.jaydenho.androidtech.widget.view.scroll;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Scroller;

/**
 * Created by hedazhao on 2017/2/10.
 */
public class ScrollLayout02 extends ViewGroup {

    private static final String TAG = ScrollLayout02.class.getSimpleName();
    private Scroller mScroller = null;
    private float mXDown;
    private float mXMove;
    private float mLastMove;
    private int mTouchSlop;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    public ScrollLayout02(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(getContext(), new AccelerateInterpolator());
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mLastMove = mXDown;
                mDownTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                mLastMove = mXMove;
                if (Math.abs(mXMove - mXDown) > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private float mXUp;
    private long mDownTime;
    private long mUpTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrollX = (int) (mLastMove - mXMove);
                Log.d(TAG, "scrollX: " + scrollX);
                if (getScrollX() + scrollX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrollX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrollX, 0);
                mLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                int targetIndex;
                mXUp = event.getRawX();
                mUpTime = System.currentTimeMillis();
                Log.d("ScrollLayout", "Math.abs((mXUp - mXDown)) / (mUpTime - mDownTime)" + (Math.abs((mXUp - mXDown)) / (mUpTime - mDownTime)));
                if ((Math.abs((mXUp - mXDown)) / (mUpTime - mDownTime)) > 0.1) {
                    if ((mXUp - mXDown) > 0) {
                        targetIndex = getScrollX() / getWidth() - 1;
                    } else {
                        targetIndex = getScrollX() / getWidth() + 1;
                    }
                } else {
                    // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                    targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                }
                int dx = targetIndex * getWidth() - getScrollX();
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(i * child.getMeasuredWidth(), 0, (i + 1) * child.getMeasuredWidth(), child.getMeasuredHeight());
        }
        // 初始化左右边界值
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
    }
}
