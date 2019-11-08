package com.jaydenho.androidtech.thread.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hedazhao
 * @since 2019/11/4
 */
public class LearnThreadPool {
    public void a(){
        ExecutorService executor = Executors.newCachedThreadPool();
        //SingleThread与newFixThreadPool(1)的不同是，返回的Executor不能再改变线程数，而后者返回的ThreadPoolExecutor可以setCoreThread改变线程数。
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(5);
        Executors.newFixedThreadPool(3);

        ThreadPoolExecutor executor3 = new ThreadPoolExecutor(5,10,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        executor3.setCorePoolSize(3);

    }
}
