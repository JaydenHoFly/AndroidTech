package com.jaydenho.androidtech.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/9/25.
 */
public class BasicAttrsView extends View {

    private Paint mPaint = null;

    public BasicAttrsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
        moveCanvas(canvas);
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
}
