package com.jim.java8.testNG;

import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * @author Jim
 * @date 2018/8/4
 */
public class DataProvider {

    @org.testng.annotations.DataProvider(name = "provideNumbers")
    private Object[][] provideNumbers(Method method) {
        Object[][] result = null;
        if (method.getName().equals("two")) {
            result = new Object[][]{new Object[]{2}};
        } else if (method.getName().equals("three")) {
            result = new Object[][]{new Object[]{3}};
        }
        return result;
    }

    @Test(dataProvider = "provideNumbers")
    public void two(int param) {
        System.out.println("two received:"+param);
    }


    @Test(dataProvider ="provideNumbers")
    public void three(int param) {
        System.out.println("three received:"+param);
    }

}
