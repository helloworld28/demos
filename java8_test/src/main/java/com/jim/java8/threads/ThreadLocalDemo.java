package com.jim.java8.threads;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2019/2/14
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                methond1();
                methond2();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                methond1();
                methond2();
            }
        }).start();
    }

    private synchronized static void methond1() {
        Profiler.begin();
        System.out.println(Thread.currentThread().getName() + " ->method1 invoking...");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void methond2() {
        System.out.println(Thread.currentThread().getName() + " ->method2 invoking...");
        System.out.println(Thread.currentThread().getName() + " cost:" + Profiler.end());
    }

    static class Profiler {
        private static ThreadLocal<Long> TIme_threadLocal = new ThreadLocal<>();

        public static void begin() {
            TIme_threadLocal.set(System.currentTimeMillis());
        }

        public static Long end() {
            return System.currentTimeMillis() - TIme_threadLocal.get();
        }
    }
}
