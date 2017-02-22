package com.jaydenho.androidtech.designpatterns.command;

/**
 * Created by hedazhao on 2017/1/10.
 */
public class Jungle extends Captain implements ILOLRoleAction {

    @Override
    public void attack() {
        System.out.println("打野gank！");
    }

    @Override
    public void defense() {
        System.out.println("打野守塔！");
    }

    @Override
    public void farm() {
        System.out.println("打野刷野发育！");
    }
}
