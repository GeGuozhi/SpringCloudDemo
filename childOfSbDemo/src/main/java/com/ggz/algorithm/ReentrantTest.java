package com.ggz.algorithm;


import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantTest {
    static Lock lock = new ReentrantLock();
    static int value = 0;
    static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                get();
//            }
//        });
//        for (int i = 0; i < 10000; i++) {
//            Thread thread1 = new Thread(thread, "thread_" + i);
//            executorService.execute(thread1);
//        }
//
//        executorService.shutdown();
        System.out.println(LocalDateTime.now());

    }


    public static void get() {
        // 获取锁
        try {
            if (lock.tryLock()) {
                System.out.println((value++)+","+Thread.currentThread().getName());
            }
            lock.unlock();
        } catch (Exception e) {
            try {
                Thread.sleep(100);
                get();
            }catch (Exception ee){
                System.out.println("睡眠失败！");
            }
        }
    }
}
