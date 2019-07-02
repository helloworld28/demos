package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/roman-to-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/7/2
 */
public class RomanToInt {

    @Test
    public void test() {

        assertEquals(new RomanToInt().romanToInt("XXVII"), 27);
        assertEquals(new RomanToInt().romanToInt("XII"), 12);
        assertEquals(new RomanToInt().romanToInt2("MCMXCIV"), 1994);
    }

    public int romanToInt(String s) {
        int[] levelArray = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] charArray = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        Map<String, Integer> dict = new HashMap<>(charArray.length);
        for (int i = 0; i < charArray.length; i++) {
            dict.put(charArray[i], levelArray[i]);
        }

        int result = 0;
        char[] chars = s.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (j + 1 < chars.length) {
                String key = String.valueOf(chars[j]) + String.valueOf(chars[j + 1]);
                Integer value = dict.get(key);
                if (value != null) {
                    result += value;
                    j = j + 1;
                    continue;
                }

                result += dict.get(String.valueOf(chars[j]));
            } else {
                result += dict.get(String.valueOf(chars[j]));
            }
        }
        return result;

    }

    public int romanToInt2(String s) {
        Map<Character, Integer> charToIntMap = new HashMap<>(10);
        charToIntMap.put('I', 1);
        charToIntMap.put('V', 5);
        charToIntMap.put('X', 10);
        charToIntMap.put('L', 50);
        charToIntMap.put('C', 100);
        charToIntMap.put('D', 500);
        charToIntMap.put('M', 1000);
        char[] chars = s.toCharArray();
        int preNum = 0;
        int result = 0;
        for (char c : chars) {
            Integer curNum = charToIntMap.get(c);
            if (preNum < curNum) {
                result = result - 2 * preNum;
            }
            result += curNum;
            preNum = curNum;
        }
        return result;

    }

}
