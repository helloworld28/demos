package com.jim.java8.threads;

import sun.security.jca.GetInstance;

/**
 * 利用内部类来实现单例的延迟初始化,线程安全
 * 因为类在初始化是会加锁
 *
 * @author Jim
 * @date 2019/2/13
 */
public class SafeLazyInitSingleInstance {

    public static class InstanceHolder {
        public static DoubleCheckLocking instance = new DoubleCheckLocking();
    }

    public static DoubleCheckLocking getInstance() {
        return InstanceHolder.instance;
    }
}
