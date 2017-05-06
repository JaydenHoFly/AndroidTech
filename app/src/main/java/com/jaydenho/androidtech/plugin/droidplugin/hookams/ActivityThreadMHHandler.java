package com.jaydenho.androidtech.plugin.droidplugin.hookams;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Message;
import android.util.Log;

import com.jaydenho.androidtech.plugin.droidplugin.hookclassloader.muticlassloader.LoadedApkClassLoaderHookHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hedazhao on 2017/4/25.
 * ActivityThread中的Handler对象mH的动态Handler
 * 真正启动Activity时,将插桩的Activity换成目标Activity.
 */
public class ActivityThreadMHHandler implements InvocationHandler {
    private Object mHandler = null;

    public ActivityThreadMHHandler(Object handler) {
        this.mHandler = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("ActivityThreadMHHandler", "method: " + method.getName() + " mhandler: " + mHandler.getClass());
        if ("handleMessage".equals(method.getName())) {
            Message msg = (Message) args[0];
            switch (msg.what) {
                case 100:
                    try {
                        Object activityClientRecord = msg.obj;
                        Field intentField = activityClientRecord.getClass().getDeclaredField("intent");
                        intentField.setAccessible(true);
                        Intent intent = (Intent) intentField.get(activityClientRecord);
                        Intent target = intent.getParcelableExtra(ActivityManagerNativeProxyHandler.EXTRA_KEY_TARGET_ACTIVITY);
                        intent.setComponent(target.getComponent());

                        Field activityInfoField = activityClientRecord.getClass().getDeclaredField("activityInfo");
                        activityInfoField.setAccessible(true);

                        // getPackageInfo 根据这个 包名获取 LoadedApk的信息; 因此这里我们需要手动填上, 从而能够命中缓存
                        ActivityInfo activityInfo = (ActivityInfo) activityInfoField.get(activityClientRecord);

                        activityInfo.applicationInfo.packageName = target.getPackage() == null ?
                                target.getComponent().getPackageName() : target.getPackage();

                        AMSHookHelper.hookPackageManager();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        Method handleMessageMethod = mHandler.getClass().getDeclaredMethod("handleMessage", Message.class);
        handleMessageMethod.setAccessible(true);
        handleMessageMethod.invoke(mHandler, args);
        return true;
    }
}
