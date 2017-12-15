package com.jaydenho.androidtech.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.jaydenho.androidtech.BR;

/**
 * Created by hedazhao on 2016/7/22.
 * 必须要实现getter,setter方法。
 * 实现双向绑定：
 * 1、使用ObserableField对象；
 * 2、实现BaseObserable接口，在属性的getter方法中使用@Bindable注解，会在BR中生成对应的id，然后在setter方法中调用notifyPropertyChanged方法通知xml, 对象UserInfo中的某个字段发生了改变。
 */
public class UserInfo extends BaseObservable {
    public final ObservableField<Float> score = new ObservableField<>();
    private String name;
    private int age;

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
