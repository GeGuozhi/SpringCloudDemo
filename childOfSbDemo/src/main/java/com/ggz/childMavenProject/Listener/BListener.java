package com.ggz.childMavenProject.Listener;

import com.ggz.childMavenProject.MonitorListener;
import com.ggz.childMavenProject.PrintEvent;

public class BListener implements MonitorListener {
    @Override
    public void handleEvent(PrintEvent event) {
        if(event.getSource().equals("openWindows")){
            event.doEvent();
            System.out.println("B收到了,并且执行了");
        }else{
            System.out.println("B收到了，但是没执行");
        }
    }
}
