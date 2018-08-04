package com.jim.java8.factory;

/**
 * @author Jim
 * @date 2018/2/11
 */
public class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza() {
        this.name = "ChicagoStyleC";
        this.dough = "ChicagoStyleC crust Dougn";
        this.sauce = "ChicagoStyleC sauce";

        toppings.add("ChicagoStyleC");
    }

    @Override
    void cut() {
        System.out.println("ChicagoStyleC cuttt");
    }
}
