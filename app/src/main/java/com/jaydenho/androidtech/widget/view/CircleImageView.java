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
 * 方法一、先生成遮罩Bitmap，然后通过Xfermode的特性将遮罩和目标图像结合，得到圆角图片；
 * 方法二、利用BitmapShader（图章），用目标图片生成BitmapShader，然后利用带BitmapShader的Paint绘制各类图形，生成的图形中，底图就是目标图片；
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

    /**
     * Xfermode的坑：两个图形相交，形成最终图形，google还给出了各种Xfermode的效果，但是要注意，两个图形坐在的bitmap宽高必须一致，
     * 也就是说两个图形其实是一样大小的，只是周围的部分透明，如果两个图形大小不一致，效果就乱了。参考：https://blog.csdn.net/harvic880925/article/details/51264653
     */
    protected Bitmap getMaskBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
        return bitmap;
    }

}
