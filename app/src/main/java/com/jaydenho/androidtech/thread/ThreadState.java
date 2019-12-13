package com.jaydenho.androidtech.thread;

/**
 * 线程状态
 * @author hedazhao
 * @since 2019/12/13
 */
public class ThreadState {

    private void test(){
        Object object = new Object();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        try {
            //当前线程进入waiting状态，等待thread线程Dead后才恢复，内部使用wait方法实现，所以允许
            //多个线程调用thread.join()。
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            //当前线程释放object/thread的锁，进入waiting状态，等待object/thread对象执行notify/notifyAll。
            object.wait();
            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        object.notify();
        object.notifyAll();

        try {
            //
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
