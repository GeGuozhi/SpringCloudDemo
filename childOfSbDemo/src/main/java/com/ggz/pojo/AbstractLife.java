package com.ggz.pojo;

/**
 * 生命类
 *
 * @author ggz on 2022/7/13
 */
public abstract class AbstractLife {
    public abstract void get();
    public abstract void get1();

    public void get2() {
        System.out.println("father get2()");
    }

    public static void get3() {
        System.out.println("father get3()");
    }
}