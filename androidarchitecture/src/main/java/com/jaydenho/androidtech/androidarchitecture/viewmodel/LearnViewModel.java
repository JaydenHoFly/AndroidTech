package com.jaydenho.androidtech.androidarchitecture.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

/**
 * Created by hedazhao on 2017/11/28.
 */

public class LearnViewModel extends ViewModel {
    private MediatorLiveData<String> mUserNameLiveData = null;
    private UserRepository mUserRepository = null;

    public LearnViewModel() {
        mUserRepository = new UserRepository();
        mUserNameLiveData = new MediatorLiveData<>();
        mUserNameLiveData.addSource(mUserRepository.getUserName(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mUserNameLiveData.setValue(s);
            }
        });
    }

    public MutableLiveData<String> getUsersName() {
        return mUserNameLiveData;
    }

    public void saveUserName(String userName) {
        mUserRepository.saveUserName(userName);
    }
}
