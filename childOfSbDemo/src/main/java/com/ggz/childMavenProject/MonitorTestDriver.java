package com.ggz.childMavenProject;

import SwordToOffer.ListNode;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MonitorTestDriver {

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException, ExecutionException, CloneNotSupportedException, IOException, ClassNotFoundException, ParseException {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        ListNode f = new ListNode(6);
        ListNode g = new ListNode(7);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        f.next = g;

        ListNode head = headInsert(a);

        ListNode tail = tailInsert(a);

        System.out.println("a");

    }

    //头插法 a->b->c
    // 0->a->b->c
    public static ListNode headInsert(ListNode head) {
        ListNode newNode = new ListNode(0);
        ListNode temp = head;
        ListNode dummy = newNode;
        while (temp != null) {
            dummy.next = temp;
            dummy = temp;
            temp = temp.next;
        }
        return newNode.next;
    }


    //尾插法 a->b->c->d
    //后继存起来，下一轮继续用
    public static ListNode tailInsert(ListNode head) {
        ListNode temp = head;
        ListNode dummy = null;
        ListNode next = null;
        while (temp != null) {
            next = temp.next;
            temp.next = null;
            temp.next = dummy;
            dummy = temp;
            temp = next;
        }
        return dummy;
    }

    /**
     * 中心扩散法求最大回文数
     * abbaccc     len = 7
     * abba          len = 4
     */
    public static String palindrome(String s) {
        String maxString = s.substring(0, 1);
        int s_len = s.length();
        for (int i = 1; i < s_len; i++) {
            int left = i;
            int right = i;
            while (right != s_len - 1 && left != 0) {
                if (!s.substring(left - 1, left).equals(s.substring(right + 1, right + 2))) {
                    break;
                } else {
                    if (s.substring(left - 1, right + 2).length() > maxString.length()) {
                        maxString = s.substring(left - 1, right + 2);
                    }
                    right++;
                    left--;
                }
            }
        }

        //abba ccc
        for (int i = 1; i < s_len; i++) {
            int left = i;
            int right = i;
            while (right < s_len - 1 && left >= 0) {
                if (!s.substring(left, left + 1).equals(s.substring(right + 1, right + 2))) {
                    break;
                } else {
                    if (s.substring(left, right + 2).length() > maxString.length()) {
                        maxString = s.substring(left, right + 2);
                    }
                    right++;
                    left--;
                }
            }
        }
        return maxString;
    }

    public void printAB() {
        Object lock = new Object();
        Boolean flag = true;
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i = i + 2) {
                    synchronized (lock) {
                        if (!flag) {
                            try {
                                lock.wait();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        lock.notify();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i = i + 2) {
                    synchronized (lock) {
                        if (flag) {
                            try {
                                lock.wait();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        lock.notify();
                    }
                }
            }
        });

        thread1.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
    }
}
