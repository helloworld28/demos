package com.jim.java8;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author Jim
 * @date 2018/5/26
 */
public class PipeLIneTest {
    public static void main(String[] args) {
        UnaryOperator<String> processing1 = (arg) -> {
            System.out.println("this processing1 "+ arg);
            return "1";
        };

        UnaryOperator processing2 = (arg) -> {
            System.out.println(" processing2 "+ arg);
            return "2";
        };

        Function function = processing1.andThen(processing2);
        function.apply("Bar");

    }
}
