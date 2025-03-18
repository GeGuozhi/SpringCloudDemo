package com.ggz.pojo;

public enum Flag {
    S("success","0","1"),
    Failure("failure","1","1");

    Flag(String a, String b,String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private final String a;
    private final String b;
    private final String c;

    public String getA() {
        return this.a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }
}
