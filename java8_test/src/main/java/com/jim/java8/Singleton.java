package com.jim.java8;

/**
 * @author Jim
 * @date 2018/2/11
 */
public class Singleton {
    public volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
