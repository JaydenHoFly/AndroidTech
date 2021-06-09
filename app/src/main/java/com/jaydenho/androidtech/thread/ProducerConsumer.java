package com.jaydenho.androidtech.thread;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hedazhao on 2021/5/23.
 */
public class ProducerConsumer {
    final LinkedList<Object> tasks = new LinkedList<>();
    BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
    final int max = 1000;

    class Producer extends Thread {
        @Override
        public void run() {
            super.run();

            while (true) {
                synchronized (tasks) {
                    if (tasks.size() < max) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        tasks.add(new Object());

                        tasks.notifyAll();

                    } else {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            super.run();

            while (true) {
                synchronized (tasks) {
                    if(!tasks.isEmpty()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        tasks.removeFirst();

                        tasks.notifyAll();

                    } else {
                        tasks.notifyAll();

                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
