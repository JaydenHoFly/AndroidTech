package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2017/11/9.
 */

public class FlappyBird extends SurfaceView
        implements SurfaceHolder.Callback,
        Runnable {
    public FlappyBird(Context context) {
        super(context);
        init();
    }

    public FlappyBird(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlappyBird(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private SurfaceHolder mSurfaceHolder = null;
    private Canvas mCanvas = null;
    private Paint mPaint = null;
    private Bitmap mBgBitmap = null;
    private Rect mGamePanelRect = null;


    //bird
    private Bird mBird = null;

    //floor
    private Floor mFloor = null;

    //pipe
    private List<Pipe> mPipes = null;

    private int mSpeed = 5;

    private boolean mIsDrawing;
    private ObjectAnimator mBricksMoveAnimator = null;

    private void init() {
        initData();
        initView();
    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mBgBitmap = loadBitmapFromResource(R.mipmap.bg1);

//        mBricksMoveAnimator = ObjectAnimator.ofFloat(this, "", 0, 100);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private void initData() {
        mGamePanelRect = new Rect();

        mBird = new Bird();
        mBird.setBitmap(loadBitmapFromResource(R.mipmap.b1));

        mFloor = new Floor();
        mFloor.setBrickBitmap(loadBitmapFromResource(R.mipmap.floor_bg2));

        mPipes = new ArrayList<>();
        Pipe pipe1 = new Pipe(mGamePanelRect.width(), mGamePanelRect.height());
        mPipes.add(pipe1);

    }

    private Bitmap loadBitmapFromResource(int b1) {
        return BitmapFactory.decodeResource(getResources(), b1);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            try {
                draw();
            } catch (Exception ignored) {

            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGamePanelRect.set(0, 0, w, h);
        mBird.set(w / 2, h * 2 / 3);
        mFloor.setGamePanelSize(mGamePanelRect.width(), mGamePanelRect.height());
    }

    private void draw() {
        mCanvas = mSurfaceHolder.lockCanvas();
        drawBackground();
        drawBird();
        drawFloor();
        drawPipes();
        mFloor.setX(mFloor.getX() - mSpeed);
        for (Pipe p : mPipes) {
            p.setX(p.getX() - mSpeed);
        }
        if (mCanvas != null)
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
    }

    private void drawPipes() {
        for (Pipe p : mPipes) {
            p.draw(mCanvas, mPaint);
        }
    }

    private void drawFloor() {
        mFloor.draw(mCanvas, mPaint);
    }

    private void drawBird() {
        mBird.draw(mCanvas, mPaint);
    }

    private void drawBackground() {
        mCanvas.drawBitmap(mBgBitmap, null, mGamePanelRect, mPaint);
    }
}
