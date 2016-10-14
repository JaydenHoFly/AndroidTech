package com.jaydenho.androidtech;

/**
 * Created by hedazhao on 2016/7/25.
 */
public class DashboardInfo {

    private final int id;
    private final String title;

    public DashboardInfo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DashboardInfo) && (((DashboardInfo) o).getId() == this.id);
    }
}
