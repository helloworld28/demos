package com.jim.java8.threads;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Jim
 * @date 2019/2/25
 */
public class DelayQueueDemo {

    public static void main(String[] args) {


    }

    class DelayItem implements Delayed {

        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }
}
