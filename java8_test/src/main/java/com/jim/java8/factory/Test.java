package com.jim.java8.factory;

/**
 * @author Jim
 * @date 2018/2/11
 */
public class Test {

    public static void main(String[] args) {
        PizzaStore nyPizzaStore = new NYPizzaStore();
        PizzaStore chicagoPizzaStore = new ChicagoPizzaStore();

        System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓Order NyPizza↓↓↓↓↓↓↓↓↓↓");
        nyPizzaStore.orderPizza("cheese");
        System.out.println("↑↑↑↑↑↑↑↑↑↑Order NyPizza↑↑↑↑↑↑↑");
        System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓Order chicagoPizzaStore↓↓↓↓↓↓↓↓↓↓");
        chicagoPizzaStore.orderPizza("cheese");
        System.out.println("↑↑↑↑↑↑↑↑↑↑Order chicagoPizzaStore↑↑↑↑↑↑↑");

    }
}
