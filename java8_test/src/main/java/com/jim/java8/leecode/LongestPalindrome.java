package com.jim.java8.leecode;

import java.util.*;

/**
 * @author Jim
 * @date 2019/3/8
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        System.err.println(new LongestPalindrome().longestPalindrome("aaabaaaa"));
        System.err.println(new LongestPalindrome().longestPalindrome("a"));
    }

    public String longestPalindrome(String s) {
        int j = s.length();
        Map<Character, List<Integer>> power = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            List<Integer> integers = power.computeIfAbsent(s.charAt(i), k -> new ArrayList<>());
            integers.add(i);
        }

        if (s.length() < 2) {
            return s;
        }

        String longestSubString = String.valueOf(s.charAt(0));
        for (int i = 0; i < j; i++) {

            List<Integer> integers = power.get(s.charAt(i));
            if (integers.size() < 2) continue;
            for (int index : integers) {
                if (index > i) {
                    String substring = s.substring(i, index + 1);

                    if (isPalindrome(substring) && substring.length() > longestSubString.length()) {
                        longestSubString = substring;
                    }
                }
            }


        }

        longestSubString = longestSubString.equals("") ? s : longestSubString;

        return longestSubString;
    }

    public boolean isPalindrome(String subString) {
        char[] chars = subString.toCharArray();
        char[] newChars = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            newChars[newChars.length - 1 - i] = chars[i];
        }
        String s = new String(newChars);
        return subString.equals(s);
    }
}
