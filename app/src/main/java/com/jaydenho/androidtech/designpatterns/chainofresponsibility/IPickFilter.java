package com.jaydenho.androidtech.designpatterns.chainofresponsibility;

import com.jaydenho.androidtech.designpatterns.builder.LOLRole;

/**
 * Created by hedazhao on 2017/2/23.
 */
public interface IPickFilter {
    boolean pass(LOLRole role);
}
