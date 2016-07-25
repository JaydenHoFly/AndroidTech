package com.jaydenho.androidtech.databinding;

/**
 * Created by hedazhao on 2016/7/22.
 */
public class UserInfo {
    private final String name;
    private final int age;

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

}
