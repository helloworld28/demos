package com.jim.java8.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Jim
 * @date 2018/5/11
 */
public class NonOwenerInvocationHandler implements InvocationHandler {


    private PersonBean personBean;

    public NonOwenerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("set") && !method.getName().equalsIgnoreCase("setHotOrNotRating")) {
            throw new IllegalAccessException("not Allow method");
        } else {
            method.invoke(personBean, args);
        }
        return null;
    }
}
