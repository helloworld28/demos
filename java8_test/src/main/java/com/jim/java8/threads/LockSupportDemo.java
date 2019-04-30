package com.jim.java8.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jim
 * @date 2019/2/22
 */
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        LockSupport.park(this);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println("wakeup thread!!!!!!");
        TimeUnit.SECONDS.sleep(3);
        LockSupport.unpark(thread);
    }
}
