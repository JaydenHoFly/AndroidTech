package com.jaydenho.androidtech.logger;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hedazhao on 2019/6/12.
 */
public class LearnLogger {
    public static void learn() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(new ) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        //日志存放目录：/mnt/sdcard/logger/
        Logger.addLogAdapter(new DiskLogAdapter());
        Logger.json("{\"name\":\"zs\"}");
        Map<String, String> params = new HashMap<>();
        params.put("name", "ls");
        Logger.d(params);
        Logger.t("LearnLogger");
        Logger.e(new RuntimeException("test exception"), "test %s", "first arg");
        Logger.d("before clearLogAdapters");
        Logger.log(Logger.INFO, "LearnLogger", "learn disk", null);
        Logger.clearLogAdapters();
        Logger.d("end");
    }
}
