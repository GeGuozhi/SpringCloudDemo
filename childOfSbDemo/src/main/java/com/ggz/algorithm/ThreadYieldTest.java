package com.ggz.algorithm;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadYieldTest {
    private static final Object obj = new Object();
    private static AtomicInteger atomicInteger = new AtomicInteger();

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

    }

    public static void counter(){
        atomicInteger.getAndIncrement();
    }

    static class MyThread1 implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            synchronized (obj) {
                System.out.println("thread1 start");
                obj.wait();
                obj.notifyAll();
                System.out.println("thread1 end");
            }
        }
    }

    static class MyThread2 implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            synchronized (obj) {
                System.out.println("thread2 start");
                System.out.println("thread2 end");
                obj.notifyAll();
            }
        }
    }

    static class MyThread3 implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            System.out.println("thread3 start");
            Thread.yield();
            System.out.println("thread3 end");
        }
    }
}