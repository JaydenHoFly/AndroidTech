package com.jaydenho.androidtech.widget.anim;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/9/23.
 */
public class LoveView extends View {

    private final Paint mPaint;
    private int mRadius;
    private int mCurRadius;
    private ColorStateList mColor;
    private int mCurColor;

    public LoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        getAttrs(context, attrs);
    }

    private void init() {

    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoveView);
        int indexCount = typedArray.getIndexCount();
        int index;
        int radius = 0;
        ColorStateList color = null;
        for (int i = 0; i < indexCount; i++) {
            index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.LoveView_j_radius:
                    radius = typedArray.getDimensionPixelOffset(index, 10);
                    break;
                case R.styleable.LoveView_j_color:
                    color = typedArray.getColorStateList(index);
                    break;
            }
        }
        typedArray.recycle();
        setColor(color != null ? color : ColorStateList.valueOf(0xFF000000));
        setRadius(radius);
    }

    private void initPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mColor.getColorForState(getDrawableState(), 0));
    }

    public void setRadius(int radiusPixel) {
        mRadius = radiusPixel;
        updateRadius();
    }

    public void setColor(ColorStateList color) {
        if (color == null) {
            throw new NullPointerException("color is null");
        }
        mColor = color;
        updateColor();
    }

    private void updateRadius() {
        boolean inval = false;
        if (mRadius != mCurRadius) {
            mCurRadius = mRadius;
            inval = true;
        }
        if (inval) {
            invalidate();
        }
    }

    private void updateColor() {
        boolean inval = false;
        int color = mColor.getColorForState(getDrawableState(), 0);
        if (color != mCurColor) {
            mCurColor = color;
            inval = true;
        }
        if (inval) {
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mCurColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(500, 500, mRadius, mPaint);
    }
}
