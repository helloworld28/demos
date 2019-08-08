package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @author Jim
 * @date 2019/8/7
 */
public class MergeKLists {

    @Test
    public void test(){
        ListNode[] lists = new ListNode[]{new ListNode(1),new ListNode(2), new ListNode(3) };

        mergeKLists(lists);
    }


    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length > 2) {
            int size = (int) Math.ceil(lists.length / (2.0));
            ListNode[] nextList = new ListNode[size];
            for (int i = 0; i < lists.length / 2; i += 2) {
                nextList[i] = mergeTwoLists(lists[i * 2], lists[i * 2 + 1]);
            }
            if (lists.length / 2 != size) {
                nextList[size-1] = lists[lists.length - 1];
            }
            return mergeKLists(nextList);


        } else if (lists.length == 2) {
            return mergeTwoLists(lists[1], lists[0]);
        } else {
            return null;
        }
    }


    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head;
        ListNode inexNode = l1;
        ListNode inexNode2 = l2;

        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }


        if (l1.val < l2.val) {
            head = l1;
            inexNode = l1.next;
        } else {
            head = l2;
            inexNode2 = l2.next;

        }

        ListNode currentNode = head;

        while (inexNode != null && inexNode2 != null) {
            if (inexNode.val < inexNode2.val) {
                currentNode.next = inexNode;
                currentNode = inexNode;
                inexNode = inexNode.next;
            } else {
                currentNode.next = inexNode2;
                currentNode = inexNode2;
                inexNode2 = inexNode2.next;
            }
        }

        if (inexNode != null && inexNode2 == null) {
            currentNode.next = inexNode;
        } else if (inexNode == null && inexNode2 != null) {
            currentNode.next = inexNode2;
        }

        return head;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
