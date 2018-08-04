package com.jim.java8.template;

/**
 * @author Jim
 * @date 2018/3/17
 */
public class Tea extends CaffeinBeverage{
    @Override
    protected void addCondiments() {
        System.out.println("add Lemon");
    }

    @Override
    protected void brew() {
        System.out.println("steepTeaBag");
    }
}
