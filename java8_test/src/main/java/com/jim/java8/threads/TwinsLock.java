package com.jim.java8.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义锁同时只能有两个线程访问
 *
 * @author Jim
 * @date 2019/2/21
 */
public class TwinsLock implements Lock {


    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {

                int currentCount = getState();
                int newCount = currentCount - arg;
                if (newCount < 0 || compareAndSetState(currentCount, newCount)) {
                    //小于0直接失败,后面的说明大于0当cas成功后当获取锁成功
                    return currentCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int current = getState();
                int newCount = current + arg;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.tryAcquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.tryReleaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
