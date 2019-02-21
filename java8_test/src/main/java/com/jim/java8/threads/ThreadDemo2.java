package com.jim.java8.threads;

import java.util.concurrent.TimeUnit;

/**
 * 多线程顺序执行
 *
 * @author Jim
 * @date 2019/2/14
 */
public class ThreadDemo2 {

    public static void main(String[] args) {
        Thread preThread = null;
        for (int i = 0; i < 10; i++) {

            preThread = new Thread(new SubThread(preThread));
            preThread.start();
        }
    }

    static class SubThread implements Runnable {
        private Thread preThread;

        public SubThread(Thread preThread) {
            this.preThread = preThread;
        }

        @Override
        public void run() {
            try {
                if (preThread != null) {
                    preThread.join();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(preThread.getName() + " is terminated ....");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
