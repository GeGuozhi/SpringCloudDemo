package com.ggz.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 5892515816553685390L;
    public String id;
    public String passWord;
    public String userName;
    public User user;

    public User(){

    }

    public User(String id, String passWord, String userName){
        User user = new User();
        user.id = id;
        user.passWord = passWord;
        user.userName = userName;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return user.id;
    }

    public User setId(String id) {
        user.id = id;
        return user;
    }

    public String getPassWord() {
        return user.passWord;
    }

    public User setPassWord(String passWord) {
        user.passWord = passWord;
        return user;
    }

    public String getUserName() {
        return user.userName;
    }

    public User setUserName(String userName) {
        user.userName = userName;
        return user;
    }
}