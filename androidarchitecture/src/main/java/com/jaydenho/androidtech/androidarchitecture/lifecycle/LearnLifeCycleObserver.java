package com.jaydenho.androidtech.androidarchitecture.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by hedazhao on 2017/11/27.
 */

public class LearnLifeCycleObserver implements LifecycleObserver {
    private static final String TAG = "LearnLifeCycleObserver";

    public LearnLifeCycleObserver() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Log.d(TAG,"onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.d(TAG,"onStop");
    }
}
