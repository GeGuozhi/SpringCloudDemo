package com.ggz.algorithm;

import SwordToOffer.LeetCode;
import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程问题
 *
 * @author ggz on 2022/10/11
 */
public class MultiThread {

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
//        Thread foo  = new Thread(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                foo();
//            }
//        });
//
//        Thread bar  = new Thread(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                bar();
//            }
//        });
//
//
//        bar.start();
//        Thread.sleep(1000);
//        foo.start();

        test_printABC_CBA();
    }

    static int flag1;
    static int flag2;

    static class printABC_1 implements Runnable {
        String content;

        public printABC_1(String content) {
            this.content = content;
        }

        @Override
        public void run() {
            if ("A".equals(content)) {
                System.out.println(content);
                flag1++;
            } else if ("B".equals(content)) {
                while (flag1 != 1) {
                    System.out.print("");
                }
                System.out.println(content);
                flag2++;
            } else if ("C".equals(content)) {
                while (flag2 != 1) {
                    System.out.print("");
                }
                System.out.println(content);
            }


        }
    }

    /**
     * 实例化三个线程，启动顺序为CBA，输出为ABC，用synchronized实现
     */

    public static void test_printABC_CBA() throws InterruptedException {
        Thread a = new Thread(new printABC_Synchronized("A"));
        Thread b = new Thread(new printABC_Synchronized("B"));
        Thread c = new Thread(new printABC_Synchronized("C"));
        c.start();
        b.start();
        a.start();
    }

    static String flag_111 = "A";
    static Object lock = new Object();

    static class printABC_Synchronized implements Runnable {
        String content;

        public printABC_Synchronized(String content) {
            this.content = content;
        }

        @Override
        public void run() {
            for (int i = 0; i < 30; i++) {
                synchronized (lock) {
                    if ("A".equals(content)) {
                        while (!"A".equals(flag_111)) {
                            try {
                                System.out.println("A进入，失败等待中");
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(content);
                        flag_111 = "B";
                        lock.notifyAll();
                    } else if ("B".equals(content)) {
                        while (!"B".equals(flag_111)) {
                            try {
                                System.out.println("B进入，失败等待中");
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(content);
                        flag_111 = "C";
                        lock.notifyAll();
                    } else if ("C".equals(content)) {
                        while (!"C".equals(flag_111)) {
                            try {
                                System.out.println("C进入，失败等待中");
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(content);
                        flag_111 = "A";
                        lock.notifyAll();
                    }
                }
            }
        }
    }

    /**
     * 实例化三个线程，启动顺序为CBA，输出为ABC
     */
    static AtomicInteger flag11 = new AtomicInteger(0);
    static AtomicInteger flag22 = new AtomicInteger(0);

    static class printABC_Atom implements Runnable {
        String content;

        public printABC_Atom(String content) {
            this.content = content;
        }

        @Override
        public void run() {
            if ("A".equals(content)) {
                System.out.println(content);
                flag11.incrementAndGet();
            } else if ("B".equals(content)) {
                while (flag11.get() != 1) {
                }
                System.out.println(content);
                flag22.incrementAndGet();
            } else if ("C".equals(content)) {
                while (flag22.get() != 1) {
                }
                System.out.println(content);
            }
        }
    }


    /**
     * 启动三个线程，顺序打印ABC十次，启动顺序不定
     */
    private static int flag = 3;
    private static Object printLock = new Object();

    public static void printA() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            synchronized (printLock) {
                while (flag != 3) {
                    printLock.wait();
                }
                flag = 1;
                System.out.print("A");
                printLock.notifyAll();
            }
        }
    }

    public static void printB() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            synchronized (printLock) {
                while (flag != 1) {
                    printLock.wait();
                }
                flag = 2;
                System.out.print("B");
                printLock.notifyAll();
            }
        }
    }

    public static void printC() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            synchronized (printLock) {
                while (flag != 2) {
                    printLock.wait();
                }
                flag = 3;
                System.out.print("C");
                printLock.notifyAll();
            }
        }
    }

    private static int n = 5;
    private static BlockingQueue<Integer> bar = new LinkedBlockingQueue<>(1);
    private static BlockingQueue<Integer> foo = new LinkedBlockingQueue<>(1);

    public static void foo() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.put(i);
            System.out.print("foo");
            bar.put(i);
        }
    }

    public static void bar() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.take();
            System.out.println("bar");
            foo.take();
        }
    }



}