package com.jim.java8.decorator;

/**
 * @author Jim
 * @date 2018/2/10
 */
public abstract class Beverage {
    String description;

    public String getDescription() {
        return this.description;
    }

    public abstract double cost();
}
