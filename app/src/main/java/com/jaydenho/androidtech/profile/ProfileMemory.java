package com.jaydenho.androidtech.profile;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by hedazhao on 2019/2/27.
 */
public class ProfileMemory {
    private static final String TAG = "ProfileMemory";

    public void doSomethingMemoryIntensive(Context context) {
        // Before doing something that requires a lot of memory,
        // check to see whether the device is in a low memory state.
        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory(context);
        Log.d(TAG, "getAvailableMemory-memoryInfo.availMem=" + memoryInfo.availMem + "|memoryInfo.lowMemory=" + memoryInfo.lowMemory +
                "|memoryInfo.threshold=" + memoryInfo.threshold + "|memoryInfo.totalMem=" + memoryInfo.totalMem);
        if (!memoryInfo.lowMemory) {
            // Do memory intensive work ...
        }
    }

    // Get a MemoryInfo object for the device's current memory status.
    private ActivityManager.MemoryInfo getAvailableMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }
}
