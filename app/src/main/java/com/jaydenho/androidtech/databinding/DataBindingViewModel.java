package com.jaydenho.androidtech.databinding;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.ImageView;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/12/15.
 */

public class DataBindingViewModel {
    private ObservableList<DataBindingInfo> mDataBindingInfos = null;

    public DataBindingViewModel() {
        mDataBindingInfos = new ObservableArrayList<>();
        mDataBindingInfos.add(new DataBindingInfo("1", R.mipmap.icon_one, 0));
        mDataBindingInfos.add(new DataBindingInfo("2", R.mipmap.icon_two, 0));
        mDataBindingInfos.add(new DataBindingInfo("3", R.mipmap.icon_three, 0));
    }

    public void fetchData() {
        mDataBindingInfos.add(new DataBindingInfo("4", R.mipmap.icon_four, 0));
        mDataBindingInfos.add(new DataBindingInfo("5", R.mipmap.icon_telephone, 0));
    }

    public ObservableList<DataBindingInfo> getDataBindingInfos() {
        return mDataBindingInfos;
    }
}
