package com.jaydenho.androidtech.androidarchitecture.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

/**
 * Created by hedazhao on 2017/11/28.
 */

public class LearnViewModel extends ViewModel {
    private MutableLiveData<Long> mUserId = null;
    private LiveData<UserInfo> mUserInfo = null;

    private UserRepository mUserRepository = null;

    public LearnViewModel() {
        mUserRepository = new UserRepository();
        mUserId = new MutableLiveData<>();
        mUserInfo = Transformations.switchMap(mUserId, new Function<Long, LiveData<UserInfo>>() {
            @Override
            public LiveData<UserInfo> apply(Long userId) {
                return mUserRepository.refreshUserInfo(userId);
            }
        });
    }

    public MutableLiveData<Long> getUserId() {
        return mUserId;
    }

    public LiveData<UserInfo> getUserInfo() {
        return mUserInfo;
    }

}
