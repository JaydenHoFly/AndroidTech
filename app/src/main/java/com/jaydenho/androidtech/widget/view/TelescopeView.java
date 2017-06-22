package com.jaydenho.androidtech.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/6/22.
 */

public class TelescopeView extends View {

    private Paint mPaint = null;
    private Bitmap mSceneBitmap = null;
    private Bitmap mBgBitmap = null;
    private Point mFingerPoint = null;
    private int mRadius = 200;
    private BitmapShader mBitmapShader = null;

    public TelescopeView(Context context) {
        this(context, null);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(5);
        mSceneBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.splash_default_bird);
        mFingerPoint = new Point(-1, -1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBgBitmap == null) {
            mBgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasBg = new Canvas(mBgBitmap);
            //只是将mSceneBitmap通过canvasBg绘制在了mBgBitmap上，并没有绘制在屏幕上，这步操作目的是生成一个和控件大小一致的Bitmap
            //无论你利用绘图函数绘多大一块，在哪绘制，与Shader无关。因为Shader总是在控件的左上角开始，而你绘制的部分只是显示出来的部分而已。没有绘制的部分虽然已经生成，但只是不会显示出来罢了。
            canvasBg.drawBitmap(mSceneBitmap, null, new Rect(0, 0, getWidth(), getHeight()), mPaint);
            mBitmapShader = new BitmapShader(mBgBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
        if (mFingerPoint.x != -1 && mFingerPoint.y != -1) {
            mPaint.setShader(mBitmapShader);
            canvas.drawCircle(mFingerPoint.x, mFingerPoint.y, mRadius, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mFingerPoint.set((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mFingerPoint.set((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                mFingerPoint.set(-1, -1);
                break;
        }
        postInvalidate();
        return true;
    }
}
