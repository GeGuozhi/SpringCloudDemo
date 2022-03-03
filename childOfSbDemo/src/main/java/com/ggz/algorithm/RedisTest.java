package com.ggz.algorithm;

import com.ggz.childMavenProject.Student;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ggz on 2022/2/28
 */
public class RedisTest {

    static Lock lock = new ReentrantLock();
    static ExecutorService executorService = Executors.newFixedThreadPool(100);
    static int value = 0;

    public static void main(String[] args) {
//        multiThreadPoolExec();
//        Set<Student> set = new HashSet<>();
//        Student student = new Student(1);
//        set.add(student);
//        System.out.println(set.iterator().next().getAge());
//        student.setAge(2);
//        set.add(student);
//        System.out.println(set.iterator().next().getAge());

        multiThreadPoolExec();

    }

    /**
     * 互斥锁
     */
    public static String getData(String key) throws InterruptedException {
        String data = getDataFromRedis(key);
        if (data == null) {
            if (lock.tryLock()) {
                data = getDataFromDatabase(key);
                if (data != null) {
                    setDataToCache();
                }
                System.out.println(value++);
                lock.unlock();
            } else {
                Thread.sleep(100);
                data = getData(key);
            }
        }
        return data;
    }

    /**
     * 多线程运行 模拟redis获取数据的过程
     */
    private static void multiThreadPoolExec(){
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                getData("key");
            }
        });
        for (int i = 0; i < 1000; i++) {
            Thread thread1 = new Thread(thread, "thread_" + i);
            executorService.execute(thread1);
        }

        executorService.shutdown();
    }

    private static void setDataToCache() {
    }

    private static String getDataFromDatabase(String key) {
        System.out.println("dataOfDatabase");
        return "dataOfDatabase";
    }

    private static String getDataFromRedis(String key) {
        return null;
    }
}