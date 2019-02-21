package com.jim.java8.threads;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

/**
 * @author Jim
 * @date 2019/2/16
 */
public class DefaultThreadPoolTest {

    @Test
    public void testExecuteJob() throws InterruptedException {
        DefaultThreadPool defaultThreadPool = new DefaultThreadPool();

        for (int i = 0; i < 12; i++) {
            defaultThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " do something ......");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        TimeUnit.SECONDS.sleep(11);
        defaultThreadPool.shutdown();
    }

}