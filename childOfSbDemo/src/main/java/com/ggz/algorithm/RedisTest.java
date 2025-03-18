package com.ggz.algorithm;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.URLUtil;
import com.ggz.childMavenProject.Student;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;
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
        multiThreadPoolExec();
//        Set<Student> set = new HashSet<>();
//        Student student = new Student(1);
//        set.add(student);
//        System.out.println(set.iterator().next().getAge());
//        student.setAge(2);
//        set.add(student);
//        System.out.println(set.iterator().next().getAge());

        int port = 0;
        int defaultPort = 8001;
//        Future<Integer> future = ThreadUtil.execAsync(() -> {
//            int p = 0;
//            System.out.println("请于5秒钟内输入端口号, 推荐  8001 、 8002  或者  8003，超过5秒将默认使用 " + defaultPort);
//            return p;
//        });
//        try {
//            port = future.get(1, TimeUnit.SECONDS);
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            port = defaultPort;
//        }
//        if (!NetUtil.isUsableLocalPort(defaultPort)) {
//            System.err.printf("端口%d被占用了，无法启动%n", defaultPort);
//            System.exit(1);
//        }

//        URL url = URLUtil.url("http://localhost:8002/serviceNumberOne/updateUser");


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
    private static void multiThreadPoolExec() {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
//                getData("key");
                testTrans();
            }
        });
        for (int i = 0; i < 1000000; i++) {
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

    public static void testTrans(){
        try {
            // 设置请求的URL
            String urlStr = "http://localhost:8002/serviceNumberOne/updateUser";

            // 创建URL对象
            URL url = new URL(urlStr);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为GET
            connection.setRequestMethod("GET");

            // 发送请求并获取响应状态码
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 请求成功
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                System.out.println(response.toString());
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

            // 关闭连接
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}