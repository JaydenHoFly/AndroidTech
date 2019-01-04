package com.jayden.learnndk;

/**
 * Created by hedazhao on 2019/1/4.
 */
public class HelloJni {

    public native String  stringFromJNI();

    static {
        System.loadLibrary("hello-jni");
    }
}
