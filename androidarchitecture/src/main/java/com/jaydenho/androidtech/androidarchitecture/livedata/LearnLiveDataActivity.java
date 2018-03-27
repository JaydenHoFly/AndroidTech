package com.jaydenho.androidtech.androidarchitecture.livedata;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.jaydenho.androidtech.androidarchitecture.R;

import java.util.Random;

/**
 * Created by hedazhao on 2017/11/27.
 */

public class LearnLiveDataActivity extends AppCompatActivity {

    private TextView mUserNameTV = null;
    private Button mUserChangeBtn = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_learn_live_data);
        mUserNameTV = findViewById(R.id.et_user_name);
        mUserChangeBtn = findViewById(R.id.btn_user_change);
        final LearnLiveData learnLiveData = new LearnLiveData();
        learnLiveData.getUsersName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mUserNameTV.setText(s);
            }
        });
        mUserChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnLiveData.getUsersName().setValue("tracy: " + new Random().nextInt());
            }
        });
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        final MediatorLiveData<String> stringMediatorLiveData = new MediatorLiveData<>();
        stringMediatorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        stringMediatorLiveData.addSource(liveData, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                stringMediatorLiveData.setValue(s);
            }
        });
        Transformations.map(liveData, new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input + "";
            }
        });
        Transformations.switchMap(liveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String input) {
                return new MutableLiveData<>();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
