package com.jim.java8.threads;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 有两种方式使用,1,通过executeService.submit, 2,可以单独使用
 *
 * @author Jim
 * @date 2019/2/28
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt();
            }
        });
        future.get();

        Callable<Object> callable = Executors.callable(new Runnable() {
            @Override
            public void run() {

            }
        });
        callable.call();


        FutureTask<Integer> integerFutureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("futureDone!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, null);

        integerFutureTask.get();


        System.out.println("DONE!!!");
    }
}
