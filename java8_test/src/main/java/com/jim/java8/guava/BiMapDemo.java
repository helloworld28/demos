package com.jim.java8.guava;

import com.google.common.collect.*;

import java.util.List;

/**
 * 双向map
 *
 * @author Jim
 * @date 2019/3/13
 */
public class BiMapDemo {

    public static void main(String[] args) {
        BiMap<String, Integer> userForId = HashBiMap.create();
        userForId.forcePut("foo", 1);
        userForId.forcePut("bar", 1);

        userForId.inverse().get(1);

        System.out.println(userForId.inverse().get(1));

        ListMultimap<Object, Object> map = MultimapBuilder.hashKeys().arrayListValues().build();
        map.put("a", 1);
        map.put("a", 2);
        map.put("a", 3);
        List<Object> a = map.get("a");
        System.out.println(a);

    }

}
