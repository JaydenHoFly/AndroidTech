package com.jaydenho.androidtech.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by hedazhao on 2016/7/26.
 */
public class TestAty extends AppCompatActivity {

    private static final String TAG = TestAty.class.getSimpleName();
    private ImageView mImageLoaderTestIv = null;

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

        UserInfo user = new UserInfo("zs", 10);
        final UserInfo finalUser = user;
        user = new UserInfo("ls", 11);
        Log.d(TAG, "finalUser: " + finalUser.toString());

        mImageLoaderTestIv = (ImageView) findViewById(R.id.test_image_loader_iv);
        ImageLoader.getInstance().displayImage("http://pic34.nipic.com/20131021/11569127_170602617166_2.jpg", mImageLoaderTestIv, getSujectIconDisplayOptions());
        ImageLoader.getInstance().loadImage("http://pic60.nipic.com/file/20150303/12216461_110913232000_2.jpg", getSujectIconDisplayOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        String str = "我是原始值";
        checkString(str);
        Log.d(TAG, "checkString: " + str);

        testProbability();
    }

    private void testProbability() {
        Map<String, Integer> testResult = new HashMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String resultStyle = getProbability();
            Integer resultTimes = testResult.get(resultStyle);
            testResult.put(resultStyle, (resultTimes == null ? 0 : resultTimes) + 1);
        }
        for (Map.Entry<String, Integer> entry : testResult.entrySet()) {
            Log.d(TAG, "[result] key: " + entry.getKey() + " value: " + entry.getValue());
        }
        Log.d(TAG, "[result] spend time(millisSecond): " + (System.currentTimeMillis() - start));
    }

    public void checkString(String str) {
        str = "我改变了";
    }

    public static DisplayImageOptions getSujectIconDisplayOptions() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true); // 设置是否开启内存缓存
        builder.cacheOnDisk(true);
        return builder.build();
    }

    private String getProbability() {
        Map<String, Integer> probabilityMap = new HashMap<>();
        String defaultStyle = "1";
        Set<String> styles = new HashSet<>();
        styles.add("1");
        styles.add("2");
        styles.add("3");
        styles.add("4");
        styles.add("5");
        styles.add("6");
        styles.add("7");
        styles.add("8");
        styles.add("9");
        styles.add("10");
        styles.add("11");
        int styleSize = styles.size();
        int average = 100 / styleSize;
        int remain = 100 - average * styleSize;
        for (String style : styles) {
            int probability = average;
            if (style.equals(defaultStyle)) {
                probability += remain;
            }
            probabilityMap.put(style, probability);
        }
        for (Map.Entry<String, Integer> entry : probabilityMap.entrySet()) {
            Log.d(TAG, "key: " + entry.getKey() + " value: " + entry.getValue());
        }
        String style = getResultFromProbability(probabilityMap, defaultStyle);
        Log.d(TAG, "style: " + style + "\n====================");
        return style;
    }

    public static <T> T getResultFromProbability(Map<T, Integer> probability, T defaultType) {
        // examine
        int total = 0;
        for (T ad_type : probability.keySet()) {
            total += probability.get(ad_type);
        }
        if (total != 100) {
            return defaultType;
        }

        // sort
        T sort[] = (T[]) new Object[probability.keySet().size()];
        List<T> tmpList = new ArrayList<>();
        tmpList.addAll(probability.keySet());

        int tag = 0;
        while (tag < sort.length) {
            T tmpMin = tmpList.get(0);
            int count = tmpList.size();
            for (int i = 1; i < count; i++) {
                T type = tmpList.get(i);
                if (probability.get(type) < probability.get(tmpMin)) {
                    tmpMin = type;
                }
            }

            tmpList.remove(tmpMin);
            sort[tag] = tmpMin;
            tag++;
        }

        // get result
        Random random = new Random();
        int resultPorbability = random.nextInt(100) + 1;

        int[] gap = new int[sort.length];
        for (int i = 0; i < sort.length; i++) {
            gap[i] = probability.get(sort[i]);

            int gapIndex = i - 1;
            while (gapIndex >= 0) {
                gap[i] += probability.get(sort[gapIndex]);
                gapIndex--;
            }
        }

        for (int i = 0; i < gap.length; i++) {
            if (i == 0) {
                if (resultPorbability <= gap[0])
                    return sort[0];
            } else if (i == sort.length - 1) {
                if (resultPorbability > gap[i - 1])
                    return sort[sort.length - 1];
            } else if (resultPorbability > gap[i - 1] && resultPorbability <= gap[i]) {
                return sort[i];
            }
        }
        return defaultType;
    }
}
