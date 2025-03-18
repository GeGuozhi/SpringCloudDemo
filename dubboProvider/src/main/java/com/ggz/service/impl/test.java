package com.ggz.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author ggz on 2022/9/23
 */
public class test implements Callable,Runnable{
    @Override
    public Object call() throws Exception {
        Thread.sleep(1);
        return "1";
    }

    @Override
    public void run() {

    }

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    static ThreadLocal<Integer> number = new ThreadLocal<>();

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<String>(new test());
        Thread future = new Thread(futureTask);
        future.start();
        try {
            System.out.println("是否已取消?"+futureTask.isCancelled());
            System.out.println("是否已完成?"+futureTask.isDone());
            System.out.println(futureTask.get());
            System.out.println("是否已取消?"+futureTask.isCancelled());
            System.out.println("是否已完成?"+futureTask.isDone());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        number.set(0);



        for (int i = 0; i < 10; i++) {
            int a = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    number.set(a);
                    System.out.println(number.get());
                    System.out.println(Thread.currentThread().getName()+"成功");
                }
            });
            executorService.execute(thread);
        }

        System.gc();
    }

    public void mm(){
        final Date date = new Date();

        HashMap<String,String> map = new HashMap<>();

        for (int i = 0; i < 15; i++) {
            int a = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(map.get(""));
                }
            });
            executorService.execute(thread);
        }
    }


}
