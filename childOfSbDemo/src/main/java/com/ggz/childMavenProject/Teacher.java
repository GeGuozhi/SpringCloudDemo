package com.ggz.childMavenProject;


import com.ggz.algorithm.Algorithm;

import java.io.Serializable;
import java.util.List;

public class Teacher implements Cloneable,Serializable {
    private static final long serialVersionUID = -1661316144969537346L;

    int age;
    String name;


    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    List<Student> students;

    public Teacher(){
        System.out.println("调用了构造函数");
    }

    public Teacher(Teacher teacher){
        System.out.println("调用了带参构造函数");
        this.students = teacher.getStudents();
        this.age = teacher.getAge();
        this.name = teacher.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
