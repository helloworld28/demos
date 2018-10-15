package com.jim.java8.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sun.reflect.generics.tree.ReturnType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Jim
 * @date 2018/10/15
 */
public class Beans implements MethodInterceptor {

    private PropertyChangeSupport propertyChangeSupport;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public static Object newInstance(Class clazz) {

        Beans callback = new Beans();
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(callback);
        enhancer.setSuperclass(clazz);
        Object bean = enhancer.create();
        callback.propertyChangeSupport = new PropertyChangeSupport(bean);
        return bean;

    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object retValFromSuper = null;
        if (!Modifier.isAbstract(method.getModifiers())) {
            retValFromSuper = proxy.invokeSuper(obj, args);
        }
        String methodName = method.getName();
        if (methodName.equals("addPropertyChangeListener")) {
            addPropertyChangeListener((PropertyChangeListener) args[0]);
        } else if (methodName.equals("removePropertyChangeListner")) {
            removePropertyChangeListener((PropertyChangeListener) args[0]);
        }
        if (methodName.startsWith("set") &&
                args.length == 1 &&
                method.getReturnType() == Void.TYPE) {
            char[] chars = methodName.substring("set".length()).toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            String propertyName = new String(chars);


            propertyChangeSupport.firePropertyChange(propertyName, null, args[0]);
        }


        return retValFromSuper;
    }

    public static void main(String[] args) {
        Bean bean = (Bean) newInstance(Bean.class);
        bean.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt);
            }
        });
        bean.setSampleProperty("foo");
        bean.setSampleProperty("bar");
    }
}
