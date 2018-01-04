package com.jaydenho.androidtech.androidarchitecture.viewmodel;

/**
 * Created by hedazhao on 2018/1/4.
 */

public class UserInfo {
    private long userId;
    private String userName;
    private int userAge;

    public UserInfo(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
