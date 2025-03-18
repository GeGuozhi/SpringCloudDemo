package com.ggz.childMavenProject;


import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class Student implements Serializable,Comparable<Student> {
    private static final long serialVersionUID = -4625041092019074089L;
    //    private static final long serialVersionUID = -7379514099487493688L;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int age;
    private String name;
    private Student stu;

    public Student getStu() {
        return stu;
    }

    public void withStu(Student stu) {
        this.stu = stu;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStu(Student stu) {
        this.stu = stu;
    }

    public Student withName(String name) {
        this.name = name;
        return this.stu;
    }

    public int getAge() {
        return age;
    }

    public Student withAge(int age) {
        this.age = age;
        return this.stu;
    }

    public Student(int age){
        this.age = age;
    }

    public Student(Student student){
        this.age = student.getAge();
        this.name = student.getName();
    }

    public Student(){

    }

    public void testParent(){
        System.out.println("parent");
    }

    @Override
    public int compareTo(@NotNull Student o) {
        return this.age - o.age;
    }

    public static void staticMethod(){
        System.out.println("this is student's static methord");
    }

    protected void testProtect(){

    }
}
