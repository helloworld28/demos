package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * <p>
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * @author Jim
 * @date 2019/7/29
 */
public class letterCombinations {


    String[][] letters = new String[][]{{}, {"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}, {"j", "k", "l"},
            {"m", "n", "o"}, {"p", "q", "r", "s"}, {"t", "u", "v"}, {"w", "x", "y", "z"}};

    @Test
    public void test() {
        List<String> strings = letterCombinations("234");
        System.out.println(strings);
    }

    //使用递归
    public List<String> letterCombinations(String digits) {

        if (digits == null || "".equals(digits)) return Collections.emptyList();

        int index = Integer.valueOf(String.valueOf(digits.charAt(0))) - 1;
        if (digits.length() == 1) {
            return Arrays.asList(letters[index]);
        }
        return combine(Arrays.asList(letters[index]), digits.substring(1));
    }

    public List<String> combine(List<String> list, String digits) {
        Integer index = Integer.valueOf(String.valueOf(digits.charAt(0))) - 1;
        List<String> res = new ArrayList<>();
        for (String str : list) {
            for (String letter : letters[index]) {
                res.add(str + letter);
            }
        }
        if (digits.length() > 1) {
            return combine(res, digits.substring(1));
        }
        return res;
    }
}
