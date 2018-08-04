package com.jim.java8.command;

/**
 * @author Jim
 * @date 2018/2/12
 */
public class CDPlayer {

    public void on() {
        System.out.println("CDPlayer is on!!!");
    }

    public void play() {
        System.out.println("CDPlayer is playing !!!!");

    }

    public void setVolumn(int i) {
        System.out.println("CDPlayer the volumn is " + i);
    }

    public void off() {
        System.out.println("CDPlayer is off");
    }
}
