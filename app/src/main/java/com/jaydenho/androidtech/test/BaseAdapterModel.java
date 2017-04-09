package com.jaydenho.androidtech.test;

/**
 * Created by hedazhao on 2016/10/25.
 */
public class BaseAdapterModel {
    private String title;
    private String iconUrl;
    private int adSourceType;

    public BaseAdapterModel(int adSourceType, String title, String iconUrl) {
        this.adSourceType = adSourceType;
        this.title = title;
        this.iconUrl = iconUrl;
    }

    public int getADSourceType() {
        return adSourceType;
    }

    public String getTitle() {
        return title;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
