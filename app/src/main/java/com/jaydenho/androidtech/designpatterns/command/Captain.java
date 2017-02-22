package com.jaydenho.androidtech.designpatterns.command;

/**
 * Created by hedazhao on 2017/1/10.
 */
public abstract class Captain {
    protected Tactics mTactics = null;

    public void setTactics(Tactics tactics) {
        this.mTactics = tactics;
    }

    public void action() {
        mTactics.execute();
    }
}
