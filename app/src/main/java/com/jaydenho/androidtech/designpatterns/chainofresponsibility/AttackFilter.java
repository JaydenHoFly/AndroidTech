package com.jaydenho.androidtech.designpatterns.chainofresponsibility;

import com.jaydenho.androidtech.designpatterns.builder.LOLRole;

/**
 * Created by hedazhao on 2017/2/23.
 */
public class AttackFilter implements IPickFilter {
    @Override
    public boolean pass(LOLRole role) {
        return role.getAttack() > 100;
    }
}
