package com.jaydenho.learnbinder.learnaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hedazhao on 2017/5/6.
 */
public class Param implements Parcelable {
    public String name;

    public Param(String name) {
        this.name = name;
    }

    protected Param(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Param> CREATOR = new Creator<Param>() {
        @Override
        public Param createFromParcel(Parcel in) {
            return new Param(in);
        }

        @Override
        public Param[] newArray(int size) {
            return new Param[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
