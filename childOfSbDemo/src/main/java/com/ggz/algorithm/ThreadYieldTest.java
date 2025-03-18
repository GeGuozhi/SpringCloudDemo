package com.ggz.algorithm;

import com.ggz.pojo.AbstractLife;
import com.ggz.pojo.People;
import com.ggz.pojo.Person;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadYieldTest {

    private static final Object obj = new Object();
    private static AtomicInteger atomicInteger = new AtomicInteger();

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
//        counter();

//        AbstractLife abstractLife = new People();
//        abstractLife.get2();

//        Date date = new Date();

//        serialize();
        deserialize("D:\\gitProject\\springcloud\\childOfSbDemo\\src\\main\\java\\com\\ggz\\algorithm\\1.txt");

    }

    @Test
    public static void serialize(){
        File file = new File("D:\\gitProject\\springcloud\\childOfSbDemo\\src\\main\\java\\com\\ggz\\algorithm\\1.txt");
        try {
            Person person = new Person("ggz","28","gym");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(person);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserialize(String path){
        Person person = new Person();
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(path));
            person = (Person) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println(person.getHobby());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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