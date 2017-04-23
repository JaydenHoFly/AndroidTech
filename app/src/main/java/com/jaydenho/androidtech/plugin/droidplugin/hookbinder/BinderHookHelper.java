package com.jaydenho.androidtech.plugin.droidplugin.hookbinder;

import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by hedazhao on 2017/4/23.
 */
public class BinderHookHelper {

    static final String CLIPBOARD_SERVICE = "clipboard";

    public static void hookServiceManager() {
        try {
            Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManagerClass.getDeclaredMethod("getService", String.class);
            getServiceMethod.setAccessible(true);

            IBinder base = (IBinder) getServiceMethod.invoke(null, CLIPBOARD_SERVICE);

            IBinder hookBinder = (IBinder) Proxy.newProxyInstance(serviceManagerClass.getClassLoader(),
                    new Class<?>[]{IBinder.class},
                    new BinderProxyHookManager(base));

            Field sCacheField = serviceManagerClass.getDeclaredField("sCache");
            sCacheField.setAccessible(true);

            HashMap<String, IBinder> sCache = (HashMap<String, IBinder>) sCacheField.get(null);
            sCache.put(CLIPBOARD_SERVICE, hookBinder);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
