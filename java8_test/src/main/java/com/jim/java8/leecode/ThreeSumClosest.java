package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

/**
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 * <p>
 * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
 * <p>
 * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum-closest
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/7/5
 */
public class ThreeSumClosest {

    @Test
    public void test() {
//        assertEquals(threeSumClosest(new int[]{-1, 2, 1, -4}, 1), 2);
//        assertEquals(threeSumClosest(new int[]{0,1,2}, 0), 3);
//        assertEquals(threeSumClosest(new int[]{0,2,1,-3}, 1), 0);
        assertEquals(threeSumClosest(new int[]{1, 1, -1, -1, 3}, -1), -1);
    }


    /**
     * 使用双指针
     * 其实就是求三个数之和
     * 如果发现三数之和有比目标的值小的就更新
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int ans = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) continue;
            int l = i + 1;
            int r = nums.length - 1;
            while (l < r) {
                int temp = nums[l] + nums[i] + nums[r];
                if (Math.abs(temp - target) < Math.abs(ans - target)) {
                    ans = temp;
                }

                if (temp > target) {
                    r--;
                } else if (temp < target) {
                    l++;
                } else {
                    return ans;
                }
            }

        }
        return ans;
    }
}
