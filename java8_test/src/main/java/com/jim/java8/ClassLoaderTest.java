package com.jim.java8;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Jim
 * @date 2018/5/22
 */
public class ClassLoaderTest {

    public static void main(String[] args) {

        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        printParentClassLoader(classLoader);


        String rootDir="D:\\work\\projects\\target\\classes\\com\\jim\\java8\\test5";


        File file = new File(rootDir);

        try {
            FileClassLoader fileClassLoader = new FileClassLoader(new URL[]{file.toURI().toURL()}, ClassLoaderTest.class.getClassLoader());

            Class<Test> aClass = (Class<Test>) fileClassLoader.findClass("com.jim.java8.test5.Test");
            Test test = aClass.newInstance();
            test.foo();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void printParentClassLoader(ClassLoader classLoader){
        System.out.println(classLoader);
        if(classLoader.getParent() != null){
            printParentClassLoader(classLoader.getParent());
        }
    }
}
