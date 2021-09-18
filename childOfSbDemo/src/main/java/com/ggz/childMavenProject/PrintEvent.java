package com.ggz.childMavenProject;

import java.util.EventObject;

public class PrintEvent{
    protected transient Object  source;

    public PrintEvent(Object source) {
        if (source == null)
            throw new IllegalArgumentException("null source");

        this.source = source;
    }



    public void doEvent(){
        System.out.println("通知一个事件源：source:"+this.getSource());
    }

    public Object getSource() {
        return source;
    }
}
