package com.jaydenho.androidtech.designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by hedazhao on 2017/1/9.
 */
public class Client {
    public static void main(String args[]) {
        ISubject realSubject = new RealSubject();
        InvocationHandler handler = new MyInvocationHandler(realSubject);
        Object proxy = Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), handler);
        ((ISubject) proxy).doSomething();
        ((ISubject2) proxy).doSubject2();
    }
}
