package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.List;

/**
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 *  
 * <p>
 * 示例:
 * <p>
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/swap-nodes-in-pairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/8/8
 */
public class SwapPairs {

    @Test
    public void test() {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        listNode.next.next.next.next.next = new ListNode(6);
        listNode.next.next.next.next.next.next = new ListNode(7);

        ListNode listNode1 = swapPairs(listNode);
        System.out.println(listNode1.val);
        System.out.println(listNode1.next.val);
        System.out.println(listNode1.next.next.val);
        System.out.println(listNode1.next.next.next.val);
        System.out.println(listNode1.next.next.next.next.val);
        System.out.println(listNode1.next.next.next.next.next.val);
        System.out.println(listNode1.next.next.next.next.next.next.val);

    }

    public ListNode swapPairs(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        int i = 0;
        ListNode next = head;
        ListNode firsNode = head.next;
        ListNode left = null;
        ListNode right;
        ListNode prePair = null;
        while (next != null) {
            i++;
            if (i % 2 == 1) {
                left = next;
                next = next.next;
            } else if (i % 2 == 0) {
                right = next;
                left.next = right.next;
                right.next = left;
                next = left.next;
                if (prePair != null) {
                    prePair.next = right;
                }
                prePair = left;

            }
        }
        return firsNode;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
