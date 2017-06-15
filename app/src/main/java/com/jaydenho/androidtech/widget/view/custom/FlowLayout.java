package com.jaydenho.androidtech.widget.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hedazhao on 2017/6/14.
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int lineWidth = 0;
        int lineHeight = 0;
        int width = 0;
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //不用考虑已用宽度，因为可以换行
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, height);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > measureWidthSize - getPaddingLeft() - getPaddingRight()) {
                //需要换行
                //根据上一行的宽高更新总宽高
                //由于另起一行，所以要根据上一行的宽度lineWidth，更新最大的行宽
                width = Math.max(lineWidth, width);
                //将上一行的高度加到总高度上
                height += lineHeight;
                //更新下一行的宽高
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                //更新本行的宽高
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == getChildCount() - 1) {
                //最后一个元素，处于最后一行，在它之后不会再有元素，也就不会再触发换行，无法更新总宽高，所以要特殊处理；
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? measureWidthSize : Math.min(measureWidthSize, width + getPaddingLeft() + getPaddingRight()),
                heightMode == MeasureSpec.EXACTLY ? measureHeightSize : Math.min(measureHeightSize, height + getPaddingTop() + getPaddingBottom()));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int leftOffset = getPaddingLeft();
        int topOffset = getPaddingTop();

        int lineHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();

            int childWidth = childMeasuredWidth + lp.leftMargin + lp.rightMargin;
            int childHeight = childMeasuredHeight + lp.topMargin + lp.bottomMargin;
            if (leftOffset + childWidth + getPaddingRight() > width) {
                //需要换行
                //重置左偏移量
                leftOffset = getPaddingLeft();
                //更新顶部偏移量
                topOffset += lineHeight;
                lineHeight = childHeight;
            }
            setChildFrame(child, leftOffset + lp.leftMargin, topOffset + lp.topMargin, childMeasuredWidth, childMeasuredHeight);
            //更新child布局之后的偏移量
            leftOffset += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
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
