package com.jaydenho.androidtech.designpatterns.proxy;

/**
 * Created by hedazhao on 2017/1/9.
 */
public class RealSubject implements ISubject, ISubject2 {
    @Override
    public void doSomething() {
        System.out.println("realSubject doSomething");
    }

    @Override
    public void doSubject2() {
        System.out.println("realSubject doSubject2");
    }
}
