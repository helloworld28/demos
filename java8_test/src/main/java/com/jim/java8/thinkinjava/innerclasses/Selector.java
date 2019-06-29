package com.jim.java8.thinkinjava.innerclasses;

/**
 * @author Jim
 * @date 2019/5/2
 */
interface Selector {
    boolean end();

    Object current();

    Object next();
}
