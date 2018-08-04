package com.jim.java8.proxy;

/**
 * @author Jim
 * @date 2018/5/11
 */
public class MatchMakingTestDrive {
    public static void main(String[] args) {
        new MatchMakingTestDrive().drive();
    }

    public void drive() {
        PersonBean joe = new PersonBeanImpl();
        PersonBean jason = new PersonBeanImpl();

        PersonBean ownerProxy = PersobBeanProxyFactory.getOwenerInvocationHandler(joe);
        ownerProxy.setGender("Male");
        ownerProxy.setName("Joe");
        try {

            ownerProxy.setHotOrNotRating(100);
        }catch (Exception e){
            System.out.println("can't set HotNotRating from ownerProxy");
        }

        PersonBean nonOwenerInvocationHandler = PersobBeanProxyFactory.getNonOwenerInvocationHandler(jason);
        nonOwenerInvocationHandler.setHotOrNotRating(10);
        try {
            nonOwenerInvocationHandler.setName("JOOO");
        }catch (Exception e){
            System.out.println("can't set other value from nonOwenerInvocationHandler");
        }
        System.out.println();
        System.out.println("DONE!");

    }
}
