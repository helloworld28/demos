package com.jim.java8.threads;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Jim
 * @date 2019/2/22
 */
public class FairAndUnFairTest {

    private ReentrantLock2 fairLock = new ReentrantLock2(true);
    private ReentrantLock2 unFairLock = new ReentrantLock2(false);

    @Test
    void testFair() {
        testLock(fairLock);
    }

    @Test
    void testUnfair() {
        testLock(unFairLock);
    }

    void testLock(Lock lock) {
        for (int i = 0; i < 10; i++) {
            Job job = new Job(fairLock);
            job.start();
            try {
                job.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName());
                System.out.println(lock.getQueuedThreads());
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        protected Collection<Thread> getQueuedThreads() {
            ArrayList arrayList = new ArrayList(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
