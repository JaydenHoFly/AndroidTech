package com.jaydenho.androidtech.test;

import android.support.v4.content.FileProvider;

/**
 * Created by wenshenghui on 2017/12/5.
 * 用于兼容＞ Android N 系统  访问文件的Uri问题
 */

public class XLFileProvider extends FileProvider {

    public static final String AUTHORITIES = "com.jayden.androidtech.XLFileProvider";
}
