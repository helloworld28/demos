package com.jim.java8.thinkinjava.innerclasses;

/**
 * 接口里可以有内部类
 *
 * @author Jim
 * @date 2019/5/3
 */
public interface ClassInInterface {
    void howdy();

    class Test implements ClassInInterface {

        @Override
        public void howdy() {
            System.out.println("Howdy");
        }


        public static void main(String[] args) {
            new Test().howdy();
            ;
        }
    }


}
