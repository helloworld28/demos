package com.jim.java8.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jim
 * @date 2019/2/14
 */
public class ReentrantLockDemo {

    private ReentrantLock lock = new ReentrantLock();
    private int i;

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        new Thread(() -> {
            reentrantLockDemo.foo();
        },"Thread1").start();
        new Thread(() -> {
            reentrantLockDemo.foo();
        },"Thread2").start();

        new Thread(() -> {
            reentrantLockDemo.foo();
        },"Thread3").start();


        new Thread(() -> {
            reentrantLockDemo.foo();
        },"Thread4").start();

        new Thread(() -> {
            reentrantLockDemo.foo();
        },"Thread5").start();

        System.out.println(" main over");
    }

    private void foo() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+":doSomething....");
            TimeUnit.SECONDS.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}
