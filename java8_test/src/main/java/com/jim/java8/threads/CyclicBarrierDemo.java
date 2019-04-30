package com.jim.java8.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 屏障必须等所有线程都执行完才释放
 * 与countDownLatch类似
 *
 * @author Jim
 * @date 2019/2/28
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " has done work!");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        int await = cyclicBarrier.await();
        System.out.println("Done!!!!");


    }
}
