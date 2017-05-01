package com.jaydenho.androidtech.plugin.droidplugin.hookams;

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

    public static Object getActivityThread( Class<?> activityThreadClass) {
        try {
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            return sCurrentActivityThreadField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hookActivityThreadMH() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");

            Object activityThread = getActivityThread(activityThreadClass);

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

    public static void hookPackageManager() throws Exception {

        // 这一步是因为 initializeJavaContextClassLoader 这个方法内部无意中检查了这个包是否在系统安装
        // 如果没有安装, 直接抛出异常, 这里需要临时Hook掉 PMS, 绕过这个检查.

        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 获取ActivityThread里面原始的 sPackageManager
        Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
        sPackageManagerField.setAccessible(true);
        Object sPackageManager = sPackageManagerField.get(currentActivityThread);

        // 准备好代理对象, 用来替换原始的对象
        Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
        Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(),
                new Class<?>[] { iPackageManagerInterface },
                new IPackageManagerHookHandler(sPackageManager));

        // 1. 替换掉ActivityThread里面的 sPackageManager 字段
        sPackageManagerField.set(currentActivityThread, proxy);
    }
}
