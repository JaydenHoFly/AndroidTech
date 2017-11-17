package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jaydenho.androidtech.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hedazhao on 2017/11/9.
 */

public class FlappyBird extends SurfaceView
        implements SurfaceHolder.Callback,
        Runnable {

    private static final String TAG = "view.FlappyBird";

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
    private Bitmap mUpPipeBitmap = null;
    private Bitmap mDownPipeBitmap = null;
    private Rect mGamePanelRect = null;

    //bird
    private Bird mBird = null;

    //floor
    private Floor mFloor = null;

    //pipe
    private List<Pipe> mPipes = null;
    private Pipe.PipeFactory mPipeFactory = null;

    private int mSpeed = 5;

    private boolean mIsDrawing;

    @Status
    private int mStatus = STATUS_PREPARE;

    private List<IGameComponent> mGameComponents = null;

    private void init() {
        initData();
        initView();
    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mBgBitmap = loadBitmapFromResource(R.mipmap.bg1);
        mUpPipeBitmap = loadBitmapFromResource(R.mipmap.g2);
        mDownPipeBitmap = loadBitmapFromResource(R.mipmap.g1);

//        mBricksMoveAnimator = ObjectAnimator.ofFloat(this, "", 0, 100);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private void initData() {
        mGameComponents = new ArrayList<>();
        mGamePanelRect = new Rect();
    }

    private Bitmap loadBitmapFromResource(int b1) {
        return BitmapFactory.decodeResource(getResources(), b1);
    }

    private void setStatus(int status) {
        if (mStatus == status) return;
        mStatus = status;
        dispatchGameStatusChanged(mStatus);
    }

    @Status
    public int getStatus() {
        return mStatus;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
        onGameCreate();
    }

    private void onGameCreate() {
        generateBird();
        generateFloor();
        generatePipes();
        setStatus(STATUS_RUNNING);

        dispatchGameCreate();
    }

    private void onGameDestroy() {
        dispatchGameDestroy();
    }

    private void generateFloor() {
        mFloor = new Floor();
        mFloor.setBrickBitmap(loadBitmapFromResource(R.mipmap.floor_bg2));
        mFloor.setGamePanelSize(mGamePanelRect.width(), mGamePanelRect.height());
    }

    private void generateBird() {
        mBird = new Bird(this, mGamePanelRect.height());
        mBird.set(mGamePanelRect.width()/ 2, mGamePanelRect.hashCode() * 2 / 3);
        mBird.setBitmap(loadBitmapFromResource(R.mipmap.b1));
        mGameComponents.add(mBird);
    }

    private boolean isGameOver() {
        if(mBird.getY() >= mGamePanelRect.height() - mFloor.getFloorHeight()
    }

    private void generatePipes() {
        mPipes = new ArrayList<>();
        mPipeFactory = new Pipe.PipeFactory(mGamePanelRect.width(), mGamePanelRect.height(), mUpPipeBitmap, mDownPipeBitmap);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsDrawing) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Pipe pipe = mPipeFactory.generatePipe();
                            mPipes.add(pipe);
                        }
                    });
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void dispatchGameCreate() {
        for (IGameComponent gameComponent : mGameComponents) {
            gameComponent.onGameCreate();
        }
    }

    private void dispatchGameStatusChanged(@Status int status) {
        for (IGameComponent gameComponent : mGameComponents) {
            gameComponent.onStatusChanged(status);
        }
    }

    private void dispatchGameDestroy() {
        for (IGameComponent gameComponent : mGameComponents) {
            gameComponent.onGameDestroy();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
        onGameDestroy();
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            try {
                mCanvas = mSurfaceHolder.lockCanvas();
                if (mCanvas != null)
                    draw();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            } finally {
                if (mCanvas != null)
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGamePanelRect.set(0, 0, w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    private void draw() {
        Log.d(TAG, "draw");
        drawBackground();
        drawBird();
        drawFloor();
        drawPipes();
        handleFloor();
        handlePipes();
    }

    private void handleFloor() {
        mFloor.setX(mFloor.getX() - mSpeed);
    }

    private void handlePipes() {
        Iterator<Pipe> it = mPipes.iterator();
        while (it.hasNext()) {
            Pipe p = it.next();
            int pX = p.getX() - mSpeed;
            p.setX(pX);
            if (p.isFinished()) {
                it.remove();
            }
        }
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

    @IntDef({STATUS_PREPARE, STATUS_RUNNING, STATUS_STOP})
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {
    }

    public static final int STATUS_PREPARE = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_STOP = 3;
}
