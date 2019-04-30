package com.jim.java8.threads;

import java.util.concurrent.CountDownLatch;

/**
 * @author Jim
 * @date 2019/2/28
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new SubTaskThread(countDownLatch)).start();
        new Thread(new SubTaskThread(countDownLatch)).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {


            e.printStackTrace();
        }
    }

    public static class SubTaskThread implements Runnable {
        CountDownLatch countDownLatch;

        public SubTaskThread(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " do somethingDone!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

    }
}
