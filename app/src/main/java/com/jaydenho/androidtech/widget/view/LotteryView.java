package com.jaydenho.androidtech.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.View;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/10/7.
 * 实现刮刮乐效果
 */
public class LotteryView extends View {

    private Paint mPaint = null;
    private Bitmap mLotterySrcBitmap = null;
    private Bitmap mDstBitmap = null;
    private Bitmap mFactBitmap = null;
    private int mWidth = 1000;
    private int mHeight = 400;
    private Canvas mDstCanvas = null;
    private Path mFingerPath = null;
    private float mPreX;
    private float mPreY;

    public LotteryView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        initBitmap();
        mFingerPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawLottery(canvas);
    }

    private void drawLottery(Canvas canvas) {
        mPaint.setStrokeWidth(45);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawBitmap(mFactBitmap, 0, 0, mPaint);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        mDstCanvas.drawPath(mFingerPath, mPaint);
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mLotterySrcBitmap, 0, 0, mPaint);

        canvas.restoreToCount(layerId);
    }

    private void initBitmap() {
        mLotterySrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_lottery);
        mFactBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blue_pool);

        mWidth = mLotterySrcBitmap.getWidth();
        mHeight = mLotterySrcBitmap.getHeight();
        mDstBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mDstCanvas = new Canvas(mDstBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                mFingerPath.moveTo(mPreX, mPreY);
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (event.getX() + mPreX) / 2;
                float endY = (event.getY() + mPreY) / 2;
                mFingerPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    private void initPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }
}
