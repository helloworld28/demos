package com.jim.java8.leecode;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Jim
 * @date 2019/3/15
 */
public class Atoi {

    public int myAtoi(String str) {
        String numStr = "+-0123456789";
        boolean foundNumber = false;
        boolean foundFirst = false;
        int rec = 0;
        int flag = 1;

        for (char c : str.toCharArray()) {

            if(c ==' ' && foundNumber){
                return rec;
            }else if(c==' '){
                continue;
            }
            if (numStr.indexOf(c) > -1) {
                if (!foundNumber) {
                    foundNumber = true;
                }

                if (c == '-' && !foundFirst) {
                    flag = -1;
                    foundFirst = true;
                    continue;

                }
                if (c == '+' && !foundFirst) {
                    flag = 1;
                    foundFirst = true;
                    continue;
                }
                if((c == '-' ||c=='+') && foundFirst){
                    return 0;
                }

                Integer integer = Integer.valueOf("" + c);
                if (rec * flag > Integer.MAX_VALUE / 10 || rec * flag == Integer.MAX_VALUE / 10 && integer > 7) {
                    return Integer.MAX_VALUE;
                }

                if (rec * flag < Integer.MIN_VALUE / 10 || rec * flag == Integer.MIN_VALUE && integer > 8) {
                    return Integer.MIN_VALUE;
                }


                rec = rec * 10 + integer;

            } else {
                if (foundNumber) {
                    return rec * flag;
                } else {
                    return 0;
                }
            }
        }
        return rec * flag;
    }

    @Test
    public void testMyAtoi() {
//        assertEquals(new Atoi().myAtoi("4193 with words"), 4193);
//        assertEquals(new Atoi().myAtoi("4193 with words"), 4193);
//        assertEquals(new Atoi().myAtoi("words and 987"), 0);
//        assertEquals(new Atoi().myAtoi("   -42"), -42);
//        assertEquals(new Atoi().myAtoi("0-1"), -1);
//        assertEquals(Integer.MIN_VALUE, -2147483648);
//        assertEquals(new Atoi().myAtoi("-91283472332"), -2147483648);
//        assertEquals(new Atoi().myAtoi("+1"), 1);
//        assertEquals(new Atoi().myAtoi("+-2"), 0);
//        assertEquals(new Atoi().myAtoi("   +0 123"), 0);
        assertEquals(new Atoi().myAtoi("-2147483649"), -2147483648);
    }
}
