package com.jaydenho.androidtech.designpatterns.factory;

/**
 * Created by hedazhao on 2017/2/17.
 */
public class RoyalLOLRoleChoiceFactory extends AbstractLOLRoleChoiceFactory {
    @Override
    public ITop chooseTop() {
        return new Ryze();
    }

    @Override
    public IADCarry chooseADCarry() {
        return new Vayne();
    }
}
