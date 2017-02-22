package com.jaydenho.androidtech.designpatterns.command;

import com.jaydenho.androidtech.designpatterns.factory.ITop;

/**
 * Created by hedazhao on 2017/1/10.
 */
public class Top implements ITop {

    @Override
    public void attack() {
        System.out.println("上单开团！");
    }

    @Override
    public void defense() {
        System.out.println("上单保护后排！");
    }

    @Override
    public void farm() {
        System.out.println("上单补刀发育！");
    }
}
