package com.jaydenho.androidtech.androidarchitecture.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by hedazhao on 2017/11/28.
 */

public class LearnViewModel extends ViewModel {
    private MutableLiveData<String> mListLiveData = null;

    public LearnViewModel() {
        mListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getUsersName() {
        return mListLiveData;
    }
}
