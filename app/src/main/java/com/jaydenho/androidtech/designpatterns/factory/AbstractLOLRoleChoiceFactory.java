package com.jaydenho.androidtech.designpatterns.factory;

/**
 * Created by hedazhao on 2017/2/17.
 */
public abstract class AbstractLOLRoleChoiceFactory {
    public abstract ITop chooseTop();

    public abstract IADCarry chooseADCarry();
}
