package com.jaydenho.androidtech.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import com.jaydenho.androidtech.BR;

/**
 * Created by hedazhao on 2016/7/22.
 */
public class UserInfo extends BaseObservable {
    public final ObservableField<Float> score = new ObservableField<>();
    private  String name;
    private  int age;

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Bindable
    public int getAge() {
        return age;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
