package com.jayden.learnproguard3;

/**
 * 下载中心广告单例的父类，可以生成与{@link #origin}相对应的单例。
 * Created by hedazhao on 2018/12/18.
 */
public class DownloadCenterAdInstance implements IDownloadCenterAdInstance {
    private String origin;

    public DownloadCenterAdInstance( String origin) {
        this.origin = origin;
    }

    @Override
    public String getOrigin() {
        return origin;
    }
}
