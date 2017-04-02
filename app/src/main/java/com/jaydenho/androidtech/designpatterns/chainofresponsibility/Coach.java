package com.jaydenho.androidtech.designpatterns.chainofresponsibility;

import com.jaydenho.androidtech.designpatterns.builder.LOLRole;
import com.jaydenho.androidtech.designpatterns.factory.Vayne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2017/2/23.
 */
public class Coach {
    public static void main(String[] args) {
        LOLRole role = new Vayne();
        FilterChain filterChain = new FilterChain();
        filterChain.addFilters(new AttackFilter()).addFilters(new SpeedFilter());
        if (filterChain.pass(role)) {
            System.out.println("choose " + role.getClass().getSimpleName());
        }
    }

}
