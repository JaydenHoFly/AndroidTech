package com.jaydenho.androidtech.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.jaydenho.androidtech.R;

import java.lang.ref.WeakReference;

/**
 * Created by hedazhao on 2017/6/23.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap mMaskBitmap = null;
    private Paint mPaint = null;
    private float mCorner;
    private RectF mRectF = null;
    private WeakReference<Bitmap> mBitmapWR = null;
    private static final Xfermode XFERMODE = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        initAttrs(attrs);
        updateSize();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void updateSize() {
        mRectF = new RectF(0, 0, getWidth(), getHeight());
    }

    private void initAttrs(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            mCorner = typedArray.getFloat(R.styleable.CircleImageView_j_corner, 10);
            typedArray.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bitmap = mBitmapWR != null ? mBitmapWR.get() : null;
        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, getWidth(), getHeight());
            drawable.draw(bitmapCanvas);
            if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                mMaskBitmap = getMaskBitmap();
            }

            mPaint.reset();
            mPaint.setXfermode(XFERMODE);
            mPaint.setFilterBitmap(false);
            bitmapCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);

            mBitmapWR = new WeakReference<>(bitmap);
        }

        mPaint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }

    protected Bitmap getMaskBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
        return bitmap;
    }

}
