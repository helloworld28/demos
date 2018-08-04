package com.jim.java8;

import com.jim.java8.example.Dish;

import java.util.*;
import java.util.stream.Stream;

import static com.jim.java8.example.Dish.menu;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by Jim on 2017/11/1.
 */
public class ReduceTest {
    public static void main(String[] args) {
        Integer reduce = Stream.of(1, 2, 3, 4, 5, 6, 7).reduce(0, (a, b) -> a + b);
        Optional<Integer> reduce1 = Stream.of(2, 3, 4).reduce((a, b) -> a + b);
        System.out.println(reduce1.isPresent());
        System.out.println(reduce);

        System.out.println("filter 与 distinct");
        Stream.of(1, 2, 1, 3, 3, 2, 4).filter(i -> i % 2 == 0).distinct().forEach(System.out::println);



        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(toList());
        dishes.forEach(System.out::println);

        menu.stream().filter(d -> d.getType() == Dish.Type.MEAT).collect(toList());

        List<String> dishNames = menu.stream().map(Dish::getName).collect(toList());

        List<String> words = Arrays.asList("Hello", "Java", "Lamada");
        List<Integer> collect = words.stream().map(String::length).collect(toList());
        Optional<String> reduce2 = words.stream().map(d -> d.substring(0, 1)).reduce((a, b) -> a + b);
        System.out.println(reduce2.get());

        //取不同的字母
        List<String[]> collect1 = words.stream().map(d -> d.split("")).distinct().collect(toList());
        List<String> collect2 = words.stream().map(d -> d.split("")).flatMap(Arrays::stream).distinct().collect(toList());


        List<Integer> collect3 = Stream.of(1, 2, 3, 4).map(s -> s * s).collect(toList());
        System.out.println("collect3:" + collect3);

        //返回所有的数对
        List<Integer> nums1 = Arrays.asList(1, 2);
        List<Integer> nums2 = Arrays.asList(3, 4, 5);
        List<Integer[]> collect4 = nums1.stream().flatMap(s -> {
            List<Integer[]> pairs = new ArrayList<>();
            nums2.forEach(n -> pairs.add(new Integer[]{s, n}));
            return pairs.stream();
        }).collect(toList());
        System.out.println("collect4:" + collect4.size());
        //第二种方法
        List<int[]> collect5 = nums1.stream().flatMap(i -> nums2.stream().map(j -> new int[]{i, j})).collect(toList());

        //返回所有的数对中能被3整除的
        List<int[]> collect6 = nums1.stream().flatMap(i -> nums2.stream().map(j -> new int[]{i, j}))
                .filter(s -> Arrays.stream(s).reduce((a, b) -> a + b).getAsInt() % 2 == 0)
                .collect(toList());

        List<int[]> collect7 = nums1.stream()
                .flatMap(i -> nums2.stream()
                        .filter(j -> (i + j) % 2 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(toList());
        System.out.println("collect6:" + collect6.size());

        //查找元素

        Optional<Dish> any = menu.stream().filter(d -> d.getType() == Dish.Type.MEAT).findAny();

        //map-reduce 常放在一下使用,google常用
        menu.stream().map(d -> 1).reduce(0, (a, b) -> a + b);




    }
}
