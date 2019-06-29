package com.jim.java8.thinkinjava.innerclasses.controller;

/**
 * @author Jim
 * @date 2019/5/3
 */
public abstract class Event {
    private long eventTime;
    protected final long delayTime;

    public Event(long delayTime) {
        this.delayTime = delayTime;

    }

    public void start() {
        eventTime = System.nanoTime() + delayTime;

    }

    public boolean ready() {
        return System.nanoTime() >= eventTime;
    }

    public abstract void action();
}
