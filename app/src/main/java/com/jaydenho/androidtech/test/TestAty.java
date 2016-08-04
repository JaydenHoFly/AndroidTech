package com.jaydenho.androidtech.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hedazhao on 2016/7/26.
 */
public class TestAty extends AppCompatActivity {

    private static final String TAG = TestAty.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test);

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        Log.d(TAG, "list.contains(1): " + list.contains(1));
        list.remove(Integer.valueOf(1));
        list.remove(list.indexOf(2));
        Log.d(TAG, Arrays.toString(list.toArray()));
    } 
	 //asdfsasafffsssf
}
