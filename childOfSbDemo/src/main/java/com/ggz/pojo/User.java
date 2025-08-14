package com.ggz.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 5892515816553685390L;
    public String id;
    public String passWord;
    public String userName;
    public User user;
    public Integer age;

    public User() {

    }

    public User(String s, String s1, String ggz) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User(String userName, int age){
        User user = new User();
        user.userName = userName;
        user.age = age;
        this.user = user;
    }
}