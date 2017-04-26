package com.jaydenho.androidtech.plugin.droidplugin.hookAMS;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.jaydenho.androidtech.plugin.droidplugin.PluginActivityStub;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hedazhao on 2017/4/25.
 * ActivityManagerNativeProxy的动态代理Handler
 * 将插桩的Activity交给AMS,欺骗AMS
 */
public class ActivityManagerNativeProxyHandler implements InvocationHandler {

    public static final String EXTRA_KEY_TARGET_ACTIVITY = "target_activity";
    private static final String TAG = "HookActivityManager";
    private Object mActivityManagerNativeProxy = null;

    public ActivityManagerNativeProxyHandler(Object base) {
        this.mActivityManagerNativeProxy = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "method: " + method.getName() + " ams are hooked!!!");
        if ("startActivity".equals(method.getName())) {
            Intent raw;
            int index = 0;

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            raw = (Intent) args[index];

            Intent newIntent = new Intent();

            String targetPackage = "com.jaydenho.androidtech";
            newIntent.setComponent(new ComponentName(targetPackage, PluginActivityStub.class.getCanonicalName()));

            newIntent.putExtra(EXTRA_KEY_TARGET_ACTIVITY, raw);

            args[index] = newIntent;
            Log.d(TAG, "hook startActivity success");
        }
        return method.invoke(mActivityManagerNativeProxy, args);
    }
}
