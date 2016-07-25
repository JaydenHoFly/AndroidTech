package com.jaydenho.androidtech.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2016/7/22.
 */
public class DataBindingAty extends AppCompatActivity {

    private static final String TAG = DataBindingAty.class.getSimpleName();
    private com.jaydenho.androidtech.databinding.AtyDataBindingBinding mBinding = null;
    private UserInfo mUser = null;
    private List<String> mNames = null;
    private int namesIndex;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_data_binding);
        initData();
    }

    private void initData() {
        Log.d(TAG, "initData");
        mUser = new UserInfo("jayden", 22);
        mBinding.setUser(mUser);

        mNames = new ArrayList<>();
        mNames.add("lanlan");
        mNames.add("dada");
        mBinding.setNames(mNames);

        namesIndex = 0;
        mBinding.setNamesIndex(namesIndex);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNames.set(1, "adad");
                namesIndex = 1;
                mBinding.setNamesIndex(namesIndex);
            }
        }, 2000);
    }
}
