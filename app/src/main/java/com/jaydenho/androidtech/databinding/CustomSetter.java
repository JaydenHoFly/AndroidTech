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
@BindingMethods({
        @BindingMethod(type = CustomView.class,
                attribute = "bind:jtext",
                method = "setJText"),
})
public class CustomSetter {

    private static final String TAG = CustomSetter.class.getSimpleName();

    @BindingAdapter({"android:text"})
    public void logcatName(TextView v, String text) {
        Log.d("CustomSetter", "loadImage url: " + text);
        v.setText("哈哈" + text);
    }

    @BindingAdapter({"bind:j_text"})
    public void setText(TextView v, String oldText, String newText) {
        Log.d(TAG, "setText oldText: " + oldText + " newText: " + newText);
    }

    @BindingConversion
    public static String convertResIdToString(int stringId) {
        return "convert to string: " + stringId;
    }

}
