package com.jim.java8.testNG;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Jim
 * @date 2018/8/7
 */
public class ConcurrentTest {

    Singleton singleton = new Singleton();

    @BeforeClass
    public void init() {
        singleton = new Singleton();
    }


    @Test(invocationCount = 1000, threadPoolSize = 100)
    public void testSingleton() {
        Thread.yield();

        singleton.getInstance();
    }
}
