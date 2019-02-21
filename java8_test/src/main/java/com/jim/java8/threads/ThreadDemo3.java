package com.jim.java8.threads;

import java.util.LinkedList;

/**
 * @author Jim
 * @date 2019/2/14
 */
public class ThreadDemo3 {

    static class ResoucePool {
        private LinkedList<Object> pool = new LinkedList<Object>();

        public ResoucePool() {
            for (int i = 0; i < 10; i++) {
                pool.add(new Object());
            }
        }

        public void releaseResource(Object resourceItem) {
            synchronized (pool) {
                pool.add(resourceItem);
                pool.notifyAll();
            }
        }

        public Object fetchResource(long mill) {
            long remain = mill;
            long future = System.currentTimeMillis() + mill;
            synchronized (pool) {
                while (pool.isEmpty() && remain > 0) {
                    try {
                        pool.wait(mill);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    remain = future - System.currentTimeMillis();
                }
                if (pool.isEmpty()) {
                    return null;
                }
                return pool.removeFirst();
            }

        }
    }


}
