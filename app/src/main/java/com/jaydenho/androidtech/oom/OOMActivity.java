package com.jaydenho.androidtech.oom;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenho.androidtech.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hedazhao
 * @since 2019/12/13
 */
public class OOMActivity extends BaseActivity implements ComponentCallbacks2 {
    private static final String TAG = "Learn-OOM";

    private List<OOMObjects> mOOMObjectss = new ArrayList<>();
    private OOMObjects mOOMObjects = new OOMObjects();

    //一次性创建大的数组，立马OOM：OutOfMemoryError: Failed to allocate a 4294967304 byte allocation
    //with 6291456 free bytes and 381MB until OOM, max allowed footprint 8978800, growth limit 402653184
//    private List<OOMObject> mOOMObjects = new ArrayList<>(Integer.MAX_VALUE/2);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        addContentView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        CountDownTimer countDownTimer = new CountDownTimer(TimeUnit.HOURS.toMillis(1), TimeUnit.SECONDS.toMillis(3)) {
            @Override
            public void onTick(long millisUntilFinished) {
                printMemory(true);
                Log.d(TAG,"mOOMObjectss.size=" + mOOMObjectss.size());
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer.start();

        printMemory(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mOOMObjects.mOOMObjects.add(new OOMObject());
                    if (mOOMObjects.mOOMObjects.size() > 10000) {
                        mOOMObjectss.add(mOOMObjects);
                        mOOMObjects = new OOMObjects();
                    }
                }
            }
        }).start();

    }

    private void printMemory(boolean relativeWithApp) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        if (!relativeWithApp) {
            Log.d(TAG, "activityManager.getMemoryClass()=" + activityManager.getMemoryClass() + "M");
            Log.d(TAG, "activityManager.getLargeMemoryClass()=" + activityManager.getLargeMemoryClass() + "M");
            Log.d(TAG, "Runtime.getRuntime().maxMemory() =" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
        }

        Log.d(TAG, "Runtime.getRuntime().totalMemory()=" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M");
        Log.d(TAG, "Runtime.getRuntime().freeMemory()=" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");

        if (!relativeWithApp) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            Log.d(TAG, "ActivityManager.MemoryInfo.totalMem=" + memoryInfo.totalMem / 1024 / 1024 + "M");
            Log.d(TAG, "ActivityManager.MemoryInfo.availMem=" + memoryInfo.availMem / 1024 / 1024 + "M");
        }


        activityManager.getMemoryClass();     //虚拟机java堆大小的上限，分配对象时突破这个大小就会OOM
        activityManager.getLargeMemoryClass();//manifest中设置largeheap=true时虚拟机java堆的上限
        Runtime.getRuntime().maxMemory();    //当前虚拟机实例的内存使用上限，为上述两者之一
        Runtime.getRuntime().totalMemory();  //当前已经申请的内存，包括已经使用的和还没有使用的
        Runtime.getRuntime().freeMemory();   //上一条中已经申请但是尚未使用的那部分。那么已经申请并且正在使用的部分used=totalMemory() - freeMemory()
//        ActivityManager.MemoryInfo.totalMem;  // 设备总内存
//        ActivityManager.MemoryInfo.availMem;  // 设备当前可用内存
        //       /proc/meminfo                 //  记录设备的内存信息
    }

    @Override
    public void onTrimMemory(int level) {

        Log.d(TAG, "onTrimMemory--level=" + level);

        mOOMObjectss.clear();

        // Determine which lifecycle or system event was raised.
        switch (level) {

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:

                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.
                */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:

                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:

                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */

                break;

            default:
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
                break;
        }
    }

    private static class OOMObjects {
        public List<OOMObject> mOOMObjects = new ArrayList<>();
    }

    private static class OOMObject {

    }
}
