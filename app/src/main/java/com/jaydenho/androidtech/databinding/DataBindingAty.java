package com.jaydenho.androidtech.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        mUser.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Log.d(TAG, "onPropertyChanged: " + mUser.getName() + " " + mUser.getAge());
            }
        });
        mBinding.setUser(mUser);

        mNames = new ArrayList<String>();
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

        Map<String, Integer> map = new HashMap<>();
        map.put("jayden", 22);
        map.put("candice", 21);
        mBinding.setMap(map);
        mBinding.setMapIndex("jayden");

        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.click01:
                        toast("click01");
                        break;
                }
            }
        };
        mBinding.setOnClickListener(l);
        mBinding.setDataBindingAty(this);
    }

    public void onClickMethod(UserInfo user) {
        toast("age: " + user.getAge());
    }

    private void toast(String msg) {
        Toast.makeText(DataBindingAty.this, msg, Toast.LENGTH_SHORT).show();
    }

}
