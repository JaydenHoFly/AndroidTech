package com.jaydenho.androidtech.designpatterns.proxy;

import com.google.repacked.kotlin.jvm.internal.Intrinsics;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hedazhao on 2017/1/9.
 */
public class MyInvocationHandler implements InvocationHandler {

    private ISubject mSubject = null;

    public MyInvocationHandler(ISubject subject) {
        Intrinsics.checkNotNull(subject, "subject is null");
        this.mSubject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equalsIgnoreCase("doSomething")) {
            //add timeout controller
            boolean isTimeout = true;
            if (isTimeout) {
                System.out.println("method doSomething is timeout");
                return null;
            }
        }
        Object result = method.invoke(mSubject, args);
        return result;
    }
}
