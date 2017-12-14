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

    private Lifecycle mLifecycle = null;

    public LearnLifeCycleObserver(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
    }
}
