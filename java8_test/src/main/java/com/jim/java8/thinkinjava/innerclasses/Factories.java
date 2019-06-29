package com.jim.java8.thinkinjava.innerclasses;

import jdk.internal.dynalink.linker.LinkerServices;

/**
 * @author Jim
 * @date 2019/5/3
 */
public class Factories {
    interface Service {
        void method1();

        void method2();
    }

    interface ServiceFactory {
        Service getService();
    }

    static class Implementation1 implements Service {

        @Override
        public void method1() {
            System.out.println("Implementation1 method1");
        }

        @Override
        public void method2() {
            System.out.println("Implementation1 method2");
        }

        public static ServiceFactory factory = new ServiceFactory() {
            @Override
            public Service getService() {
                return new Implementation1();
            }
        };
    }

    static class Implementation2 implements Service {

        @Override
        public void method1() {
            System.out.println("Implementation2 method1");
        }

        @Override
        public void method2() {
            System.out.println("Implementation2 method2");
        }

        public static ServiceFactory factory = new ServiceFactory() {
            @Override
            public Service getService() {
                return new Implementation2();
            }
        };

    }


    public static void serviceConsumer(ServiceFactory serviceFactory) {
        Service service = serviceFactory.getService();
        service.method1();
        service.method2();
    }

    public static void main(String[] args) {
        serviceConsumer(Implementation1.factory);
        serviceConsumer(Implementation2.factory);
    }
}
