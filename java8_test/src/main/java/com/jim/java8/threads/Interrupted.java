package com.jim.java8.threads;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2019/2/14
 */
public class Interrupted {

    public static void main(String[] args) throws InterruptedException {
        Thread sleepRunner = new Thread(new SleepRunner(), "SleepRunner");
        Thread busyRunner = new Thread(new BusyRunner(), "BusyRunner");

        sleepRunner.start();

        busyRunner.start();

        TimeUnit.SECONDS.sleep(1);

        sleepRunner.interrupt();
        busyRunner.interrupt();
        System.out.println(sleepRunner.getName() + "isInterrupted:" + sleepRunner.isInterrupted());
        System.out.println(busyRunner.getName() + "isInterrupted:" + busyRunner.isInterrupted());
        TimeUnit.SECONDS.sleep(2);

    }

    static class SleepRunner implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while (true) {

            }
        }
    }
}
