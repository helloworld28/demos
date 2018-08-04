package com.jim.java8;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jim
 * @date 2018/1/19
 */
public class FileReadTest {

    public static  String foo = "bar";

    public static final  int foo2 = 1;

    static {
        System.out.println("fooabar.....");
        foo = "bar22";
    }
    public static void main(String[] args) {



        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("23", "12");
        map.put("123", "11");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {

        }

        System.out.println(map);

        System.out.println(FileReadTest.foo);
    }
}
