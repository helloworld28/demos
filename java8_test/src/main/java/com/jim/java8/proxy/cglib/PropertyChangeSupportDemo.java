package com.jim.java8.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;

/**
 * 属性变化监听
 *
 * @author Jim
 * @date 2018/10/15
 */
public class PropertyChangeSupportDemo {


    private static class Person {
        private String name;
        private String sex;
        private int age;


        public Person() {
        }

        private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


        private void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
            propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
        }

        private void removePropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            String oldValue = this.name;
            this.name = name;
            propertyChangeSupport.firePropertyChange("name", oldValue, name);

        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            String oldValue = this.sex;
            this.sex = sex;
            propertyChangeSupport.firePropertyChange("sex", oldValue, sex);
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            int oldValue = this.age;
            this.age = age;
            propertyChangeSupport.firePropertyChange("age", oldValue, age);
        }
    }


    public static void main(String[] args) {
        Person person = new Person();
        person.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt);
            }
        });
        person.setName("Jack");
        person.setName("Michael");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Person.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("Cglib:开始调用方法：" + method.getName());
                Object retVal = proxy.invokeSuper(obj, args);
                System.out.println("Cglib:调用方法完成结果：" + retVal);
                return retVal;
            }
        });

        Person person2 = (Person) enhancer.create();
        person2.setName("Jim");


    }
}
