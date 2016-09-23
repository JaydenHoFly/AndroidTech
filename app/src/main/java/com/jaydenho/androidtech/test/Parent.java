package com.jaydenho.androidtech.test;

/**
 * Created by hedazhao on 2016/8/31.
 */
public class Parent {
    {
        System.out.println("parent  block ........" + staticVar);
    }

    private static String staticVar = getStatic();

    public Parent() {
        System.out.println("parent constructor......");
    }

    private String getUnStatic(){
        System.out.println("parent  un static........");
        return "";
    }

    private static String getStatic(){
        System.out.println("parent static........" + staticVar);
        return "";
    }

    static {
        System.out.println("parent static block ........");
    }
}
