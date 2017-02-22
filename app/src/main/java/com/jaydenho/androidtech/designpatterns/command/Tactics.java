package com.jaydenho.androidtech.designpatterns.command;

/**
 * Created by hedazhao on 2017/1/10.
 */
public abstract class Tactics {
    protected ILOLRoleAction mADC = null;
    protected ILOLRoleAction mTop = null;
    protected ILOLRoleAction mJungle = null;

    public Tactics() {
        mADC = new ADCarry();
        mTop = new Top();
        mJungle = new Jungle();
    }

    public abstract void execute();
}
