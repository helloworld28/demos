package com.jim.java8;

import java.util.Stack;

/**
 * @author Jim
 * @date 2019/4/27
 */
public class StackTest {
    public static void main(String[] args) {
        //使用栈来实现反转字符串
        Stack<String> strings = new Stack<>();
        for (String str : "My dog has fleas".split(" ")) {
            strings.push(str);
        }

        while (!strings.empty()) {
            System.out.print(" ");
            System.out.print(strings.pop());
        }
    }
}
