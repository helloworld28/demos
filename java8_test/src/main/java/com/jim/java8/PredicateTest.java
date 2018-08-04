package com.jim.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Jim on 2017/7/11.
 */
public class PredicateTest {

    public static void main(String[] args) {
        Apple apple1 = new Apple("red", 10);
        Apple apple2 = new Apple("green", 20);


    }


    static class Apple{
        String color;
        int weight;

        public Apple(String color, int weight) {
            this.color = color;
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

    /**
     * Created by Jim on 2017/8/3.
     */
    public static class FunctionTest {

        public static void main(String[] args) {
            List foo = new ArrayList<>();
            List<String> strings = (List<String>) foo.stream().map(s -> s.toString()).collect(Collectors.toList());

            IntPredicate intPredicate = (int i) -> i % 2 == 0;

            System.out.println(intPredicate.test(10000));

            Predicate<Integer> integerPredicate = (Integer i) -> i % 2 == 1;
            System.out.println(integerPredicate.test(1000));

            List<String> list = Arrays.asList("A", "B", "A");
            list = (List<String>) list.stream().distinct().collect(Collectors.toList());
            System.out.println(list);
            List<Integer> collect = list.stream().map(s -> s.length()).collect(Collectors.toList());
            System.out.println(collect);

            List<String> wordList = Arrays.asList("Hello", "world");
            List<String> collect1 = wordList.stream().map(s -> s.split("")).flatMap(Arrays::stream).collect(Collectors.toList());
            System.out.println(collect1);

            List<Integer> num1 = Arrays.asList(0, 1, 2, 3);
            List<Integer> num2 = Arrays.asList(0, 4, 5, 6);
            List<int[]> collect2 = num1.stream().flatMap(i -> num2.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());
            System.out.println(collect2);


            Integer reduce = collect1.stream().map(s -> 1).reduce(0, (a, b) -> a + b);
            System.out.println("num of ch " + reduce);

            OptionalInt reduce1 = num1.stream().mapToInt(s -> s.intValue()).reduce(Integer::sum);
            System.out.println(reduce1.getAsInt());

            long count = IntStream.rangeClosed(0, 100).filter(s -> s % 2 == 0).count();
            System.out.println(count);

            List<int[]> collect3 = IntStream.rangeClosed(1, 100).boxed()
                    .flatMap(a -> IntStream.rangeClosed(a, 100)
                            .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                    .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})).limit(5).collect(Collectors.toList());
            collect3.forEach(s ->{
                System.out.println(s[0]+","+s[1]+","+s[2]);
            });


            try {
                long count1 = Files.lines(Paths.get("D:\\work\\projects\\src\\main\\resources\\data.txt")).flatMap(s -> Arrays.stream(s.split(" "))).count();
                System.out.println("words :"+ count1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
