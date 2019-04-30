package com.jim.java8.threads;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 信号量
 * 用于限流
 *
 * @author Jim
 * @date 2019/2/28
 */
public class SemaphoreDemo {

    private static final int THREAD_COUNT = 30;


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(10);
        ExecutorService executorService = new ThreadPoolExecutor(10, 1000,
                10000, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), new ThreadFactoryBuilder().setNameFormat("XX-task-%d").build());
        for (int i = 0; i < THREAD_COUNT; i++) {

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("save Data");
                    semaphore.release();
                }
            });
        }
        executorService.shutdown();

    }

}
