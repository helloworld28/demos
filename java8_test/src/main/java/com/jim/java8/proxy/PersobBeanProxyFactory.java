package com.jim.java8.proxy;

import java.lang.reflect.Proxy;

/**
 * @author Jim
 * @date 2018/5/11
 */
public class PersobBeanProxyFactory {

    public static PersonBean getOwenerInvocationHandler(PersonBean personBean) {
        return (PersonBean) Proxy.newProxyInstance(personBean.getClass().getClassLoader(),
                personBean.getClass().getInterfaces(),
                new OwenerInvocationHandler(personBean));
    }

    public static PersonBean getNonOwenerInvocationHandler(PersonBean personBean) {
        return (PersonBean) Proxy.newProxyInstance(personBean.getClass().getClassLoader(),
                personBean.getClass().getInterfaces(),
                new NonOwenerInvocationHandler(personBean));
    }


}
