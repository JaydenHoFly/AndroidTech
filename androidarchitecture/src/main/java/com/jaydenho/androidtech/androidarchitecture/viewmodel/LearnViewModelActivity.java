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

import com.jaydenho.androidtech.R;

import java.util.Random;

/**
 * Created by hedazhao on 2017/11/27.
 */

public class LearnViewModelActivity extends AppCompatActivity {

    private static final String TAG = "LearnViewModelActivity";
    private TextView mUserNameTV = null;
    private Button mUserChangeBtn = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_learn_live_data);
        mUserNameTV = findViewById(R.id.tv_user_name);
        mUserChangeBtn = findViewById(R.id.btn_user_change);
        final LearnViewModel learnViewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        learnViewModel.getUsersName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mUserNameTV.setText(s);
            }
        });
        mUserChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnViewModel.saveUserName("userName: " + new Random().nextInt(100));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }
}
