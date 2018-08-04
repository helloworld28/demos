package com.jim.java8;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Jim on 2017/11/20.
 */
public class Partitioning {
    public static void main(String[] args) {
        System.out.println(new Partitioning().isPrime(2));
//        System.out.println(new Partitioning().partitionPrimes(50));
    }
    public  Map<Boolean, List<Integer>> partitionPrimes(int n){
        Map<Boolean, List<Integer>> collect = IntStream.rangeClosed(2, n).boxed().collect(Collectors.partitioningBy(this::isPrime));
        return collect;
    }
    public  boolean isPrime(int n){
        return IntStream.rangeClosed(2, n).noneMatch(i-> n % i == 0);
    }

}
