package com.jim.java8;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author Jim
 * @date 2018/5/19
 */
public class BitVetory {

    private int count;
    private int[] data;
    private final int BIT_LENGTH = 32;
    private int SHIFT = 5;
    private int MASK = 0x1f;
    private int P;
    private int S;


    public BitVetory(int n) {
        data = new int[(n / BIT_LENGTH) + 1];
        count = n;
    }

    public void set(int i) {
        P = i >> SHIFT;
        S = i & MASK;
        data[P] |= 1 << S;
    }

    public int get(int i) {
        P = i >> SHIFT;
        S = i & MASK;
        return Integer.bitCount(data[P] & 1 << S);
    }

    public void clear(int i){
        P = i >> SHIFT;
        S = i & MASK;
        data[P] &= ~(1 << S);
    }

    public List<Integer> getValues() {
        List result = new ArrayList();

        for (int i = 0; i < count; i++) {
            if (get(i) > 0) {
                result.add(i);
            }
        }
        return result;
    }


    private static List<Integer> getRandomsList(int maxNUm, int count) {
        Random random = new Random();

        List<Integer> randomsList = new ArrayList<Integer>();
        while (randomsList.size() < (count - 1)) {
            int element = random.nextInt(count - 1) + 1;//element ∈  [1,count)
            if (!randomsList.contains(element)) {
                randomsList.add(element);
            }
        }
        return randomsList;
    }

    public static void main(String[] args) {
        int maxNum = 1_000_000_0;

        List<Integer> randomsList = getRandomsList(maxNum, 1000);
        Collections.shuffle(randomsList);
        System.out.println("排序前：" + randomsList);
        BitVetory bitVetory = new BitVetory(maxNum);
        randomsList.forEach(bitVetory::set);
        List<Integer> values = bitVetory.getValues();
        System.out.println("排序后："+values);
    }
}
