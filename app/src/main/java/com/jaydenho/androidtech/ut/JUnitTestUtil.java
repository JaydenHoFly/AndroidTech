package com.jaydenho.androidtech.ut;

import android.content.Context;

import com.jaydenho.androidtech.R;

import java.util.List;

/**
 * Created by hedazhao on 2016/10/13.
 */
public class JUnitTestUtil {
    public static boolean isValidEmail(String email) {
        return email.contains("@");
    }

    public String getString(Context context){
        return context.getString(R.string.app_name);
    }

    public void calcList(List<String> data){
        data.add("one");
        data.clear();
    }
}
