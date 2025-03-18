package com.ggz.pojo;

/**
 * 继承生命类
 *
 * @author ggz on 2022/7/13
 */
public class People extends AbstractLife{
    @Override
    public void get() {
        System.out.println("people get()");
    }

    @Override
    public void get1() {
        System.out.println("people get1()");
    }

    @Override
    public void get2() {
        System.out.println("people get2()");
    }

    public static void get3() {
        System.out.println("people get3()");
    }
}