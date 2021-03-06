package com.jaydenho.androidtech.androidarchitecture.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jaydenho.androidtech.androidarchitecture.R;


/**
 * Created by hedazhao on 2017/11/27.
 */

public class LearnViewModelActivity extends AppCompatActivity {

    private static final String TAG = "LearnViewModelActivity";
    private TextView mUserIdTV = null;
    private Button mUserChangeBtn = null;
    private Button mAutoLogin = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_learn_live_data);
        mUserIdTV = findViewById(R.id.et_user_name);
        mUserChangeBtn = findViewById(R.id.btn_user_change);
        mAutoLogin = findViewById(R.id.btn_auto_login);

        final LearnViewModel learnViewModel = ViewModelProviders.of(this).get(LearnViewModel.class);

        mAutoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnViewModel.getUserId().postValue(1000L);
            }
        });

        mUserChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnViewModel.getUserId().postValue(Long.parseLong(mUserIdTV.getText().toString()));
            }
        });

        learnViewModel.getUserInfo().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(@Nullable UserInfo userInfo) {
                showUserInfo(userInfo);
            }
        });
    }

    private void showUserInfo(UserInfo userInfo) {
        //show userInfo
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }
}
