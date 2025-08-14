package com.ggz.TestPackage.controller;

import com.ggz.TestPackage.model.Student;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

public class NormalTest {
    public static void main(String[] args) {
//        System.out.println("begging");
//        User user = null;
//        Optional<User> userOptional = Optional.ofNullable(user);
//        System.out.println(userOptional.map(User::getName).orElse("11"));
//        System.out.println(userOptional.map(User::getName).map(String::toUpperCase).orElse(null));
//
//        System.out.println(userOptional.map(User::getName).orElseGet(()->{
//            if(1>0){
//                return "11";
//            }else{
//                return "122";
//            }
//        }));
//
//        User user1 = new User();
//        user1.setName("ggz");
//        Optional<User> userOptional1 = Optional.ofNullable(user1);
//        System.out.println(userOptional1.map(User::getName).orElse(""));
//        Date date = new Date(System.currentTimeMillis());
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Date date2 = new Date(System.currentTimeMillis());
//
//        System.out.println(date.getTime());
//        System.out.println(date2.getTime());
//
//        System.out.println(date2.before(date));
//        System.out.println(date.before(date2));

//        System.out.println("abc".indexOf("a"));
//        System.out.println("abc".indexOf("d"));
//        // 示例1：检查一个对象是否为空
//        Student user = null;
////        Assert.assertNotNull(user, "实体类user为空");
//
//        // 示例2：检查对象的某个属性是否为空
//        Student user1 = new Student();
//        Assert.assertNotNull("用户名字为空",user1.getName());
//
//        // 示例3：检查一个字符串是否为空
//        String name = null;
//        String name1 = "ggz";
//        Assert.assertNotNull("字符为空",name);
//        Assert.assertNotNull("字符为空",name1);

//        Function<String, String> function = a -> a + " Jack!";
//        Function<String, String> function1 = a -> a + " Bob!";
//        String greet = function.andThen(function1).apply("Hello");
//        System.out.println(greet); // Hello Jack! Bob!
//        Student s = new Student();
//        s.setTime(LocalDateTime.now());
//        List<Student> studentList = new ArrayList<>();
//        studentList.add(s);
//
//        // 目标日期：2025年4月23日
//        LocalDate targetDate = LocalDate.of(2025, 4, 23);
//
//        // 过滤出 time 字段日期等于 targetDate 的学生
//        List<Student> filteredList = studentList.stream()
//                .filter(student -> {
//                    LocalDateTime time = student.getTime();
//                    return time != null && time.toLocalDate().equals(targetDate);
//                })
//                .collect(Collectors.toList());
//
//        // 输出结果
//        filteredList.forEach(System.out::println);

//    public int test()

        LocalDateTime local = LocalDateTime.now();

        System.out.println(local);
        System.out.println(local.toLocalDate());
        System.out.println(LocalDate.of(2025, 4, 23));
        System.out.println(LocalDateTime.now().equals(LocalDate.of(2025, 4, 23)));
    }
}
