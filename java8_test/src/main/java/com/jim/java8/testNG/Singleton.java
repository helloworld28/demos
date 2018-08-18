package com.jim.java8.testNG;

import org.testng.Assert;

/**
 * @author Jim
 * @date 2018/8/7
 */
public class Singleton {
    private static Singleton singleton = null;


    public static Singleton getInstance() {
        if (singleton == null) {
            Thread.yield();
            Assert.assertNull(singleton);
            singleton = new Singleton();
        }
        return singleton;
    }

}
