package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，
 * 使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 * <p>
 * 注意：
 * <p>
 * 答案中不可以包含重复的四元组。
 * <p>
 * 示例：
 * <p>
 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
 * <p>
 * 满足要求的四元组集合为：
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/4sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/7/30
 */
public class FourSum {

    @Test
    public void test() {
        List<List<Integer>> lists = fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0);
        System.out.println(lists);
        lists = fourSum(new int[]{-3, -2, -1, 0, 0, 1, 2, 3}, 0);
        System.out.println(lists);
        lists = fourSum(new int[]{0, 0, 0, 0}, 0);
        System.out.println(lists);
    }

    /**
     * 把四个数变求三个数的，就是在三个数的求和在外面再加上层
     * 注意要过滤到重复的
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {

        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; ) {

            int tempTarget = target - nums[i];

            for (int j = i + 1; j < nums.length; ) {
                int l = j + 1;
                int r = nums.length - 1;
                while (l < r) {
                    int sum = nums[l] + nums[j] + nums[r];
                    if (sum > tempTarget) {
                        r--;
                    } else if (sum < tempTarget) {
                        l++;
                    } else {
                        //下个数有与这个相同的直接跳过
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (nums[l] == nums[++l] && l < r) ;
                        while (nums[r] == nums[--r] && l < r) ;
                    }
                }
                //下个数有与这个相同的直接跳过
                if (j < nums.length - 2) {
                    while (nums[j] == nums[++j] && j < nums.length - 2) ;
                } else {
                    j++;
                }
            }
            //下个数有与这个相同的直接跳过
            if (i < nums.length - 2) {
                while (nums[i] == nums[++i] && i < nums.length - 2) ;
            } else {
                i++;
            }
        }
        return res;
    }
}
