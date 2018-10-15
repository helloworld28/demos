package com.jim.java8.proxy.cglib;

import net.sf.cglib.core.KeyFactory;

/**
 * keyFactory
 * 使用场景是当key为多种类型，多个对象时
 * 可能使用keyFactory来生成一个key
 *
 * @author Jim
 * @date 2018/10/15
 */
public class KeySample {
    private interface MyFactory {
        public Object newInstance(int a, char[] b, String d);
    }

    public static void main(String[] args) {
        MyFactory keyFactory = (MyFactory) KeyFactory.create(MyFactory.class);
        Object key1 = keyFactory.newInstance(20, new char[]{'a', 'b'}, "hello");
        Object key2 = keyFactory.newInstance(20, new char[]{'a', 'b'}, "hello");
        Object key3 = keyFactory.newInstance(20, new char[]{'a', '_'}, "hello");
        System.out.println(key1.equals(key2));
        System.out.println(key2.equals(key3));

    }
}
