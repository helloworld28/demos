package com.jim.java8.leecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= target) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] == (target - nums[i])) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] result = new Solution().twoSum(new int[]{0, 4, 3, 0}, 0);
        System.out.println(result[0]);
        System.out.println(Math.round(-1.5));
        new ArrayList();

    }
}