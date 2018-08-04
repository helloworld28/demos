package com.jim.java8;

import java.util.Arrays;

/**
 * @author Jim
 * @date 2018/3/20
 */
public class MaopaoTest {



    private static void order(Integer[] array){
        for (int i =0; i< array.length; i++){
            for(int j = 1; j < (array.length - i); j++){
                if(array[j-1] > array[j]){
                    int temp = array[j -1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }
            }
        }

    }

    public static void main(String[] args) {
        Integer[] ints = {11,19,3, 1, 5, 9, 2, 5,3};
        order(ints);
        System.out.println(Arrays.asList(ints));
    }
}
