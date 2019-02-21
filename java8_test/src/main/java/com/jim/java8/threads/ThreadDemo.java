package com.jim.java8.threads;

import java.util.concurrent.TimeUnit;

/**
 * 三个线程顺序执行
 *
 * @author Jim
 * @date 2019/2/14
 */
public class ThreadDemo {


    private volatile static int flag = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ThreadDemo.class) {
                    System.out.println(Thread.currentThread().getName() + " invoking...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = 1;
                    ThreadDemo.class.notifyAll();
                }
            }
        }, "threadA");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ThreadDemo.class) {
                    while (flag != 1) {
                        try {
                            ThreadDemo.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " invoking...");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = 2;
                    ThreadDemo.class.notifyAll();
                }
            }
        }, "threadB");

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ThreadDemo.class) {
                    while (flag != 2) {
                        try {
                            ThreadDemo.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println(Thread.currentThread().getName() + " invoking...");
            }
        }, "ThreadC");

        threadC.start();
        threadB.start();
        threadA.start();
        threadC.join();
    }

}
