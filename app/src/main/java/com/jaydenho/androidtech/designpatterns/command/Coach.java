package com.jaydenho.androidtech.designpatterns.command;

/**
 * Created by hedazhao on 2017/1/10.
 */
public class Coach {
    public static void main(String[] args) {
        Captain j4396 = new Jungle();
        j4396.setTactics(new FarmTactics());
        j4396.action();

        j4396.setTactics(new GankTactics());
        j4396.action();
    }
}
