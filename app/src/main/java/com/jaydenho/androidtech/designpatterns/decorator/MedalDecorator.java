package com.jaydenho.androidtech.designpatterns.decorator;

/**
 * Created by hedazhao on 2017/4/14.
 */
public abstract class MedalDecorator implements ILOLRoleVisual {
    protected ILOLRoleVisual mLOLRoleVisual;

    public void setLOLRoleVisual(ILOLRoleVisual LOLRoleVisual) {
        mLOLRoleVisual = LOLRoleVisual;
    }
}
