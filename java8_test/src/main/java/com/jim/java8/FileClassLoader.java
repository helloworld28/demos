package com.jim.java8;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author Jim
 * @date 2018/5/22
 */
public class FileClassLoader extends URLClassLoader
{

    public FileClassLoader(URL[] urls) {
        super(urls);
    }

    public FileClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    protected Object getClassLoadingLock(String className) {
        return super.getClassLoadingLock(className);
    }
}
