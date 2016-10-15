package com.jaydenho.androidtech.ut;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hedazhao on 2016/10/14.
 */
public class LogHistory implements Parcelable {

    private String mString;
    private long mLong;

    public LogHistory() {
    }

    public void addEntry(String s, long l) {
        this.mString = s;
        this.mLong = l;
    }

    protected LogHistory(Parcel in) {
        mString = in.readString();
        mLong = in.readLong();
    }

    public static final Creator<LogHistory> CREATOR = new Creator<LogHistory>() {
        @Override
        public LogHistory createFromParcel(Parcel in) {
            return new LogHistory(in);
        }

        @Override
        public LogHistory[] newArray(int size) {
            return new LogHistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 2;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mString);
        dest.writeLong(mLong);
    }

    public String getmString() {
        return mString;
    }

    public long getmLong() {
        return mLong;
    }
}
