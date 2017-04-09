package com.jaydenho.androidtech.designpatterns.factory;

/**
 * Created by hedazhao on 2017/2/17.
 */
public class Client {
    public static void main(String[] args) {
//        AbstractLOLRoleChoiceFactory myFactory = new OMGLOLRoleChoiceFactory();
        AbstractLOLRoleChoiceFactory myFactory = new RoyalLOLRoleChoiceFactory();
        IADCarry myADCarry = myFactory.chooseADCarry();
        ITop myTop = myFactory.chooseTop();
        myADCarry.attack();
        myTop.attack();
    }
}
