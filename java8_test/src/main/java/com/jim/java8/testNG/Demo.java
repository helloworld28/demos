package com.jim.java8.testNG;

import org.testng.annotations.Test;

/**
 * @author Jim
 * @date 2018/8/4
 */
public class Demo {


    @Test(timeOut = 10000, invocationCount = 1000,
            successPercentage = 98)
    public void waitForAnswer() {

    }
}
