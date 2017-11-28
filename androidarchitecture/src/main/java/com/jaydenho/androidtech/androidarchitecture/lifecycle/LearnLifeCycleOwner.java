package com.jaydenho.androidtech.androidarchitecture.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;

/**
 * Created by hedazhao on 2017/11/27.
 */

public class LearnLifeCycleOwner implements LifecycleOwner {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    public LearnLifeCycleOwner() {
        getLifecycle().addObserver(new LearnLifeCycleObserver(getLifecycle()));
    }

    public void onCreate() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public void onStop() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
