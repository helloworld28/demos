package com.jim.java8.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jim
 * @date 2018/2/11
 */
public abstract class Pizza {
    String name;
    String dough;
    String sauce;
    List toppings = new ArrayList();

    void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Tossing dough ...");
        System.out.println("Adding sauce... ");
        System.out.println(String.format("Adding Topping[%s]", toppings));

    }

    void bake() {
        System.out.println("back Pizza:" + name);
    }

    void cut() {
        System.out.println("cut the pizza:" + name);
    }

    void box(){
        System.out.println("box:"+ name);
    }

    public String getName(){
        return this.name;
    }
}
