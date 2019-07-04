package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/7/4
 */
public class ThreeSum {

    @Test
    public void test() {
        List<List<Integer>> lists = new ThreeSum().threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        assertEquals(Arrays.asList(-1, -1, 2).toString(), lists.get(1).toString());
        assertEquals(Arrays.asList(-1, 0, 1).toString(), lists.get(0).toString());
        lists = new ThreeSum().threeSum(new int[]{7, -1, 14, -12, -8, 7, 2, -15, 8, 8, -8, -14, -4, -5, 7, 9, 11, -4, -15, -6, 1, -14, 4, 3, 10, -5, 2, 1, 6, 11, 2, -2, -5, -7, -6, 2, -15, 11, -6, 8, -4, 2, 1, -1, 4, -6, -15, 1, 5, -15, 10, 14, 9, -8, -6, 4, -6, 11, 12, -15, 7, -1, -9, 9, -1, 0, -4, -1, -12, -2, 14, -9, 7, 0, -3, -4, 1, -2, 12, 14, -10, 0, 5, 14, -1, 14, 3, 8, 10, -8, 8, -5, -2, 6, -11, 12, 13, -7, -12, 8, 6, -13, 14, -2, -5, -11, 1, 3, -6});
        System.out.println(lists);


    }

    @Test
    public void test2() {
        List<List<Integer>> lists = new ThreeSum().threeSum2(new int[]{-1, 0, 1, 2, -1, -4});
        assertEquals(Arrays.asList(-1, -1, 2).toString(), lists.get(0).toString());
        assertEquals(Arrays.asList(-1, 0, 1).toString(), lists.get(1).toString());
        assertEquals(2, lists.size());
        lists = new ThreeSum().threeSum2(new int[]{7, -1, 14, -12, -8, 7, 2, -15, 8, 8, -8, -14, -4, -5, 7, 9, 11, -4, -15, -6, 1, -14, 4, 3, 10, -5, 2, 1, 6, 11, 2, -2, -5, -7, -6, 2, -15, 11, -6, 8, -4, 2, 1, -1, 4, -6, -15, 1, 5, -15, 10, 14, 9, -8, -6, 4, -6, 11, 12, -15, 7, -1, -9, 9, -1, 0, -4, -1, -12, -2, 14, -9, 7, 0, -3, -4, 1, -2, 12, 14, -10, 0, 5, 14, -1, 14, 3, 8, 10, -8, 8, -5, -2, 6, -11, 12, 13, -7, -12, 8, 6, -13, 14, -2, -5, -11, 1, 3, -6});
        System.out.println(lists);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length == 0) return Collections.emptyList();
        Arrays.sort(nums);
        List<List<Integer>> results = new ArrayList<>();

        int prei = nums[0];
        int prej = nums[1];
        int pren = nums[2];
        for (int i = 1; i < nums.length - 2; i++) {
            if (prei == nums[i]) continue;
            prei = nums[i];
            for (int j = i + 1; j < nums.length - 1; j++) {
                if (prej == nums[j]) continue;
                prej = nums[j];
                for (int n = j + 1; n < nums.length; n++) {
                    if (pren == nums[n]) continue;
                    pren = nums[n];
                    if (nums[i] + nums[j] + nums[n] == 0) {
                        results.add(Arrays.asList(nums[i], nums[j], nums[n]));
                    }
                }
            }
        }
        return results;
    }


    public List<List<Integer>> threeSum2(int[] nums) {
        if (nums.length == 0) return Collections.emptyList();
        Arrays.sort(nums);

        List<List<Integer>> results = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) continue;

            int l = i + 1;
            int r = nums.length - 1;

            if (nums[r] < 0) return results;
            do {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) {
                    results.add(Arrays.asList(nums[i], nums[l], nums[r]));
                }

                if (sum < 0) {
                    while (nums[l] == nums[++l] && l < r) ;
                } else if (sum > 0) {
                    while (nums[r] == nums[--r] && l < r) ;
                } else {
                    while (nums[l] == nums[++l] && l < r) ;
                    while (nums[r] == nums[--r] && l < r) ;
                    if (l >= r) {
                        break;
                    }
                }


            } while (l < r);

        }

        return results;
    }

}
