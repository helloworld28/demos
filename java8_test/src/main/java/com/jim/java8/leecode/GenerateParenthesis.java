package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 * <p>
 * 例如，给出 n = 3，生成结果为：
 * <p>
 * [
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/8/7
 */
public class GenerateParenthesis {

    @Test
    public void test() {
        List<String> strings = generateParenthesis(3);
        System.out.println(strings);
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateAll(new char[2 * n], 0, 0, 0, result);
        return result;
    }

    public void generateAll(char[] result, int post, int leftCount, int rightCount, List<String> list) {

        if (post == result.length) {
            if (isValid(result)) {
                list.add(new String(result));
            }
        } else {
            if (leftCount < result.length) {
                result[post] = '(';
                generateAll(result, post + 1, leftCount++, rightCount, list);
            }
            if (leftCount > rightCount) {
                result[post] = ')';
                generateAll(result, post + 1, leftCount, rightCount++, list);
            }
        }
    }

    public boolean isValid(char[] result) {
        int leftCount = 0;
        int rightCount = 0;
        for (char c : result) {
            if (c == '(') {
                leftCount++;
            } else {
                rightCount++;
            }
            if (leftCount - rightCount < 0) {
                return false;
            }
        }
        if (leftCount != rightCount) {
            return false;
        }

        return true;
    }
}
