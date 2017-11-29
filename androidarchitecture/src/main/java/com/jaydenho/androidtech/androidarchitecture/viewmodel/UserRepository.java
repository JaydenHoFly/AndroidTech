package com.jaydenho.androidtech.androidarchitecture.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * Created by hedazhao on 2017/11/29.
 */

public class UserRepository {
    private MutableLiveData<String> mUserNameLiveData = null;

    public UserRepository() {
        mUserNameLiveData = new MutableLiveData<>();
    }

    public LiveData<String> getUserName() {
        return mUserNameLiveData;
    }

    public void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUserNameLiveData.setValue("zhangsan");
            }
        }).start();
    }

    public void saveUserName(String userName) {
        mUserNameLiveData.setValue(userName);
    }

}
