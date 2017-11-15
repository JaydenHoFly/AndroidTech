package com.jaydenho.androidtech.widget.view.custom.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jaydenho.androidtech.R;

public class SurfaceViewTemplate extends SurfaceView
        implements SurfaceHolder.Callback, Runnable {

    // SurfaceHolder
    private SurfaceHolder mHolder;
    // 用于绘图的Canvas
    private Canvas mCanvas;
    // 子线程标志位
    private boolean mIsDrawing;
    private Paint mPaint = null;
    private Point mCirclePoint = null;
    private static final Point CENTRAL_POINT = new Point(200, 200);
    private static final RectF CIRCLE_RECT = new RectF(0, 0, 400, 400);
    private float mLastAngle = 0f;
    private float mAngle = 0f;

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        mPaint = new Paint();
        mCirclePoint = new Point();
        //mHolder.setFormat(PixelFormat.OPAQUE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
            mLastAngle = mAngle;
            mAngle = mLastAngle + 2;
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            // draw sth
            mCanvas.drawColor(getResources().getColor(R.color.colorAccent));
            mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            mPaint.setStyle(Paint.Style.STROKE);
            mCanvas.drawArc(CIRCLE_RECT, mLastAngle, mAngle, false, mPaint);
        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}