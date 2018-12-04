package com.jaydenho.androidtech.widget.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import com.jaydenho.androidtech.R;

/**
 * 通过手势操作图片。
 * Created by hedazhao on 2017/7/5.
 */

public class ZoomImageView extends android.support.v7.widget.AppCompatImageView
        implements ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "view.ZoomImageView";
    private static final float SCALE_MAX = 4;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;
    private ScaleGestureDetector mScaleGestureDetector = null;
    private GestureDetector mGestureDetector = null;
    private final Matrix mMatrix = new Matrix();

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    private boolean once = false;
    private float mPreX = 0;
    private float mPreY = 0;

    private int mTouchSlop = 0;
    private boolean mIsDrag = false;

    private static final int SCALE_LEVEL_ORIGIN = 1;
    private static final int SCALE_LEVEL_1 = 2;
    private int mScaleLevel = SCALE_LEVEL_ORIGIN;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ZoomImageView);
        float f = t.getFloat(R.styleable.ZoomImageView_j_zoom_corner, 0.3f);
        Log.d(TAG, "corner: " + f);
        t.recycle();
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mScaleLevel == SCALE_LEVEL_ORIGIN) {
                    //双击放大
                    RectF rectF = getDrawableMatrixRectF();
                    float scale;
                    if (rectF.width() > rectF.height()) {
                        //宽>高，让高度满屏
                        scale = getHeight() / rectF.height();
                    } else {
                        scale = getWidth() / rectF.width();
                    }
                    mMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
                    setImageMatrix(mMatrix);
                    mScaleLevel = SCALE_LEVEL_1;
                } else if (mScaleLevel == SCALE_LEVEL_1) {
                    //双击正常

                    mScaleLevel = SCALE_LEVEL_ORIGIN;
                }
                return super.onDoubleTap(e);
            }
        });
        setOnTouchListener(this);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleX = getScaleX();
        float scaleY = getScaleY();
        float scaleFactor = detector.getScaleFactor();

        Log.d(TAG, "onScale scale: " + scale + " scaleX: " + scaleX + " scaleY: " + scaleY + " scaleFactor: " + scaleFactor);

        if (getDrawable() == null) {
            return true;
        }

        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > initScale && scaleFactor < 1.0f)) {
            if (scaleFactor * scale < initScale) {
                //scaleFactor为本次postScale操作的因子，之前的缩放值是scale，postScale之后的scale值：scaleFactor*scale, 不能小于initScale(不支持缩小)
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorder();
            setImageMatrix(mMatrix);
        }

        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleEnd");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        float currentX = event.getX();
        float currentY = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouch getX(): " + currentX + " getRawX(): " + event.getRawX());
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = currentX - mPreX;
                float dy = currentY - mPreY;
                if (!mIsDrag) {
                    mIsDrag = isDrag(dx, dy);
                }
                if (mIsDrag) {
                    boolean isXDrag = true;
                    boolean isYDrag = true;
                    RectF rectF = getDrawableMatrixRectF();
                    if (rectF.width() < getWidth()) {
                        dx = 0;
                        isXDrag = false;
                    }
                    if (rectF.height() < getHeight()) {
                        dy = 0;
                        isYDrag = false;
                    }
                    mMatrix.postTranslate(dx, dy);
                    checkBorderWhenDrag(isXDrag, isYDrag);
                    setImageMatrix(mMatrix);
                    Log.d(TAG, "------------------");
                    Log.d(TAG, "getX(): " + getX() + " getY(): " + getY());
                    Log.d(TAG, "getLeft() " + getLeft() + " getTop(): " + getTop());
                    Rect rect1 = new Rect();
                    getLocalVisibleRect(rect1);
                    Rect rect2 = new Rect();
                    getGlobalVisibleRect(rect2);
                    Log.d(TAG, "localVisibleRect " + rect1.toString() + " globalVisibleRect: " + rect2.toString());
                    Log.d(TAG, "event.getX(): " + event.getX() + " event.getY() " + event.getY());
                    Log.d(TAG, "event.getRawX(): " + event.getRawX() + " event.getRawY() " + event.getRawY());
                    Log.d(TAG, "------------------");
                }
                break;
        }
        mPreX = currentX;
        mPreY = currentY;
        return mScaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onGlobalLayout() {
        if (!once) {
            initDrawable();
            once = true;
        }
    }

    private void initDrawable() {
        Drawable d = getDrawable();
        int dw = d.getIntrinsicWidth();
        int dh = d.getIntrinsicHeight();
        final int width = getWidth();
        final int height = getHeight();

        float scale = 1.0f;
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        } else if (dh > height && dw <= width) {
            scale = height * 1.0f / dh;
        } else if (dw > width && dh > height) {
            //UNDONE max or min
            scale = Math.max(width * 1.0f / dw, height * 1.0f / dh);
        }
        initScale = scale;
        mMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
        mMatrix.postScale(scale, scale, width / 2, height / 2);
        setImageMatrix(mMatrix);
        Log.d(TAG, "dw: " + dw + " dh: " + dh);
        Log.d(TAG, "width: " + width + " height: " + height);
        Log.d(TAG, "scale: " + scale);
        Log.d(TAG, "onGlobalLayout matrix: " + mMatrix.toString());
    }

    private boolean isDrag(float dx, float dy) {
        return Math.sqrt((dx * dx + dy * dy)) > mTouchSlop;
    }

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private void checkBorder() {
        RectF rectF = getDrawableMatrixRectF();
        final int width = getWidth();
        final int height = getHeight();
        float deltaX = 0;
        float deltaY = 0;
        // 如果宽或高大于屏幕，则控制范围
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }
        if (rectF.width() < width) {
            deltaX = (width - rectF.width()) / 2 - rectF.left;
        }
        if (rectF.height() < height) {
            deltaY = (height - rectF.height()) / 2 - rectF.top;
        }
        Log.d(TAG, "checkBorder deltaX: " + deltaX + " deltaY: " + deltaY);
        mMatrix.postTranslate(deltaX, deltaY);
    }

    private void checkBorderWhenDrag(boolean isXDrag, boolean isYDrag) {
        RectF rectF = getDrawableMatrixRectF();
        float dx = 0;
        float dy = 0;
        //x轴方向拖动了而且拖动后图片上边缘在屏幕内top>0
        if (rectF.top > 0 && isYDrag) {
            //让Matrix往上移动top的距离,使图片上边缘和屏幕上边缘重合,不能让图片上边缘进入屏幕内
            dy = -rectF.top;
        }
        if (rectF.bottom < getHeight() && isYDrag) {
            dy = getHeight() - rectF.bottom;
        }
        if (rectF.left > 0 && isXDrag) {
            dx = -rectF.left;
        }
        if (rectF.right < getWidth() && isXDrag) {
            dx = getWidth() - rectF.right;
        }
        mMatrix.postTranslate(dx, dy);
    }

    private RectF getDrawableMatrixRectF() {
        Drawable drawable = getDrawable();
        RectF rectF = new RectF();
        if (drawable == null) return rectF;
        rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mMatrix.mapRect(rectF);
        return rectF;
    }
}
