package com.jaydenho.androidtech.androidarchitecture.lifecycle;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Framework层通过{@link android.arch.lifecycle.ReportFragment ReportFragment}
 * 为所有Activity实现了LifecycleOwner的功能，应用层只要实现LifecycleOwner并提供LifeCycle对象就行。
 * created by hedazhao at 2019/4/16
 */
public class LearnLifeCycleActivity2 extends Activity implements LifecycleOwner {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new LearnLifeCycleObserver());
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
