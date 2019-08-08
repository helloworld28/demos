package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "()"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 * <p>
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 * <p>
 * 输入: "([)]"
 * 输出: false
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/7/31
 */
public class StrIsValid {


    @Test
    public void test() {
        assertFalse(isValid("(["));
        assertTrue(isValid("()[]{}"));
        assertFalse(isValid("([)]"));
        assertFalse(isValid("]"));
        assertTrue(isValid("()[fdsafsa]{()}"));
    }


    //利用栈来实现
    public boolean isValid(String s) {
        Map<Character, Character> characterMap = new HashMap<>(3);
        characterMap.put('(', ')');
        characterMap.put('[', ']');
        characterMap.put('{', '}');

        List<Character> openCharacters = new ArrayList<>(3);
        openCharacters.add('(');
        openCharacters.add('{');
        openCharacters.add('[');

        List<Character> closeCharacters = new ArrayList<>(3);
        closeCharacters.add(')');
        closeCharacters.add(']');
        closeCharacters.add('}');

        Stack<Character> stack = new Stack<>();

        char[] chars = s.toCharArray();
        for (Character c : chars) {
            if (openCharacters.contains(c)) {
                stack.push(c);
            } else if (closeCharacters.contains(c)) {
                if (stack.empty()) {
                    return false;
                }
                Character pop = stack.pop();
                Character character = characterMap.get(pop);
                if (!character.equals(c)) {
                    return false;
                }
            }
        }

        return (stack.empty());
    }
}
