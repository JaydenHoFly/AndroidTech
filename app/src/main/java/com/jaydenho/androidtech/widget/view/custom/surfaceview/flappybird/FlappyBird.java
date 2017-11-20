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
import java.util.concurrent.TimeUnit;

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

    private static final int BIRD_DROP_Y = -5;
    private static final int BIRD_JUMP_Y = 100;
    private SurfaceHolder mSurfaceHolder = null;
    private Canvas mCanvas = null;
    private Paint mPaint = null;
    private Bitmap mBgBitmap = null;
    private Bitmap mUpPipeBitmap = null;
    private Bitmap mDownPipeBitmap = null;

    //config
    private int mFloorHeight = 0;
    private Rect mGamePanelRect = null;
    private Rect mGameActivityRect = null;

    //bird
    private Bird mBird = null;

    //floor
    private Floor mFloor = null;

    //pipe

    /**
     * count/s
     */
    private static final long PIPE_GENERATE_SPEED = 30;
    private List<Pipe> mPipes = null;
    private Pipe.PipeFactory mPipeFactory = null;
    private long mLastPipeGenerateTmtp = 0L;

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
        mGameActivityRect = new Rect();
    }

    private Bitmap loadBitmapFromResource(int b1) {
        return BitmapFactory.decodeResource(getResources(), b1);
    }

    private void setStatus(int status) {
        if (mStatus == status) return;
        Log.d(TAG, "setStatus. oldStatus: " + mStatus + " newStatus: " + status);
        mStatus = status;
        dispatchGameStatusChanged(mStatus);
    }

    @Status
    public int getStatus() {
        return mStatus;
    }

    public Rect getGamePanelRect() {
        return mGamePanelRect;
    }

    public Rect getGameActivityRect() {
        return mGameActivityRect;
    }

    public int getFloorHeight() {
        return mFloorHeight;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        onGameCreate();
        new Thread(this).start();
    }

    private void onGameCreate() {
        generateBird();
        generateFloor();
        generatePipes();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setStatus(STATUS_RUNNING);
            }
        }, 2000);

        dispatchGameCreate();
    }

    private void onGameDestroy() {
        dispatchGameDestroy();
    }

    private void generateFloor() {
        mFloor = new Floor(this, loadBitmapFromResource(R.mipmap.floor_bg2));
        mGameComponents.add(mFloor);
    }

    private void generateBird() {
        mBird = new Bird(this, loadBitmapFromResource(R.mipmap.b1));
        mBird.set(mGameActivityRect.width() / 2, mGameActivityRect.height() / 2);
        mGameComponents.add(mBird);
    }

    private boolean isGameOver() {
        if (isBirdDrop2Floor()) return true;
        return false;
    }

    private boolean isBirdDrop2Floor() {
        return mBird.getBody().bottom >= mGameActivityRect.bottom;
    }

    private void generatePipes() {
        mPipes = new ArrayList<>();
        mPipeFactory = new Pipe.PipeFactory(this, mUpPipeBitmap, mDownPipeBitmap);
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
        mFloorHeight = (int) ((1 - Floor.FLOOR_Y_POSITION_RATIO) * h);
        mGamePanelRect.set(0, 0, w, h);
        mGameActivityRect.set(0, 0, w, h - mFloorHeight);
        Log.d(TAG, "mGamePanelRect: " + mGamePanelRect.toShortString());
        Log.d(TAG, "mGameActivityRect: " + mGameActivityRect.toShortString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                jumpBird();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void draw() {
        drawBackground();
        drawBird();
        drawFloor();
        drawPipes();
        handleBird();
        handleFloor();
        handlePipes();
        if (isGameOver()) {
            setStatus(STATUS_STOP);
        }
    }

    private void handleFloor() {
        if (isGameRunning())
            mFloor.setX(mFloor.getX() - mSpeed);
    }

    private void tryGeneratePipe() {
        if (shouldGeneratePipe()) {
            mLastPipeGenerateTmtp = System.currentTimeMillis();
            Pipe pipe = mPipeFactory.generatePipe();
            mGameComponents.add(pipe);
            mPipes.add(pipe);
        }
    }

    private boolean shouldGeneratePipe() {
        return System.currentTimeMillis() - mLastPipeGenerateTmtp >= TimeUnit.MINUTES.toMillis(1) / PIPE_GENERATE_SPEED;
    }

    private void handlePipes() {
        if (isGameRunning()) {
            tryGeneratePipe();
            Iterator<Pipe> it = mPipes.iterator();
            while (it.hasNext()) {
                Pipe p = it.next();
                int pX = p.getX() - mSpeed;
                p.setX(pX);
                if (p.isFinished()) {
                    it.remove();
                    mGameComponents.remove(p);
                }
            }
        }
    }

    private void jumpBird() {
        if (!isGameRunning()) return;
        mBird.setMoveY(BIRD_JUMP_Y);
    }

    private void dropBird() {
        if (!isGameRunning()) return;
        mBird.setMoveY(BIRD_DROP_Y);
    }

    private void handleBird() {
        if (isGameRunning()) {
            dropBird();
        }
    }

    private boolean isGameRunning() {
        return getStatus() == STATUS_RUNNING;
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
