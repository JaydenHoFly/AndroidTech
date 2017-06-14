package com.jaydenho.androidtech.widget.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hedazhao on 2017/6/14.
 */

public class SimpleLinearLayout extends ViewGroup {
    public SimpleLinearLayout(Context context) {
        super(context);
    }

    public SimpleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        int height = 0;
        int width = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            height = Math.max(child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin, height);
            width += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
        }
        setMeasuredDimension(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY ? measureWidth : Math.min(measureWidth, width + getPaddingLeft() + getPaddingRight()),
                MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY ? measureHeight : Math.min(measureHeight, height + getPaddingTop() + getPaddingBottom()));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int leftOffset = l + getPaddingLeft();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            leftOffset += lp.leftMargin;
            setChildFrame(child, leftOffset, t + lp.topMargin + getPaddingTop(), child.getMeasuredWidth(), child.getMeasuredHeight());
            leftOffset += child.getMeasuredWidth() + lp.rightMargin;
        }
    }

    private void setChildFrame(View child, int left, int top, int childWidth, int childHeight) {
        child.layout(left, top, left + childWidth, top + childHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
