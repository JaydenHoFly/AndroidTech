package com.jaydenho.androidtech.ut;

import android.content.Context;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2016/10/13.
 */
public class JUnitTestUtil {
    public static boolean isValidEmail(String email) {
        return email.contains("@");
    }

    public String getString(Context context) {
        return context.getString(R.string.app_name);
    }

    public void calcList(List<String> data) {
        data.add("one");
        data.clear();
    }

    public interface ICallback {
        void onSuccess(List<String> data);

        void onFail();
    }

    public void makeRequest(final ICallback callback) {
        List<String> data = new ArrayList<>();
        data.add("john");
        data.add("jayden");
//        callback.onSuccess(data);
        callback.onFail();
    }
}
