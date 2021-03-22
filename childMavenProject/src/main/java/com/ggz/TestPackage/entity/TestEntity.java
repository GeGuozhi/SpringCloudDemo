package com.ggz.TestPackage.entity;

import jdk.nashorn.internal.objects.annotations.Setter;

public class TestEntity {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    int id;
    String name;
    boolean sex;
}
