package com.jim.java8.threads;

/**
 * @author Jim
 * @date 2019/2/13
 */
public class DoubleCheckLocking {

    private volatile static DoubleCheckLocking instance;

    public static DoubleCheckLocking getInstance() {

        if (instance == null) {
            synchronized (DoubleCheckLocking.class) {
                if (instance == null) {
                    //
                    instance = new DoubleCheckLocking();
                }
            }
        }

        return instance;
    }


}
