package com.jaydenho.androidtech.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by hedazhao on 2017/3/4.
 */
public class CommonUtil {

    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "_hdz" + File.separator + "android-tech";
    /**
     * 仅用作测试
     */
    public static void saveOverLengthLog2SDCard(String fileName, String content) {
        FileUtils.writeFile(BASE_PATH + File.separator + fileName, content);
    }
}
