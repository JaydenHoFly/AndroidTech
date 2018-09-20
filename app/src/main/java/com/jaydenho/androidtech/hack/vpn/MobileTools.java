package com.jaydenho.androidtech.hack.vpn;

import android.app.Application;
import android.content.Context;

import com.jaydenho.androidtech.AndroidApplicationLike;

/**
 * Created by hedazhao on 2018/9/14.
 */
public class MobileTools {
   public static Context getInstance(){
       return AndroidApplicationLike.getApplicationInstance();
   }
}
