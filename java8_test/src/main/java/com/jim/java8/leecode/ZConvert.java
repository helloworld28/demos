package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Jim
 * @date 2019/3/14
 */
public class ZConvert {

    public String convert(String s, int numRows) {
        int min = Math.min(s.length(), numRows);
        List<StringBuilder> stringBuilders = new ArrayList<StringBuilder>(min);

        for (int i = 0; i < min; i++) {
            stringBuilders.add(new StringBuilder());
        }

        boolean isGoDown = true;
        int currntRows = 0;
        for (char c : s.toCharArray()) {

            if (currntRows == numRows) {
                isGoDown = false;
                currntRows -= 2;
                currntRows = currntRows < 0 ? 0 : currntRows;
            }
            if (currntRows == 0) {
                isGoDown = true;
            }

            stringBuilders.get(currntRows).append(c);
            currntRows += isGoDown ? 1 : -1;

        }

        final StringBuilder result = new StringBuilder();

        stringBuilders.forEach(str -> result.append(str.toString()));
        return result.toString();
    }

    @Test
    public void test() {
        assertEquals(convert("LEETCODEISHIRING", 3), "LCIRETOESIIGEDHN");
        assertEquals(convert("LEETCODEISHIRING", 4), "LDREOEIIECIHNTSG");
        assertEquals(convert("AB", 1), "AB");
    }
}
