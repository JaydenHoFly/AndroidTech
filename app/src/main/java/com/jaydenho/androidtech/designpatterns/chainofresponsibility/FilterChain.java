package com.jaydenho.androidtech.designpatterns.chainofresponsibility;

import com.jaydenho.androidtech.designpatterns.builder.LOLRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2017/2/23.
 */
public class FilterChain implements IPickFilter {
    private List<IPickFilter> mFilters = null;

    public FilterChain() {
        mFilters = new ArrayList<>();
    }

    public FilterChain addFilters(IPickFilter filter) {
        this.mFilters.add(filter);
        return this;
    }

    @Override
    public boolean pass(LOLRole role) {
        for (IPickFilter filter : mFilters) {
            if (!filter.pass(role)) {
                return false;
            }
        }
        return true;
    }
}
