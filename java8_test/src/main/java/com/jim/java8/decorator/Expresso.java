package com.jim.java8.decorator;

/**
 * @author Jim
 * @date 2018/2/10
 */
public class Expresso extends Beverage {

    public Expresso() {
        this.description = "Expresso";
    }

    @Override
    public double cost() {
        return .89;
    }
}
