package com.jaydenho.androidtech.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jaydenho.androidtech.R;

import java.util.HashMap;
import java.util.Map;

public class DataBindingAty extends AppCompatActivity {

    private static final String TAG = DataBindingAty.class.getSimpleName();
    private com.jaydenho.androidtech.databinding.AtyDataBindingBinding mBinding = null;
    private UserInfo mUser = null;
    private final ObservableArrayList<Integer> mNames = new ObservableArrayList<>();
    private int namesIndex;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private Handler mThreadHandler = null;
    private DataBindingListAdapter mListAdapter = null;
    private DataBindingViewModel mViewModel = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setDefaultComponent(new CustomSetterComponent());
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

        mNames.add(1);
        mNames.add(2);
        mBinding.setNames(mNames);

        namesIndex = 0;
        mBinding.setNamesIndex(namesIndex);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                namesIndex = 10;
                mBinding.setNamesIndex(namesIndex);
                mUser.score.set(80f);
                mUser.setName("candice");
                mNames.set(0, 3);
                mNames.add(0, 4);
            }
        }, 2000);

        HandlerThread ht = new HandlerThread("thread");
        ht.start();
        mThreadHandler = new Handler(ht.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
        mThreadHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUser.setName("leo");
                mNames.set(0, 5);
            }
        }, 3000);

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

        mUser.score.set(90f);

        initRecyclerView();
    }

    /**
     * 如何用DataBinding实现RecyclerView
     */
    private void initRecyclerView() {
        mViewModel = new DataBindingViewModel();
        mListAdapter = new DataBindingListAdapter();
        //layout中的id是list_rv, 自动生成名为listRV的控件
        RecyclerView listRV = mBinding.listRV;
        //CustomSetter#setInfos绑定了属性infos
        mBinding.setInfos(mViewModel.getDataBindingInfos());
        listRV.setLayoutManager(new LinearLayoutManager(this));
        listRV.setAdapter(mListAdapter);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewModel.fetchData();
            }
        }, 5000);
    }

    public void onClickMethod(UserInfo user) {
        toast("age: " + user.getAge());
    }

    private void toast(String msg) {
        Toast.makeText(DataBindingAty.this, msg, Toast.LENGTH_SHORT).show();
    }

}
