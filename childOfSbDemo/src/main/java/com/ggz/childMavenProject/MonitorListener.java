package com.ggz.childMavenProject;

import java.util.EventListener;

public interface  MonitorListener extends EventListener {
    public void handleEvent(PrintEvent event);
}
