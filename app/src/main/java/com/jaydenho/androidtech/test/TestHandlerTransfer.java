package com.jaydenho.androidtech.test;

import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by hedazhao on 2016/12/17.
 */
public class TestHandlerTransfer implements Parcelable {

    private Handler mHandler = null;

    public TestHandlerTransfer() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d("TestHandlerTransfer","handleMessage");
                return true;
            }
        });
    }

    protected TestHandlerTransfer(Parcel in) {

    }

    public static final Creator<TestHandlerTransfer> CREATOR = new Creator<TestHandlerTransfer>() {
        @Override
        public TestHandlerTransfer createFromParcel(Parcel in) {
            return new TestHandlerTransfer(in);
        }

        @Override
        public TestHandlerTransfer[] newArray(int size) {
            return new TestHandlerTransfer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
