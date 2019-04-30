package com.jim.java8.threads;

import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2019/2/22
 */
public class TwinsLockTest {


    public static void main(String[] args) {
        TwinsLock twinsLock = new TwinsLock();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        twinsLock.lock();
                        try {
                            System.out.println(Thread.currentThread().getName() + " get the Lock!");
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            twinsLock.unlock();
                            System.out.println(Thread.currentThread().getName() + "release the Lock");
                        }
                    }
                }
            }).start();
        }

    }

}
