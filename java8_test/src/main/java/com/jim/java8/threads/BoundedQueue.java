package com.jim.java8.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jim
 * @date 2019/2/22
 */
public class BoundedQueue {

    private Object[] items;
    private int addIndex, removeIndex, count;
    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    public void add(Object object) {
        lock.lock();
        try {

            while (count == items.length) {
                notFull.await();
                System.out.println(Thread.currentThread().getName() + "wait");
            }

            if (++addIndex == items.length) {
                addIndex = 0;
            }
            items[addIndex] = object;
            ++count;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void peek() {
        lock.lock();
        try {
            while (items.length == 0) {
                notEmpty.await();
                System.out.println(Thread.currentThread().getName() + "wait");
            }
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            count--;
            notFull.signal();
            items[removeIndex] = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BoundedQueue boundedQueue = new BoundedQueue(5);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> boundedQueue.add("obj")).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> boundedQueue.peek()).start();
        }
    }
}
