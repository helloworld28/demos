package com.jim.java8;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * @author Jim
 * @date 2018/5/19
 */
public class SingletonInner {

    {
        System.out.println("inner ....");
    }
    public static SingletonInner singletonInner = new SingletonInner();
    public static class Holder {
        public static SingletonInner singletonInner = new SingletonInner();
        {
            System.out.println("holder init");
        }

    }

    private SingletonInner() {
        System.out.println("SingletonInner init...");
    }

    public static SingletonInner getInstance() {
        return Holder.singletonInner;
    }

    public static void foo(){
        System.out.println("foo..");
    }

    public static void main(String[] args) {
        SingletonInner.foo();
//        SingletonInner.getInstance();

    }
}
