package com.jaydenho.androidtech.widget.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/9/25.
 */
public class BasicAttrsView extends View {

    private static final String TAG = BasicAttrsView.class.getSimpleName();
    private Paint mPaint = null;
    private Path mFingerPath = null;
    private float mFingerX;
    private float mFingerY;
    private float mPreFingerX;
    private float mPreFingerY;

    private Path mWavePath = null;
    private int mWaveLength = 1000;
    private int mWaveStartHeight = 0;
    private int mWaveEndHeight = 300;
    private ObjectAnimator mWaveXAnimator = null;
    private ObjectAnimator mWaveYAnimator = null;
    private int mWaveX;
    private int mWaveY;

    private ObjectAnimator mDashPathMoveAnimator = null;
    private int mDashPathPhase = 0;

    private Bitmap mColorMatrixBitmap = null;

    public BasicAttrsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        closeHardAccelerate();
        mPaint = new Paint();
        mFingerPath = new Path();
        mWavePath = new Path();
      /*  startWaveAnimator();
        startDashPathMoveAnimator();*/
        initColorMatrixBitmap();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
        mWaveStartHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawA(canvas);
    }

    private void drawA(Canvas canvas) {
        int centerX = 200;
        int centerY = 200;
        String text = "texttexttext";
        mPaint.setTextSize(80);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawCircle(centerX, centerY, centerX, mPaint);
        Rect textRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), textRect);
        canvas.drawText(text, centerX, centerY + textRect.height() / 2, mPaint);
    }

    private void learnCanvas(Canvas canvas) {
        canvas.drawColor(Color.RED);
        int id1 = canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.rotate(40);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(100, 100, 200, 200, mPaint);
        canvas.restoreToCount(id1);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 200, 200, mPaint);
    }

    private void drawColorMatrix(Canvas canvas) {
        initPaint();
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setARGB(255, 200, 100, 100);
        Rect rect = new Rect(100, 100, 500, 300);
        canvas.drawRect(rect, mPaint);

        canvas.translate(500, 0);

        ColorMatrix blueColorMatrix = new ColorMatrix(new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(blueColorMatrix));
        canvas.drawRect(rect, mPaint);
    }

    private void drawPoolColorMatrix(Canvas canvas) {
        initPaint();
        Rect rect = new Rect(0, 0, mColorMatrixBitmap.getWidth(), mColorMatrixBitmap.getHeight());

        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawBitmap(mColorMatrixBitmap, null, rect, mPaint);

        canvas.translate(0, mColorMatrixBitmap.getHeight() + 100);
        oppositeFilter();
        canvas.drawBitmap(mColorMatrixBitmap, null, rect, mPaint);
    }

    private void oppositeFilter() {
        mPaint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{//反色
                0, 1, 0, 0, 0,
                1, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        })));
    }

    private void grayFilter() {
        mPaint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{//灰白照片
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0, 0, 0, 1, 0,
        })));
    }

    private void initColorMatrixBitmap() {
        mColorMatrixBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blue_pool);
    }

    private void drawDashPath(Canvas canvas) {
        Path path = new Path();
        path.moveTo(200, 500);
        path.lineTo(500, 200);
        path.lineTo(800, 500);
        mPaint.setPathEffect(null);
        mPaint.setARGB(255, 255, 0, 0);
        canvas.drawPath(path, mPaint);

        mPaint.setARGB(255, 0, 255, 0);
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10, 100, 100}, mDashPathPhase));
        canvas.translate(0, 30);
        canvas.drawPath(path, mPaint);

        mPaint.setARGB(255, 0, 0, 255);
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10, 100, 100}, 15));
        canvas.translate(0, 30);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 行走的斑马线
     */
    private void startDashPathMoveAnimator() {
        mDashPathMoveAnimator = ObjectAnimator.ofInt(this, "dashPathPhase", 0, 230);
        mDashPathMoveAnimator.setDuration(1000);
        mDashPathMoveAnimator.setInterpolator(new LinearInterpolator());
        mDashPathMoveAnimator.setRepeatMode(ValueAnimator.RESTART);
        mDashPathMoveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mDashPathMoveAnimator.start();
    }

    public void setDashPathPhase(int phase) {
        mDashPathPhase = phase;
        postInvalidate();
    }

    private void drawJoin(Canvas canvas) {
        mPaint.setStrokeWidth(80);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(300, 100);
        path.lineTo(100, 400);
        canvas.drawPath(path, mPaint);
    }

    private void drawWave(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePath.reset();
        mWavePath.moveTo(-mWaveLength + mWaveX, mWaveY);
        int halfWaveLength = mWaveLength / 2;
        for (int i = -mWaveLength; i < getWidth() + mWaveLength; i += mWaveLength) {
            mWavePath.rQuadTo(halfWaveLength / 2, 100, halfWaveLength, 0);
            mWavePath.rQuadTo(halfWaveLength / 2, -100, halfWaveLength, 0);
        }
        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }

    private void startWaveAnimator() {
        PropertyValuesHolder waveXHolder = PropertyValuesHolder.ofInt("waveX", 0, mWaveLength);
        mWaveXAnimator = ObjectAnimator.ofPropertyValuesHolder(this, waveXHolder);
        mWaveXAnimator.setDuration(2000);
        mWaveXAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        PropertyValuesHolder waveYHolder = PropertyValuesHolder.ofInt("waveY", mWaveStartHeight, mWaveEndHeight);
        mWaveYAnimator = ObjectAnimator.ofPropertyValuesHolder(this, waveYHolder);
        mWaveYAnimator.setDuration(1000 * 10);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(mWaveXAnimator, mWaveYAnimator);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    public void setWaveX(int waveX) {
        this.mWaveX = waveX;
        postInvalidate();
    }

    public void setWaveY(int waveY) {
        this.mWaveY = waveY;
        postInvalidate();
    }

    private void drawBezier(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(100, 100);
        path.quadTo(160, 0, 200, 120);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 绘制文字的最大矩形框和最小矩形框
     *
     * @param canvas
     */
    private void drawTextBounds(Canvas canvas) {
        int textX = 200;
        int textY = 200;
        String text = "frog";
        mPaint.setTextSize(80);

        mPaint.setARGB(255, 0, 0, 255);
        mPaint.setStyle(Paint.Style.FILL);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();//最大矩形
        Rect maxRect = new Rect(textX, fontMetricsInt.top + textY, (int) (textX + mPaint.measureText(text, 0, text.length())), fontMetricsInt.bottom + textY);
        canvas.drawRect(maxRect, mPaint);

        Rect minRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), minRect);//最小矩形
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(minRect.left + textX, minRect.top + textY, minRect.right + textX, minRect.bottom + textY, mPaint);

        mPaint.setARGB(255, 0, 255, 0);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(textX, textY, 800, textY, mPaint);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, textX, textY, mPaint);
    }

    private void drawArc(Canvas canvas) {
        mPaint.setARGB(255, 0, 255, 0);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(0, 0, 200, 200);
        canvas.drawArc(rectF, -90, 180, false, mPaint);
    }

    private void moveCanvas(Canvas canvas) {
        mPaint.setARGB(255, 0, 255, 0);
        RectF rectF = new RectF(0, 0, 400, 200);
        canvas.drawRect(rectF, mPaint);

        mPaint.setARGB(255, 255, 0, 0);
        canvas.translate(200, 100);
        canvas.drawRect(rectF, mPaint);

        mPaint.setARGB(255, 0, 0, 255);
        canvas.rotate(90);
        canvas.drawRect(rectF, mPaint);
    }

    private void drawText(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(80);
        canvas.drawText("你好", 0, 80, mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("你好", 200, 40, mPaint);
    }

    private void drawPath(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, 300);
        path.lineTo(400, 300);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawGeometry(Canvas canvas) {
        canvas.drawLine(0, 0, 100, 150, mPaint);
        mPaint.setARGB(255, 0, 255, 0);
        float[] pts = new float[]{0, 0, 150, 150, 200, 200, 400, 400};
        canvas.drawLines(pts, 4, 4, mPaint);
        RectF ovalRectF = new RectF(0, 400, 300, 600);
        canvas.drawRect(ovalRectF, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawOval(ovalRectF, mPaint);
    }

    private void drawByFinger(Canvas canvas) {
        canvas.drawPath(mFingerPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE://为了实现随手指移动画出轨迹
                float endX = (mPreFingerX + event.getX()) / 2;
                float endY = (mPreFingerY + event.getY()) / 2;
                mFingerPath.quadTo(mPreFingerX, mPreFingerY, endX, endY);
                postInvalidate();
                mPreFingerX = event.getX();
                mPreFingerY = event.getY();
                break;
            case MotionEvent.ACTION_DOWN:
                mFingerPath.moveTo(event.getX(), event.getY());
                mPreFingerX = event.getX();
                mPreFingerY = event.getY();
                break;
        }
        return true;
    }

    private void initPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void closeHardAccelerate() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }
}
