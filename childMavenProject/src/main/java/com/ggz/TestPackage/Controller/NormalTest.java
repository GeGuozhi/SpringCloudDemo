package com.ggz.TestPackage.Controller;

import com.ggz.TestPackage.entity.User;

import java.util.Date;
import java.util.Optional;

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
        Date date = new Date(System.currentTimeMillis());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date date2 = new Date(System.currentTimeMillis());

        System.out.println(date.getTime());
        System.out.println(date2.getTime());

        System.out.println(date2.before(date));
        System.out.println(date.before(date2));
    }
}
