package com.jaydenho.androidtech.designpatterns.chainofresponsibility;

import com.jaydenho.androidtech.designpatterns.builder.LOLRole;

/**
 * Created by hedazhao on 2017/2/23.
 */
public class SpeedFilter implements IPickFilter {
    @Override
    public boolean pass(LOLRole role) {
        return role.getSpeed() > 330;
    }
}
