package com.ggz.childMavenProject;


import org.thymeleaf.util.Validate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EventSource {
    private List<MonitorListener> listenerList = new LinkedList<MonitorListener>();

    public void addListener(MonitorListener monitorListener){
        listenerList.add(monitorListener);
    }

    public void removeListener(MonitorListener monitorListener){
        Validate.isTrue(listenerList.indexOf(monitorListener)>-1,"未订阅的监听者");
        listenerList.remove(monitorListener);
    }

    public void notifyAllListener(PrintEvent printEvent){
        Iterator<MonitorListener> iterator = listenerList.iterator();

        /**
         * 继承了MonitorListener的监听者们被循环通知。while添加中可以增加，被通知的类型。
         */
        while(iterator.hasNext()){
            iterator.next().handleEvent(printEvent);
        }
    }
}
