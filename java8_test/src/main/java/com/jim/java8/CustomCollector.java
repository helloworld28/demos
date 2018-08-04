package com.jim.java8;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Jim
 * @date 2018/1/9
 */
public class CustomCollector {
    public static void main(String[] args) {
        System.out.println(partitionPrimes(20));
    }


    public static Map<Boolean, List<Integer>> partitionPrimes(Integer num) {
        return IntStream.rangeClosed(2, num).boxed().collect(Collectors.partitioningBy(candidate -> isPrimes(candidate)));
    }

    public static boolean isPrimes(int candidate) {
        int numRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, numRoot).noneMatch(n -> candidate % n == 0);

    }

}
