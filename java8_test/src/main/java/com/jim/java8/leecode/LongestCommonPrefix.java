package com.jim.java8.leecode;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1:
 * <p>
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 * <p>
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 * <p>
 * 所有输入只包含小写字母 a-z 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-common-prefix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/7/3
 */
public class LongestCommonPrefix {

    @Test
    public void test() {
        assertEquals("fl", new LongestCommonPrefix().longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        assertEquals("", new LongestCommonPrefix().longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        assertEquals("fl", new LongestCommonPrefix().longestCommonPrefix2(new String[]{"flower", "flow", "flight"}));
        assertEquals("", new LongestCommonPrefix().longestCommonPrefix2(new String[]{"dog", "racecar", "car"}));
    }


    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String shortestStr = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() < shortestStr.length()) {
                shortestStr = strs[i];
            }
        }
        String lognestCommon = "";
        for (int i = 0; i < shortestStr.length(); i++) {
            String subStr = shortestStr.substring(0, i + 1);
            for (String str : strs) {
                if (!str.startsWith(subStr)) {
                    return lognestCommon;
                }
            }
            lognestCommon = subStr;
        }
        return lognestCommon;
    }

    public String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.equals("")) return prefix;
            }
        }
        return prefix;
    }
}
