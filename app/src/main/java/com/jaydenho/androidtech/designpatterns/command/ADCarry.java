package com.jaydenho.androidtech.designpatterns.command;

import com.jaydenho.androidtech.designpatterns.factory.IADCarry;

/**
 * Created by hedazhao on 2017/1/10.
 */
public class ADCarry implements IADCarry {

    @Override
    public void attack() {
        System.out.println("adc shoot enemy!");
    }

    @Override
    public void defense() {
        System.out.println("adc clear soldier.");
    }

    @Override
    public void farm() {
        System.out.println("adc farm.");
    }
}
