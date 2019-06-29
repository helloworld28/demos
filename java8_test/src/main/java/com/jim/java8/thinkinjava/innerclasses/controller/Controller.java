package com.jim.java8.thinkinjava.innerclasses.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jim
 * @date 2019/5/3
 */
public class Controller {
    private List<Event> events = new ArrayList<>();

    public void add(Event event) {
        events.add(event);
    }

    public void run() {
        while (events.size() > 0) {
            for (Event e : new ArrayList<>(events)) {
                if (e.ready()) {
                    e.action();
                    events.remove(e);
                    System.out.println(e);
                }
            }
        }
    }
}
