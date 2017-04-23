package com.jaydenho.androidtech.designpatterns.decorator;

import com.jaydenho.androidtech.designpatterns.builder.Carter;

import java.util.ArrayList;

/**
 * Created by hedazhao on 2017/4/14.
 */
public class Client {

    public static void main(String[] args) {
        ILOLRoleVisual carter = new Carter();
        MedalDecorator sktMedalDecorator = new SKTMedalDecorator();
        sktMedalDecorator.setLOLRoleVisual(carter);
        System.out.println("show medal: " + sktMedalDecorator.getMedal());
    }
}
