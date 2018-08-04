package com.jim.java8.template;

/**
 * @author Jim
 * @date 2018/3/17
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("making tea....");
        Tea tea = new Tea();
        tea.prepareRecipe();
        System.out.println("making Coffee...");
        Coffee coffee = new Coffee();
        coffee.prepareRecipe();
    }
}
