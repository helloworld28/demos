package com.jim.java8.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ABC三个线程如何保证顺序执行
 * 1.构成调用链
 * 2.利用单线程池
 *
 * @author Jim
 * @date 2018/8/4
 */
public class ThreadInvoker {

    public static void main(String[] args) {
        //1.形成调用链
        ThreadFoo threadFooC = new ThreadFoo("Thread-C", null);
        ThreadFoo threadFooB = new ThreadFoo("Thread-B", new Thread(threadFooC));
        ThreadFoo threadFoo = new ThreadFoo("Thread-A", new Thread(threadFooB));

        new Thread(threadFoo).start();

        //2.利用单线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Thread(new ThreadFoo("Thread-A", null)));
        executorService.submit(new Thread(new ThreadFoo("Thread-B", null)));
        executorService.submit(new Thread(new ThreadFoo("Thread-C", null)));


    }

    public static class ThreadFoo implements Runnable {
        Thread nextThread;
        private String threadName;


        public ThreadFoo(String threadName, Thread nextThread) {
            this.threadName = threadName;
            this.nextThread = nextThread;
        }


        public void run() {
            System.out.println(threadName + " is invoked");
            if (nextThread != null) {
                nextThread.start();
            }

        }
    }
}
