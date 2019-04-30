package com.jim.java8.leecode;

/**
 * @author Jim
 * @date 2019/3/7
 */
public class FindMedianSortedArrays {

    private double findMedianSortedArrays(int[] A, int[] B) {

        int m = A.length;
        int n = B.length;

        if (m > n) {
            int temp = n;
            n = m;
            m = temp;
            int[] tempInts = A;
            B = A;
            A = tempInts;
        }

        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin < iMax) {

            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && A[i] < B[j - 1]) {
                iMin = i + 1;
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1;
            } else {
                if ((m + n) % 2 == 1) {
                    return Math.min(A[i], B[j]);
                } else {
                    return (A[i] + B[j]) / 2d;
                }
            }
        }
        return 0.0;
    }
}
