package com.jaydenho.androidtech.databinding;

import android.databinding.BaseObservable;

/**
 * Created by hedazhao on 2016/7/22.
 */
public class UserInfo extends BaseObservable {
    private  String name;
    private  int age;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
