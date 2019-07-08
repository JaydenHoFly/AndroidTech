package com.jaydenho.androidtech.thread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 原子性和内存可见性。
 * 结论：AtomicBoolean具有原子性和可见性。
 * Created by hedazhao on 2019/7/8.
 */
public class MemoryVisibility {
    private static AtomicBoolean mIsInited = new AtomicBoolean(false);
    private static int i = 0;

    public static void init() {
        if (!mIsInited.getAndSet(true)) {
            i++;
            System.out.println("test init--i=" + i);
        }
    }

    public static void main(String[] args){
        test();
    }

    public static void test() {
        int n = 1000;
        for (int i = 0; i < n; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            }).start();
        }
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test end--i=" + i);
    }
}
