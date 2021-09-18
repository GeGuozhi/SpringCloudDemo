package com.ggz.TestPackage.Controller;

import com.ggz.TestPackage.entity.User;

import java.util.Optional;

public class NormalTest {
    public static void main(String[] args) {
        System.out.println("begging");
        User user = null;
        Optional<User> userOptional = Optional.ofNullable(user);
        System.out.println(userOptional.map(User::getName).orElse(null));
        System.out.println(userOptional.map(User::getName).map(String::toUpperCase).orElse(null));

        User user1 = new User();
        user1.setName("ggz");
        Optional<User> userOptional1 = Optional.ofNullable(user1);
        System.out.println(userOptional1.map(User::getName).orElse(""));
    }
}
