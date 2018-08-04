package com.jim.java8.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jim
 * @date 2018/4/21
 */
public class Menu extends MenuComponent {
    private String name;
    private String description;
    List<MenuComponent> childs = new ArrayList<>();


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void print() {
        System.out.println(toString());
    }

    @Override
    public void add(MenuComponent menuComponent) {
        childs.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        childs.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(int i) {
        return childs.get(i);
    }

    @Override
    public Iterator createIterator() {
//        return new CompositeIterator();
        return null;
    }


    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", childs=" + childs +
                '}';
    }
}
