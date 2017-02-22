package com.jaydenho.androidtech.designpatterns.command;

/**
 * Created by hedazhao on 2017/1/10.
 */
public class GankTactics extends Tactics {
    @Override
    public void execute() {
        mJungle.attack();
        mTop.attack();
    }
}
