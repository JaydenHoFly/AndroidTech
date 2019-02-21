package com.jaydenho.androidtech.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jaydenho.androidtech.util.FileUtils;

import java.io.File;
import java.util.Arrays;

/**
 * 学习文件存储的各种路径。
 * Created by hedazhao on 2019/2/18.
 */
public class LearnFileStorage {
    private static final String TAG = "LearnFileStorage";

    public static void printPath(Context context) {
        // 内部存储-start
        // 保存缓存文件。
        //com.jaydenho.androidtech D/LearnFileStorage: cacheDir=/data/user/0/com.jaydenho.androidtech/cache
        Log.d(TAG, "cacheDir=" + context.getCacheDir().getAbsolutePath());
        //com.jaydenho.androidtech D/LearnFileStorage: filesDir=/data/user/0/com.jaydenho.androidtech/files
        Log.d(TAG, "filesDir=" + context.getFilesDir().getAbsolutePath());
        //com.jaydenho.androidtech D/LearnFileStorage: dir-custom-private=/data/user/0/com.jaydenho.androidtech/app_custom
        Log.d(TAG, "dir-custom-private=" + context.getDir("custom", Context.MODE_PRIVATE).getAbsolutePath());
        context.getCacheDir().getParentFile();
        // 输出应用的私有文件列表。
        //com.jaydenho.androidtech D/LearnFileStorage: fileList=[ams-pms-hook.apk]
        Log.d(TAG, "fileList=" + Arrays.toString(context.fileList()));
        // 内部存储-end

        // 外部存储-start
        // 保存大文件。
        //com.jaydenho.androidtech D/LearnFileStorage: obbDir=/storage/emulated/0/Android/obb/com.jaydenho.androidtech
        Log.d(TAG, "obbDir=" + context.getObbDir());
        // 保存应用私有文件，该文件夹下的文件不会被分享到media中，但是其他应用可以访问。
        //com.jaydenho.androidtech D/LearnFileStorage: externalFileDir-music=/storage/emulated/0/Android/data/com.jaydenho.androidtech/files/Music
        Log.d(TAG, "externalFileDir-music=" + context.getExternalFilesDir(Environment.DIRECTORY_MUSIC));
        // 保存公有文件。
        //com.jaydenho.androidtech D/LearnFileStorage: externalStoragePublicDir-music=/storage/emulated/0/Music
        Log.d(TAG, "externalStoragePublicDir-music=" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        //com.jaydenho.androidtech D/LearnFileStorage: externalCacheDir=/storage/emulated/0/Android/data/com.jaydenho.androidtech/cache
        Log.d(TAG, "externalCacheDir=" + context.getExternalCacheDir());
        // 外部存储-end
    }

    public static void traverse(File dir) {
        FileUtils.getFileSizeAndPrintChild(0, 100, dir);
    }
}
