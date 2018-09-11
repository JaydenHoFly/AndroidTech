package com.jaydenho.androidtech.classload;

/**
 * Created by hedazhao on 2018/8/9.
 */
public abstract class Parent {
    public Parent() {
        doAbstract();
        doNormal();
    }

    protected abstract void doAbstract();

    protected void doNormal() {
        System.out.println("doNormal. parent");
    }

    interface a{
        String a = null;
    }
}
