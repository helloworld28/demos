package com.jim.java8.threads;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Jim
 * @date 2019/2/23
 */
public class HashMapDeadLockTest {

    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    objectObjectHashMap.put(UUID.randomUUID().toString(), "");
                }
            }).start();
        }
    }
}
