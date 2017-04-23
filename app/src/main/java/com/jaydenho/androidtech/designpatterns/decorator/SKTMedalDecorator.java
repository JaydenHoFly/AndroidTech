package com.jaydenho.androidtech.designpatterns.decorator;

/**
 * Created by hedazhao on 2017/4/14.
 */
public class SKTMedalDecorator extends MedalDecorator {
    @Override
    public String getName() {
        return mLOLRoleVisual.getName();
    }

    @Override
    public String getLeftShoe() {
        return mLOLRoleVisual.getLeftShoe();
    }

    @Override
    public String getMedal() {
        return "--- SKT ---";
    }
}
