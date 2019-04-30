package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Jim
 * @date 2019/3/15
 */
public class IsPalindrome {

    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        List<Integer> ints = new ArrayList<>();
        while (x != 0) {
            ints.add(x % 10);
            x /= 10;
        }
        ArrayList<Integer> integers = new ArrayList<>(ints);
        Collections.reverse(ints);
        for (int i = 0; i < integers.size(); i++) {
            if (!ints.get(i).equals(integers.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testisPalindrome() {
        assertTrue(new IsPalindrome().isPalindrome(121));
        assertFalse(new IsPalindrome().isPalindrome(10));
    }
}
