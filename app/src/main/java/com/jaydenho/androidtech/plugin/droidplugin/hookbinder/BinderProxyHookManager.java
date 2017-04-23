package com.jaydenho.androidtech.plugin.droidplugin.hookbinder;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hedazhao on 2017/4/23.
 * 这个Handler是代理从ServiceManager中getService()方法返回的IBinder对象，也就是Stub中传给 static public IInterface asInterface(IBinder obj) 的obj.
 */
public class BinderProxyHookManager implements InvocationHandler {

    private static final String TAG = "BinderProxyHookHandler";

    // 绝大部分情况下,这是一个BinderProxy对象
    // 只有当Service和我们在同一个进程的时候才是Binder本地对象
    // 这个基本不可能
    IBinder base;

    Class<?> stub;

    Class<?> iInterface;

    public BinderProxyHookManager(IBinder base) {
        this.base = base;
        try {
            this.stub = Class.forName("android.content.IClipboard$Stub");
            this.iInterface = Class.forName("android.content.IClipboard");
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d(TAG, "hook queryLocalInterface");
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[]{IBinder.class, IInterface.class, this.iInterface},
                    new BinderHookHandler(this.base, this.stub));
        }
        Log.d(TAG, "method:" + method.getName());
        return method.invoke(base, args);
    }
}
