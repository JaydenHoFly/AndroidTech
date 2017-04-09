package com.jaydenho.androidtech.designpatterns.factory;

import com.jaydenho.androidtech.designpatterns.builder.LOLRole;

/**
 * Created by hedazhao on 2017/2/17.
 */
public class Vayne extends LOLRole implements IADCarry {
    @Override
    public void attack() {

    }

    @Override
    public void defense() {

    }

    @Override
    public void farm() {

    }

    @Override
    public int getAttack() {
        return super.getAttack() + 100;
    }

    @Override
    public int getBlood() {
        return super.getBlood();
    }

    @Override
    public int getSpeed() {
        return super.getSpeed() + 20;
    }
}
