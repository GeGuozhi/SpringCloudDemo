package com.ggz.entity;

public enum Flag {
    S("success","0","1"),
    F("failure","1","1");

    Flag(String a, String b,String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private String a;
    private String b;
    private String c;

    public String getA() {
        return this.a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}
