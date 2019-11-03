package com.jaydenho.androidtech.thread;

/**
 * @author hedazhao
 * @since 2019/9/19
 */
public class LearnInterrupt {

    private void a(){
        Object lock = new Object();
        try {
            lock.wait(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
