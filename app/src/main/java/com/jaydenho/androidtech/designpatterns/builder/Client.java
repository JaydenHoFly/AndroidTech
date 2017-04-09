package com.jaydenho.androidtech.designpatterns.builder;

import com.jaydenho.androidtech.designpatterns.factory.AbstractLOLRoleChoiceFactory;
import com.jaydenho.androidtech.designpatterns.factory.IADCarry;
import com.jaydenho.androidtech.designpatterns.factory.ITop;
import com.jaydenho.androidtech.designpatterns.factory.RoyalLOLRoleChoiceFactory;

/**
 * Created by hedazhao on 2017/2/17.
 */
public class Client {
    public static void main(String[] args) {
        Rune.LOLRuneBuilder builder = new Rune.LOLRuneBuilder()
                .setAttack()
                .setBlood()
                .setSpeed();
        IRune rune = builder.build();
    }
}
