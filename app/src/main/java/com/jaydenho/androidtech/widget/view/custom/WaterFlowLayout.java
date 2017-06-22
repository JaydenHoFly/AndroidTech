package com.jaydenho.androidtech.widget.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hedazhao on 2017/6/21.
 * 来源：http://blog.csdn.net/harvic880925/article/details/69787359
 */

public class WaterFlowLayout extends ViewGroup {

    private int mColumnCount = 3;
    private int mColumnSpace = 20;
    private int mRowSpace = 20;
    private int[] mTops;
    /**
     * 分配给child的宽度，child的margin和padding都包含在此宽度中。
     */
    private int mChildWidth = 0;

    public WaterFlowLayout(Context context) {
        this(context, null);
    }

    public WaterFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTops = new int[mColumnCount];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        mChildWidth = (measureWidth - (mColumnCount - 1) * mColumnSpace - getPaddingLeft() - getPaddingRight()) / mColumnCount;
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mChildWidth, MeasureSpec.EXACTLY);
        //noinspection UnnecessaryLocalVariable
        int childHeightMeasureSpec = heightMeasureSpec;

        int width;
        int height;

        clearTops();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //得出当前最短的列，本次测量的子控件将会添加至该列
            int minColumnIndex = getMinColumnIndex();
            //将固定的宽度和剩余的高度传递给子View进行测量
            measureChildWithMargins(child, childWidthMeasureSpec, 0, childHeightMeasureSpec, mTops[minColumnIndex]);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            mTops[minColumnIndex] += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin + mRowSpace;
        }
        if (childCount < mColumnCount) {
            width = mChildWidth * childCount + (childCount - 1) * mColumnSpace + getPaddingLeft() + getPaddingRight();
        } else {
            width = measureWidth;
        }
        height = mTops[getMaxColumnIndex()] + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(measureWidthMode == MeasureSpec.AT_MOST ? width : measureWidth, measureHeightMode == MeasureSpec.AT_MOST ? height : measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        clearTops();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //得出当前最短的列，本次测量的子控件将会添加至该列
            int minColumnIndex = getMinColumnIndex();
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int left = getPaddingLeft() + minColumnIndex * mChildWidth + minColumnIndex * mColumnSpace + lp.leftMargin;
            int top = getPaddingTop() + mTops[minColumnIndex] + lp.topMargin;
            setChildFrame(child, left, top, child.getMeasuredWidth(), child.getMeasuredHeight());
            mTops[minColumnIndex] += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin + mRowSpace;
        }
    }

    private void setChildFrame(View child, int left, int top, int childWidth, int childHeight) {
        child.layout(left, top, left + childWidth, top + childHeight);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(super.generateDefaultLayoutParams());
    }

    private void clearTops() {
        for (int i = 0; i < mTops.length; i++) {
            mTops[i] = 0;
        }
    }

    /**
     * 如果有两列高度相同，那么优先取左边的列，后续可添加配置
     */
    private int getMinColumnIndex() {
        int minColumnIndex = 0;
        for (int i = 0; i < mTops.length; i++) {
            if (mTops[minColumnIndex] > mTops[i]) {
                minColumnIndex = i;
            }
        }
        return minColumnIndex;
    }

    private int getMaxColumnIndex() {
        int maxColumnIndex = 0;
        for (int i = 0; i < mTops.length; i++) {
            if (mTops[maxColumnIndex] < mTops[i]) {
                maxColumnIndex = i;
            }
        }
        return maxColumnIndex;
    }
}
