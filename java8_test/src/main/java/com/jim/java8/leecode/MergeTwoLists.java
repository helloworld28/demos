package com.jim.java8.leecode;

import org.testng.annotations.Test;

/**
 * @author Jim
 * @date 2019/8/1
 */
public class MergeTwoLists {

    @Test
    public void test() {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(4);

        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(4);


        ListNode listNode1 = mergeTwoLists(head, head2);
        System.out.println(listNode1);
        while (listNode1.next != null) {
            System.out.print(" -> " + listNode1.val);
            listNode1 = listNode1.next;
        }
        System.out.print(" -> " + listNode1.val);

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
