package com.jim.java8.decorator;

/**
 * @author Jim
 * @date 2018/2/10
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        this.description = "HouseBlend";
    }

    @Override
    public double cost() {
        return 2.99;
    }
}
