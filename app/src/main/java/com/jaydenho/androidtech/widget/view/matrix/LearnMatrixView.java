package com.jaydenho.androidtech.widget.view.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/7/4.
 */

public class LearnMatrixView extends View {

    private static final String TAG = "View.LearnMatrixView";

    public LearnMatrixView(Context context) {
        this(context, null);
    }

    public LearnMatrixView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LearnMatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Matrix mMatrix = null;
    private Bitmap mBitmap = null;

    private void init() {
        mMatrix = new Matrix();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blue_pool);

        mMatrix.reset();
        mMatrix.preScale(2, 4);
        Log.d(TAG,"drawJet matrix2: " + mMatrix.toString());
        mMatrix.postTranslate(100, 200);
        Log.d(TAG,"drawJet matrix3: " + mMatrix.toString());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
