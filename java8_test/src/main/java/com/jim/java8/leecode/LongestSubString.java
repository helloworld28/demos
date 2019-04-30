package com.jim.java8.leecode;

import java.util.*;

/**
 * @author Jim
 * @date 2019/3/5
 */
public class LongestSubString {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring3("abba"));
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();

        String subString = "";
        String longestSubstring = "";
        int startIndex = 0;
        for (int i = 0; i < chars.length; i++) {
            if (subString.indexOf(chars[i]) == -1) {
                subString += chars[i];
            } else {
                if (longestSubstring.length() <= subString.length()) {
                    longestSubstring = subString;
                }
                subString = "";
                i = startIndex;
                startIndex++;
            }
        }
        if (longestSubstring.length() <= subString.length()) {
            longestSubstring = subString;
        }

        System.out.println(longestSubstring);
        return longestSubstring.length();
    }

    /**
     * 利用滑动窗口
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s) {
        Set<Character> subSet = new HashSet<>();
        int i = 0, j = 0;
        int ans = 0;
        while (i < s.length() && j < s.length()) {
            if (!subSet.contains(s.charAt(j))) {
                subSet.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                subSet.remove(s.charAt(i++));
            }
        }
        return ans;
    }


    public static int lengthOfLongestSubstring3(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int i = 0, j = 0;
        int ans = 0;
        for (; j < s.length(); j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            map.put(s.charAt(j), j + 1);
            ans = Math.max(ans, j - i + 1);
        }
        return ans;
    }
}
