package com.jaydenho.androidtech.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.jaydenho.androidtech.BR;

import java.util.Random;

/**
 * Created by hedazhao on 2016/7/22.
 * 必须要实现getter,setter方法。
 * 实现双向绑定：
 * 1、使用ObserableField对象作为属性，并为属性设置getter, setter方法，就能在xml直接通过UserInfo的对象拿到属性；
 * 2、实现BaseObserable接口，在属性的getter方法中使用@Bindable注解，会在BR中根据getter方法名生成对应的id，然后在setter方法中调用notifyPropertyChanged方法通知xml, 对象UserInfo中的某个字段发生了改变。
 * 并不需要有具体的属性，只要有getter方法即可，参考{@link #getNoFieldName()}
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
    public String getNoFieldName() {
        return "no field name: " + new Random().nextInt(20);
    }

    public void updateNoFieldName() {
        //通知该类中所有属性都发生变化
        notifyChange();
        //仅通知BR.noFieldName发生变化
//        notifyPropertyChanged(BR.noFieldName);
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
