package com.jaydenho.androidtech.androidarchitecture.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * Created by hedazhao on 2017/11/29.
 */

public class UserRepository {

    public UserRepository() {
    }

    public MutableLiveData<UserInfo> refreshUserInfo(final long userId) {
        final MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //load basic info
                userInfo.postValue(new UserInfo(userId));
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //load other info
                userInfo.postValue(new UserInfo(userId));
            }
        }).start();
        return userInfo;
    }

}
