package com.ggz.entity;

public class TestEntity {
    public TestEntity(String id,String name,String sex){
        this.id = Integer.valueOf(id);
        this.name = name;
        this.sex = sex.equals("male")?true:false;
    }

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
    /**
     * 是否是🚹（男）的
     */
    boolean sex;
}
