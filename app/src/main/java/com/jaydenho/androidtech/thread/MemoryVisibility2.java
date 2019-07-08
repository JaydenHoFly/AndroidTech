package com.jaydenho.androidtech.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 和VolatileTest比，print并不会出现任何日志，说明AtomicInteger具有内存可见性。
 */
public class MemoryVisibility2 {
    volatile AtomicInteger a = new AtomicInteger(1);
    volatile AtomicInteger b = new AtomicInteger(2);

    public void change() {
        a.set(3);
        b.set(3);
    }

    public void print() {
        if (a.get() == 3 && b.get() == 1) {
            System.out.println("b=" + b.get() + ";a=" + a.get());
        }
    }

    public static void main(String[] args) {
        while (true) {
            final MemoryVisibility2 test = new MemoryVisibility2();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();

        }
    }
}