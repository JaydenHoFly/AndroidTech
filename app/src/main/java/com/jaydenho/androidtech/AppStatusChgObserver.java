package com.jaydenho.androidtech;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

import static com.jaydenho.androidtech.AppStatusChgObserver.STATUS.STATUS_BACKGROUND;

/**
 * Created by chenjunheng on 2017/4/10.
 * 手雷状态变化监听类
 */

public class AppStatusChgObserver {
    private final String TAG = AppStatusChgObserver.class.getSimpleName();
    private volatile static AppStatusChgObserver sInstance;

    private STATUS mBackgroundStatus = STATUS.STATUS_INIT;

    public enum STATUS {
        /**
         * 初始状态，进程启动后，尚未进入app前台。例如推送后台拉取进程。
         * 引入该状态，用于判断app是否第一次进入前台。
         */
        STATUS_INIT,
        /**
         * app处于后台
         */
        STATUS_BACKGROUND,
        /**
         * app处于前台
         */
        STATUS_FOREGROUND
    }

    private int mCurResumeActivityHC;
    private Set<WeakReference> mWRListener;
    private boolean mIsForeground = false;

    private AppStatusChgObserver() {
        mWRListener = new HashSet<>();
    }

    /**
     * @return 当前手雷是否在后台
     */
    public boolean isAPPInBackGround() {
        Log.d(TAG, "get bg: " + (mBackgroundStatus == STATUS_BACKGROUND));
        return mBackgroundStatus == STATUS_BACKGROUND;
    }

    /**
     * 注册监听
     *
     * @param listener
     */
    public void registerListener(IStatusChgListener listener) {
        WeakReference<IStatusChgListener> w = new WeakReference<>(listener);
        mWRListener.add(w);
    }

    static final int STATUS_RESUME = 0;
    static final int STATUS_STOP = 1;

    void notifyActivityChg(@NonNull Activity activity, int status) {
        synchronized (this.getClass()) {
            switch (status) {
                case STATUS_RESUME:
                    Log.d(TAG, "notifyActivityChg. STATUS_RESUME. mBackgroundStatus: " + mBackgroundStatus);
                    if (mBackgroundStatus == STATUS.STATUS_INIT) {
                        //用户第一次进入前台，并没有发生状态变化，所以不用调用notifyChg方法。
                        mBackgroundStatus = STATUS.STATUS_FOREGROUND;
                        Log.d(TAG, "notifyActivityChg. app come to foreground first.");
                    } else if (mBackgroundStatus != STATUS.STATUS_FOREGROUND) {
                        if ((activity.hashCode() == mCurResumeActivityHC ||
                                mCurResumeActivityHC == 0)) {
                            //从后台切前台
                            notifyChg(STATUS.STATUS_FOREGROUND, activity);
                            mBackgroundStatus = STATUS.STATUS_FOREGROUND;
                            Log.d(TAG, "fg-----------" + activity.hashCode() + " " + activity.getClass().getSimpleName());
                        } else if (mBackgroundStatus == STATUS_BACKGROUND) {
                            //从后台切前台
                            notifyChg(STATUS.STATUS_FOREGROUND, activity);
                            mBackgroundStatus = STATUS.STATUS_FOREGROUND;
                            Log.d(TAG, "fg-----------" + activity.hashCode() + " " + activity.getClass().getSimpleName());
                        }
                    }
                    this.mCurResumeActivityHC = activity.hashCode();
                    break;
                case STATUS_STOP:
                    Log.d(TAG, "notifyActivityChg. STATUS_STOP. mBackgroundStatus: " + mBackgroundStatus);
                    if (activity.hashCode() == mCurResumeActivityHC) {
                        notifyChg(STATUS_BACKGROUND, activity);
                        mBackgroundStatus = STATUS_BACKGROUND;
                        Log.d(TAG, "bg-----------" + activity.hashCode() + " " + activity.getClass().getSimpleName());
                    }
                    break;
            }
        }
    }

    /**
     * 当前后台状态发送变化时调用，例如前台-后台，后台-前台。第一次启动app进入前台不算状态变化。
     */
    private void notifyChg(STATUS status, Activity activity) {
    }


    public static AppStatusChgObserver getInstance() {
        if (sInstance == null) {
            synchronized (AppStatusChgObserver.class) {
                if (sInstance == null) {
                    sInstance = new AppStatusChgObserver();
                }
            }
        }
        return sInstance;
    }

    /**
     * 手雷状态变化监听器
     */
    public interface IStatusChgListener {
        /**
         * 应用状态变化的回调
         *
         * @param status
         */
        void onStatusChange(STATUS status);
    }

    private WeakReference<Activity> mTopActivity;

    public Activity getTopActivity() {
        if (mTopActivity != null) {
            return mTopActivity.get();
        }
        return null;
    }

    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        private final String ACTIVITY_LIFE_CYCLE_TAG = "ActivityLifeCycle";

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            printActivityLifeCycleInfo(activity, "onActivityCreated");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            printActivityLifeCycleInfo(activity, "onActivityStarted");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            printActivityLifeCycleInfo(activity, "onActivityResumed");
            mIsForeground = true;
            notifyActivityChg(activity, AppStatusChgObserver.STATUS_RESUME);
            mTopActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            printActivityLifeCycleInfo(activity, "onActivityPaused");
            mIsForeground = false;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            printActivityLifeCycleInfo(activity, "onActivityStopped");
            notifyActivityChg(activity, AppStatusChgObserver.STATUS_STOP);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            printActivityLifeCycleInfo(activity, "onActivitySaveInstanceState");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            printActivityLifeCycleInfo(activity, "onActivityDestroyed");
        }

        private void printActivityLifeCycleInfo(Activity activity, String lifeCycleName) {
            Log.i(ACTIVITY_LIFE_CYCLE_TAG, activity.getClass().getSimpleName() + "-" + activity.hashCode() + "|" + lifeCycleName + "|" + getMultiWindowLog(activity));
        }

        private String getMultiWindowLog(@NonNull Activity activity) {
            return "isInMultiWindowMode: " + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && activity.isInMultiWindowMode());
        }
    };

    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return mActivityLifecycleCallbacks;
    }

    public boolean isAppInForeground() {
        return mIsForeground;
    }


}
