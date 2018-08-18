package com.jim.java8.guava;

import org.testng.util.Strings;

import java.sql.Wrapper;
import java.util.List;
import java.util.Objects;

/**
 * @author Jim
 * @date 2018/8/17
 */
public class StringsTest {

    public static void main(String[] args) {
        System.out.println(Strings.escapeHtml("<HTML>"));
        System.out.println(Strings.getValueOrEmpty(null));
        System.out.println(Strings.isNotNullAndNotEmpty(null));
        System.out.println(Strings.isNotNullAndNotEmpty(""));


    }
}
