package com.jim.java8.leecode;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * <p>
 * 示例：
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * <p>
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 * <p>
 * 给定的 n 保证是有效的。
 * <p>
 * 进阶：
 * <p>
 * 你能尝试使用一趟扫描实现吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jim
 * @date 2019/7/31
 */
public class RemoveNthFromEnd {

    @Test
    public void test() {
        ListNode head = new ListNode(1);
        ListNode listNode = head;
        for (int i = 2; i < 6; i++) {
            listNode.next = new ListNode(i);
            listNode = listNode.next;
        }

        ListNode listNode1 = removeNthFromEnd2(head, 5);
        System.out.println(listNode1);
        while (listNode1.next != null) {
            System.out.print(" -> " + listNode1.val);
            listNode1 = listNode1.next;
        }
        System.out.print(" -> " + listNode1.val);


        ListNode listNode2 = removeNthFromEnd2(new ListNode(1), 1);
        assertTrue(listNode2 == null);


    }


    /**
     * 两次扫描
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int lenght = 1;
        ListNode nextNode = head;
        while (nextNode.next != null) {
            nextNode = nextNode.next;
            ++lenght;
        }

        nextNode = head;
        for (int i = 0; i < lenght - n - 1; i++) {
            nextNode = nextNode.next;
        }
        if (n == lenght) {
            head = head.next;
        } else {
            nextNode.next = nextNode.next.next;
        }

        return head;

    }


    /**
     * 利用双指针
     * 保证头尾的间隔为n就行
     * 一次扫描
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {

        ListNode startNode = head;
        ListNode endNode = head;
        for (int i = 0; i < n; i++) {
            endNode = endNode.next;
        }

        if (endNode != null) {
            while (endNode.next != null) {
                startNode = startNode.next;
                endNode = endNode.next;
            }
            startNode.next = startNode.next.next;
            return head;
        } else {
            return startNode.next;
        }


    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }


    }
}
