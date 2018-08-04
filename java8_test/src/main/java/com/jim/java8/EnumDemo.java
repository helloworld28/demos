package com.jim.java8;

/**
 * @author Jim
 * @date 2018/5/19
 */
public enum  EnumDemo {
    MONDAY("monday", 1),
    WEBSDAY("WEBSDAY", 2),
    THRUSDAY("THRUSDAY", 3),
    FRIDAY("FRIDAY", 4),
    SATUREADAY("SATUREADAY", 5),
    SUNDAY("SUNDAY", 6);

    private String name;
    private int value;

    EnumDemo(String name, int value) {
        this.name = name;
        this.value = value;
    }


    public static void main(String[] args) {
        EnumDemo.valueOf("");
    }
}
