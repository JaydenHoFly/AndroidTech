package com.jaydenho.learnbinder.learnaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/1.
 */
public class Book implements Parcelable {
    private int price;
    private String name;

    protected Book(Parcel in) {
        this.price = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(price);
        dest.writeString(name);
    }
}
