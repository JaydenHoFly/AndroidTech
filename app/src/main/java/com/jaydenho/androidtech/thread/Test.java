package com.jaydenho.androidtech.thread;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 生产者消费者。
 * Created by hedazhao on 2019/5/8.
 */
public class Test {
    private static final String TAG = "Test";
    private final Queue<String> queue = new ArrayDeque<>();
    private long consumeTime;
    private long consumeThreadTime;

    public static void test() {
        Test t = new Test();
        t.startA();
        t.startB();
    }

    private void startA() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (queue) {
                    consumeTime = System.currentTimeMillis();
                    consumeThreadTime = SystemClock.currentThreadTimeMillis();
                    while (true) {
                        if (queue.isEmpty()) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        consume();
                        queue.notifyAll();
                    }
                }
            }
        }).start();
    }

    private void startB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (queue) {
                    while (true) {
                        if (queue.size() >= 3) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        produce();
                        queue.notifyAll();
                    }
                }
            }
        }).start();
    }

    private void produce() {
        try {
            Thread.sleep(1000);
            Log.d(TAG, "produce");
            queue.add("1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void consume() {
        Log.d(TAG, "consume--consumeTime=" + (System.currentTimeMillis() - consumeTime) + "|consumeThreadTime=" + (SystemClock.currentThreadTimeMillis() - consumeThreadTime));
        consumeTime = System.currentTimeMillis();
        consumeThreadTime = SystemClock.currentThreadTimeMillis();
        queue.remove();
    }
}
