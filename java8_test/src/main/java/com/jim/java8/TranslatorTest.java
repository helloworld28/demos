package com.jim.java8;

/**
 * Created by Jim on 2017/8/21.
 */
public class TranslatorTest {
    public static void main(String[] args) {
        byte[] bytes = DataConverter.hexStringToByte("3C5B045D3E36");
        System.out.println((char)bytes[0]);
    }
}
