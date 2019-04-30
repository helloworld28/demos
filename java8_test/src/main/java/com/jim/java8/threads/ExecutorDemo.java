package com.jim.java8.threads;

import com.jim.java8.Exec;

import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2019/2/28
 */
public class ExecutorDemo {
    public static void main(String[] args) {
        Executors.newFixedThreadPool(10);
        Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("do some thing schedule");
            }
        }, 100, 1000, TimeUnit.MICROSECONDS);




    }
}
