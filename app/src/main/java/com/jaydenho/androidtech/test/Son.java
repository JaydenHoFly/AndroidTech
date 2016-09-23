package com.jaydenho.androidtech.test;

/**
 * Created by hedazhao on 2016/8/31.
 */
public class Son extends Parent {
    private String son = getSon();
    private String son2 = getSon();
    private static String staticSonnVar = "son static init";

    public Son() {
        super();
        System.out.println("son constructor....");
    }

    private String getSon() {
        System.out.println("son un static ....");
        return "    ";
    }

    static {
        System.out.println("son static block....." + staticSonnVar);
    }

}