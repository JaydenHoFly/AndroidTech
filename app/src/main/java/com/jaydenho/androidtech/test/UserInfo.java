package com.jaydenho.androidtech.test;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2016/8/31.
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 2446434975897559222L;
    private String mName;
    private String age;
    private String age1 = "";
    private List<String> list = new ArrayList();

    public UserInfo(String name) {
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
