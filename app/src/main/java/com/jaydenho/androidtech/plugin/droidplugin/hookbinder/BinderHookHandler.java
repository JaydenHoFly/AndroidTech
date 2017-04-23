package com.jaydenho.androidtech.plugin.droidplugin.hookbinder;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hedazhao on 2017/4/23.
 * 这个Handler是代理类似ActivityManagerNative这种实现了IInterface具体业务逻辑的类
 */
public class BinderHookHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    Object base;

    public BinderHookHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getPrimaryClip".equals(method.getName())) {
            Log.d(TAG, "hook getPrimaryClip");
            return ClipData.newPlainText(null, "you are hooked");
        }

        if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }
        return method.invoke(base, args);
    }


}
