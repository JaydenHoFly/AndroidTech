package com.jaydenho.androidtech.databinding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by hedazhao on 2016/8/4.
 */
//绑定属性和方法的方式一，jtext是xml中的属性，namespace用app就行；setJText是CustomView中的方法
@BindingMethods({
        @BindingMethod(type = CustomView.class,
                attribute = "jtext",
                method = "setJText"),
})
public class CustomSetter {

    private static final String TAG = CustomSetter.class.getSimpleName();

    @BindingAdapter({"android:text"})
    public void logcatName(TextView v, String text) {
        Log.d("CustomSetter", "loadImage url: " + text);
        v.setText("哈哈" + text);
    }

    //绑定属性和方法的方式二，j_text是xml中的属性，对应该setText方法
    @BindingAdapter({"j_text"})
    public void setText(TextView v, String oldText, String newText) {
        Log.d(TAG, "setText oldText: " + oldText + " newText: " + newText);
        v.setText("j_text" + oldText + newText);
    }

    //两个属性只要有一个变化了，都会调用该setText2方法
    @BindingAdapter({"j_text_1", "j_text_2"})
    public void setText2(TextView v, String t1, String t2) {
        v.setText("j_text1: " + t1 + "&" + t2);
    }

    @BindingConversion
    public static String convertResIdToString(int stringId) {
        return "convert to string: " + stringId;
    }

    //形参类型转换，例如j_text接收的形参是String类型，如果给到它的参数类型是int，那么它会调用该方法自动将int转换成String
    @BindingConversion
    public static String convertResIdToString2(int stringId) {
        return "convert to string2: " + stringId;
    }

}
