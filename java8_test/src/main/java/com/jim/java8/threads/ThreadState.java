package com.jim.java8.threads;

import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2019/2/14
 */
public class ThreadState {

    public static void main(String[] args) throws InterruptedException {

        new Thread(new TimeWaiting()).start();
        new Thread(new Waitiing()).start();
        new Thread(new Blocked()).start();
        Thread thread = new Thread(new Blocked());
        thread.start();
        thread.join();

    }

    static class TimeWaiting implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Waitiing implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                try {
                    synchronized (Waitiing.class) {
                        Waitiing.class.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Blocked implements Runnable {

        @Override
        public void run() {
            synchronized (Blocked.class) {
                for (; ; ) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
