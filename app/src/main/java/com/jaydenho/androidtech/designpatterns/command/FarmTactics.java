package com.jaydenho.androidtech.designpatterns.command;

/**
 * Created by hedazhao on 2017/1/10.
 */
public class FarmTactics extends Tactics {
    @Override
    public void execute() {
        mADC.farm();
        mTop.farm();
        mJungle.farm();
    }
}
