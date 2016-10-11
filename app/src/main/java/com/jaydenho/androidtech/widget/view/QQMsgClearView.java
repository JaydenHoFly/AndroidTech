package com.jaydenho.androidtech.widget.view;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/10/9.
 */
public class QQMsgClearView extends FrameLayout {

    private static final String TAG = QQMsgClearView.class.getSimpleName();
    private Paint mPaint = null;
    private float mOriginX;
    private float mOriginY;
    private float mOriginR;
    private float mMoveX;
    private float mMoveY;
    private float mMoveR;

    private float mP0X;
    private float mP0Y;
    private float mP1X;
    private float mP1Y;
    private float mP2X;
    private float mP2Y;
    private float mP3X;
    private float mP3Y;
    private float mP4X;
    private float mP4Y;

    private float mCenterX;
    private float mCenterY;
    /**
     * 关键角度的弧度值
     */
    private double mAlphaAngle;
    private Path mPath = null;

    private boolean isTouch;

    private TextView mTipTV = null;

    private boolean isBoom;
    private static final float mBoomLength = 200;

    private boolean isDisappear;

    private ObjectAnimator mBoomAnimator = null;
    private int mBoomResId;
    private boolean isDrawBoom;

    public QQMsgClearView(Context context) {
        this(context, null);
    }

    public QQMsgClearView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQMsgClearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        resetPaint();
        int saveCount = canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), mPaint, Canvas.ALL_SAVE_FLAG);
        if (!isDisappear) {
            if (!isBoom) {
                drawOriginCircle(canvas);
                if (isTouch) {
                    setTipTVPosition(mMoveX, mMoveY);
                    drawMoveCircle(canvas);
                    calcPath();
                    canvas.drawPath(mPath, mPaint);
                } else {
                    setTipTVPosition(mOriginX, mOriginY);
                }
            } else {
                if (isTouch) {
                    setTipTVPosition(mMoveX, mMoveY);
                } else {
                    mTipTV.setVisibility(View.GONE);
                    isBoom = false;
                    isDisappear = true;
                    mBoomAnimator.start();
                }
            }
        }
        if (isDrawBoom) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBoomResId);
            if(bitmap != null){
                canvas.drawBitmap(bitmap, mMoveX, mMoveY, mPaint);
            }
            isDrawBoom = false;
        }
        canvas.restoreToCount(saveCount);
        super.dispatchDraw(canvas);
    }

    private void setTipTVPosition(float centerX, float centerY) {
        mTipTV.setX(centerX - mTipTV.getWidth() / 2);
        mTipTV.setY(centerY - mTipTV.getHeight() / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int[] tipTVLocation = new int[2];
                mTipTV.getLocationOnScreen(tipTVLocation);
                Rect tipTvRect = new Rect(tipTVLocation[0] - mTipTV.getWidth(), tipTVLocation[1] - mTipTV.getHeight(), tipTVLocation[0] + mTipTV.getWidth(), tipTVLocation[1] + mTipTV.getHeight());
                isTouch = tipTvRect.contains((int) event.getRawX(), (int) event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouch && !isBoom) {
                    isBoom = Math.sqrt(Math.pow(Math.abs(mMoveX - mOriginX), 2) + Math.pow(Math.abs(mMoveY - mOriginY), 2)) >= mBoomLength;
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }
        if (!isDisappear) {
            mMoveX = event.getX();
            mMoveY = event.getY();
            postInvalidate();
        }
        return true;
    }

    private void calcPath() {
        mAlphaAngle = Math.atan((mMoveY - mOriginY) / (mMoveX - mOriginX));
        Log.d(TAG, "mAlphaAngle: " + mAlphaAngle);
        mP0X = mMoveX + (float) (mMoveR * Math.sin(mAlphaAngle));
        mP0Y = mMoveY - (float) (mMoveR * Math.cos(mAlphaAngle));

        mP1X = mOriginX + (float) (mOriginR * Math.sin(mAlphaAngle));
        mP1Y = mOriginY - (float) (mOriginR * Math.cos(mAlphaAngle));

        mP2X = mOriginX - (float) (mOriginR * Math.sin(mAlphaAngle));
        mP2Y = mOriginY + (float) (mOriginR * Math.cos(mAlphaAngle));

        mP3X = mMoveX - (float) (mMoveR * Math.sin(mAlphaAngle));
        mP3Y = mMoveY + (float) (mMoveR * Math.cos(mAlphaAngle));

        mCenterX = (mMoveX + mOriginX) / 2;
        mCenterY = (mMoveY + mOriginY) / 2;
        Log.d(TAG, "mCenterX: " + mCenterX + " mCenterY: " + mCenterY);

        mPath.reset();
        mPath.moveTo(mP0X, mP0Y);
        mPath.quadTo(mCenterX, mCenterY, mP1X, mP1Y);
        mPath.lineTo(mP2X, mP2Y);
        mPath.quadTo(mCenterX, mCenterY, mP3X, mP3Y);
        mPath.lineTo(mP0X, mP0Y);
    }

    private void drawMoveCircle(Canvas canvas) {
        canvas.drawCircle(mMoveX, mMoveY, mMoveR, mPaint);
    }

    private void drawOriginCircle(Canvas canvas) {
        canvas.drawCircle(mOriginX, mOriginY, mOriginR, mPaint);
    }

    private void setBoomImageResource(int resId) {
        mBoomResId = resId;
        isDrawBoom = true;
        postInvalidate();
    }

    private void initBoomAnimator() {
        PropertyValuesHolder firstHolder = PropertyValuesHolder.ofKeyframe("boomImageResource", Keyframe.ofInt(0.2f, R.mipmap.idp), Keyframe.ofInt(0.4f, R.mipmap.idq), Keyframe.ofInt(0.6f, R.mipmap.idr), Keyframe.ofInt(0.8f, R.mipmap.ids), Keyframe.ofInt(1f, R.mipmap.idt));
        mBoomAnimator = ObjectAnimator.ofPropertyValuesHolder(this, firstHolder);
        mBoomAnimator.setInterpolator(new LinearInterpolator());
        mBoomAnimator.setDuration(500);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mOriginX = 500;
        mOriginY = 500;
        mOriginR = 20;
        mMoveR = 20;
        mPath = new Path();

        mTipTV = new TextView(context);
        LayoutParams tipTVLP = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTipTV.setBackgroundResource(R.drawable.qq_msg_clear_tv);
        mTipTV.setText("99+");
        addView(mTipTV, tipTVLP);

        initBoomAnimator();
    }

    private void resetPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
    }
}
