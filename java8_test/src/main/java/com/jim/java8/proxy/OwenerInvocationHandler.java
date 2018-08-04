package com.jim.java8.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Jim
 * @date 2018/5/11
 */
public class OwenerInvocationHandler implements InvocationHandler {

    private PersonBean personBean;

    public OwenerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equalsIgnoreCase("setHotOrNotRating")) {
            throw new IllegalAccessException("no allow setHotOrNotRating");
        } else {
            method.invoke(personBean, args);
        }
        return null;
    }
}
