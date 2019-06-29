package com.jim.java8.mybatis;

/**
 * 方法引用直接就包装到一个runnable
 *
 * @author Jim
 * @date 2019/5/25
 */
public class Test {


    public static void main(String[] args) {
        bar(Test::foo);
    }

    public static void foo() {
        System.out.println("foo");
    }

    public static void bar(Runnable runnable) {
        runnable.run();
    }
}
