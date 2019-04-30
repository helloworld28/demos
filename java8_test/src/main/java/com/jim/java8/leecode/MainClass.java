package com.jim.java8.leecode;/* -----------------------------------
 *  WARNING:
 * -----------------------------------
 *  Your code may fail to compile
 *  because it contains public class
 *  declarations.
 *  To fix this, please remove the
 *  "public" keyword from your class
 *  declarations.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */



public class MainClass {
    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }


    public static ListNode stringToListNode(String input) {
        // Generate array from the input
        int[] nodeValues = stringToIntegerArray(input);

        // Now convert that list into linked list
        ListNode dummyRoot = new ListNode(0);
        ListNode ptr = dummyRoot;
        for(int item : nodeValues) {
            ptr.next = new ListNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }

    public static String listNodeToString(ListNode node) {
        if (node == null) {
            return "[]";
        }

        String result = "";
        while (node != null) {
            result += Integer.toString(node.val) + ", ";
            node = node.next;
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            ListNode l1 = stringToListNode(line);
            line = in.readLine();
            ListNode l2 = stringToListNode(line);

            ListNode ret = new Solution().addTwoNumbers(l1, l2);

            String out = listNodeToString(ret);

            System.out.print(out);
        }
    }



    static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode firstNode = null;
            ListNode currentNode = null;
            int next = 0;
            ListNode l1NextNode=l1;
            ListNode l2NextNode = l2;
            do{

                int l1Val = 0;
                if(l1NextNode!=null){
                    l1Val = l1NextNode.val;
                    l1NextNode = l1NextNode.next;
                }else{
                    l1NextNode = null;
                }

                int l2Val = 0;
                if(l2NextNode != null){
                    l2Val = l2NextNode.val;
                    l2NextNode = l2NextNode.next;
                }else{
                    l2NextNode=null;
                }

                int result = l1Val + l2Val + next;


                next = result/10;
                int val = result%10;
                if(firstNode == null){
                    firstNode = new ListNode(val);
                    currentNode = firstNode;
                }else{
                    currentNode.next = new ListNode(val);
                    currentNode = currentNode.next;
                }





            }while(l1NextNode!=null || l2NextNode!=null);
            return firstNode;
        }
    }
}
