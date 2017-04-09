package com.jaydenho.androidtech.test;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by hedazhao on 2016/8/31.
 */
public class UserInfo implements Serializable {
    private String mName;

    public UserInfo(String name){
        this.mName = name;
    }

    protected UserInfo(Parcel in) {
        mName = in.readString();
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mName='" + mName + '\'' +
                '}';
    }
}
