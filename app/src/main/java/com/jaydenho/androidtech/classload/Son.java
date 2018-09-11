package com.jaydenho.androidtech.classload;

/**
 * Created by hedazhao on 2018/8/9.
 */
public class Son extends Parent {

    @Override
    protected void doAbstract() {
        System.out.println("doAbstract. son");
    }

    @Override
    protected void doNormal() {
        super.doNormal();
        System.out.println("doNormal. son");
    }
}
