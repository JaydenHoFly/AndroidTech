package com.jaydenho.androidtech.plugin.droidplugin.hookAMS;

import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hedazhao on 2017/4/25.
 */

public class AMSHookHelper {

    public static void hookActivityManagerNative() {
        try {
            Class<?> amn = Class.forName("android.app.ActivityManagerNative");
            Field iActivityManagerDefault = amn.getDeclaredField("gDefault");
            iActivityManagerDefault.setAccessible(true);

            Object iActivityManagerSingleton = iActivityManagerDefault.get(null);

            Class<?> singleton = Class.forName("android.util.Singleton");
            Method get = singleton.getDeclaredMethod("get");
            get.setAccessible(true);
            Object iActivityManager = get.invoke(iActivityManagerSingleton);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Object activityManagerProxy = Proxy.newProxyInstance(amn.getClassLoader(), new Class<?>[]{iActivityManagerClass}, new ActivityManagerNativeProxyHandler(iActivityManager));

            Field mInstance = singleton.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            mInstance.set(iActivityManagerSingleton, activityManagerProxy);

        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static void hookActivityThreadMH() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThread = sCurrentActivityThreadField.get(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Object mH = mHField.get(activityThread);

            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            Handler.Callback callback = (Handler.Callback) Proxy.newProxyInstance(activityThreadClass.getClassLoader(), new Class<?>[]{Handler.Callback.class}, new ActivityThreadMHHandler(mH));

            mCallbackField.set(mH, callback);

        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
