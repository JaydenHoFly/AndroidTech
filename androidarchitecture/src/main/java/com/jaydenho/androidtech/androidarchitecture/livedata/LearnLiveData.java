package com.jaydenho.androidtech.androidarchitecture.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

/**
 * Created by hedazhao on 2017/11/27.
 */

public class LearnLiveData {
    private MutableLiveData<String> mListLiveData = null;

    public LearnLiveData() {
        mListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getUsersName() {
        return mListLiveData;
    }
}
