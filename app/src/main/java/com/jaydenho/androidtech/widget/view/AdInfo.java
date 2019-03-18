package com.jaydenho.androidtech.widget.view;

import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by hedazhao on 2019/3/17.
 */
public class AdInfo {
    private String TAG = "AdInfo";
    private CountDownTimer mCountDownTimer;
    private static final int MSG_WHAT_EFFECTIVE_SHOW = 1000;
    private static WeakHashMap<View, AdInfo> mPMPAdShowTrackRecords = new WeakHashMap<>();
    /**
     * 本广告是否已经被有效曝光。
     */
    private boolean mIsEffectiveShow = false;
    private Handler mMainHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_EFFECTIVE_SHOW:
                    Log.d(TAG, "trackPMPAdShow--is effective show.");
                    mIsEffectiveShow = true;
                    cancelPMPAdShowTracker("is effective show.");
                    return true;
            }
            return false;
        }
    });

    public AdInfo(String tag) {
        TAG += "|" + tag;
    }

    public void trackPMPAdShow(final View v) {
        if (!mIsEffectiveShow) {
            Log.d(TAG, "trackPMPAdShow--start.");
            AdInfo adInfo = mPMPAdShowTrackRecords.get(v);
            if (adInfo != null && !adInfo.equals(this)) {
                //view被复用了，取消之前的延时任务和tracker.
                adInfo.cancelPendingTaskOfEffectiveShow("view is render another ad.");
                adInfo.cancelPMPAdShowTracker("view is render another ad.");
            }
            final Rect rect = new Rect();
            if (mCountDownTimer == null) {
                mCountDownTimer = createPMPAdShowTracker(rect, new WeakReference<>(v));
                mPMPAdShowTrackRecords.put(v, this);
                mCountDownTimer.start();
            } else {
                Log.d(TAG, "trackPMPAdShow--is still tracking.");
            }
        }
    }

    /**
     * 启动有效曝光的延时任务。
     * 如果已经存在延时任务，则不会重复启动。
     */
    private void startPendingTaskOfEffectiveShow() {
        if (!mMainHandler.hasMessages(MSG_WHAT_EFFECTIVE_SHOW)) {
            Log.d(TAG, "trackPMPAdShow--startPendingTaskOfEffectiveShow");
            mMainHandler.sendEmptyMessageDelayed(MSG_WHAT_EFFECTIVE_SHOW, TimeUnit.SECONDS.toMillis(1));
        }
    }

    /**
     * 取消有效曝光的延时任务。
     *
     * @param reason 原因。
     */
    private void cancelPendingTaskOfEffectiveShow(String reason) {
        if (mMainHandler.hasMessages(MSG_WHAT_EFFECTIVE_SHOW)) {
            Log.d(TAG, "trackPMPAdShow--cancelPendingTaskOfEffectiveShow--reason=" + reason);
            mMainHandler.removeMessages(MSG_WHAT_EFFECTIVE_SHOW, null);
        }
    }

    private CountDownTimer createPMPAdShowTracker(final Rect rect, final WeakReference<View> wrv) {
        //一分钟内，每隔100ms检测一次广告控件可见像素点占比。
        return new CountDownTimer(TimeUnit.MINUTES.toMillis(3), 100) {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTick(long millisUntilFinished) {
                View v = wrv.get();
                if (v != null) {
                    if (!v.isAttachedToWindow()) {
                        cancelPendingTaskOfEffectiveShow("view is detached.");
                    } else {
                        int visibilityPercents = AutoPlayUtil.getVisibilityPercents(rect, v);
                        Log.d(TAG, "trackPMPAdShow--visibilityPercents=" + visibilityPercents);
                        if (visibilityPercents >= 50) {
                            startPendingTaskOfEffectiveShow();
                        } else {
                            cancelPendingTaskOfEffectiveShow("visibilityPercents is " + visibilityPercents);
                        }
                    }
                } else {
                    cancelPendingTaskOfEffectiveShow("view has been destroyed.");
                    cancelPMPAdShowTracker("view has been destroyed.");
                }
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "trackPMPAdShow--track finish.");
            }
        };
    }

    private void cancelPMPAdShowTracker(String reason) {
        if (mCountDownTimer != null) {
            Log.d(TAG, "trackPMPAdShow--cancelPMPAdShowTracker--reason=" + reason);
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    private static Map<String, AdInfo> mTestData = new HashMap<>();

    public static AdInfo obtainAdInfo(String s) {
        AdInfo adInfo = mTestData.get(s);
        if (adInfo == null) {
            adInfo = new AdInfo(s);
            mTestData.put(s, adInfo);
        }
        return adInfo;
    }

    public static void clearAdInfos() {
        for (Map.Entry<String, AdInfo> entry : mTestData.entrySet()) {
            entry.getValue().cancelPendingTaskOfEffectiveShow("clear");
            entry.getValue().cancelPMPAdShowTracker("clear");
        }
        mTestData.clear();
    }
       /*v.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
        if (mOnAttachStateChangeListener == null) {
            mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    Log.d(TAG, "onViewAttachedToWindow");
                    trackPMPAdShow(v);
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    Log.d(TAG, "onViewDetachedFromWindow");
                    cancelPMPAdShowTracker("view is detached");
                }
            };
        }
        v.addOnAttachStateChangeListener(mOnAttachStateChangeListener);*/
}
