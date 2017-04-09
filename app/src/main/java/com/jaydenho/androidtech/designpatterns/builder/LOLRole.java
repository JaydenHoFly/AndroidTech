package com.jaydenho.androidtech.designpatterns.builder;

import com.jaydenho.androidtech.designpatterns.command.ILOLRoleAction;

/**
 * Created by hedazhao on 2017/2/17.
 */
public abstract class LOLRole implements ILOLRoleAction, ILOLRoleAttr {

    private IRune mRune = null;

    public IRune getRune() {
        return mRune;
    }

    @Override
    public int getAttack() {
        return 50 + (mRune != null ? mRune.getExtraAttack() : 0);
    }

    @Override
    public int getBlood() {
        return 1000 + (mRune != null ? mRune.getExtraBlood() : 0);
    }

    @Override
    public int getSpeed() {
        return 325 + (mRune != null ? mRune.getExtraSpeed() : 0);
    }
}
