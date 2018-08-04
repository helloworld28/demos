package com.jim.java8.iterator;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Jim
 * @date 2018/4/21
 */
public class CompositeIterator implements Iterator {

    Stack stack = new Stack();

    public CompositeIterator(Iterator iterator) {
        stack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        if (stack.isEmpty()) {
            return false;
        }
        Iterator iterator = (Iterator) stack.peek();
        if (iterator.hasNext()) {
            return true;
        } else {
            stack.pop();
            return hasNext();
        }
    }

    @Override
    public Object next() {
        if (hasNext()) {
            MenuComponent menuComponent = (MenuComponent) stack.peek();
            if (menuComponent instanceof Menu) {
                stack.push(menuComponent.createIterator());
            }
            return menuComponent;
        }
        return null;
    }

    @Override
    public void remove() {

    }
}
