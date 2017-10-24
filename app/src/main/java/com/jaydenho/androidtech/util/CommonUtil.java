package com.jaydenho.androidtech.util;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

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

    @NonNull
    public static <T> T changeTypeByDays(T defaultType, T[] values) {
        return changeTypeByTime("dd", defaultType, values);
    }

    @NonNull
    public static <T> T changeTypeByMonths(T defaultType, T[] values) {
        return changeTypeByTime("MM", defaultType, values);
    }

    @NonNull
    private static <T> T changeTypeByTime(String inFormat, T defaultType, T[] values) {
        T type;
        String time = DateFormat.format(inFormat, System.currentTimeMillis()).toString();
        int timeInt = Integer.valueOf(time);
        int x = timeInt % 10;
        if (x >= values.length) {
            x = 100;
        }
        switch (x) {
            case 0:
                type = values[0];
                break;
            case 1:
                type = values[1];
                break;
            case 2:
                type = values[2];
                break;
            case 3:
                type = values[3];
                break;
            case 4:
                type = values[4];
                break;
            case 5:
                type = values[5];
                break;
            case 6:
                type = values[6];
                break;
            case 7:
                type = values[7];
                break;
            case 8:
                type = values[8];
                break;
            case 9:
                type = values[9];
                break;
            default:
                type = defaultType;
        }
        return type;
    }
}
