package com.jim.java8.leecode;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Jim
 * @date 2019/3/15
 */
public class IntReverse {

    public int reverse(int x) {
        String s = String.valueOf(Math.abs(x));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            stringBuilder.append(s.charAt(i));
        }
        Integer integer = Integer.valueOf(stringBuilder.toString());
        if (x < 0) {
            return -integer;
        } else {
            return integer;
        }

    }

    public int reverse2(int x) {

        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;

            if (rev > Integer.MAX_VALUE / 10 || rev == Integer.MAX_VALUE / 10 && pop > 7) {
                return 0;
            }
            if (rev < Integer.MIN_VALUE / 10 || rev == Integer.MIN_VALUE && pop < -8) {
                return 0;
            }

            rev = rev * 10 + pop;
        }
        return rev;

    }

    @Test
    public void testReverse() {
        assertEquals(new IntReverse().reverse(-123), -321);
    }
}
