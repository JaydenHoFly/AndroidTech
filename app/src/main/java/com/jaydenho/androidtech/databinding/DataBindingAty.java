package com.jaydenho.androidtech.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2016/7/22.
 */
public class DataBindingAty extends AppCompatActivity {

    private com.jaydenho.androidtech.databinding.AtyDataBindingBinding mBinding = null;
    private UserInfo mUser = null;
    private List<String> mNames = null;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_data_binding);
        initData();
    }

    private void initData() {
        mUser = new UserInfo();
        mUser.setName("jayden");
        mUser.setAge(22);
        mBinding.setUser(mUser);

        mNames = new ArrayList<>();
        mNames.add("lanlan");
        mNames.add("dada");
    }
}
