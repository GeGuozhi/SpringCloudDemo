package com.ggz.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author ggz on 2025/10/31
 */
@Component
@Slf4j
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final GitProperties git;

    public ApplicationStartupListener(GitProperties git) {
        this.git = git;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("=== Application Startup Information ===");
        log.info("Application Name: mainApp");
        log.info("Server Port: 8004");
        log.info("gitCommitId: {}", git.getCommitId());
        log.info("gitCommitTime: {}", git.getCommitTime());
        log.info("gitBranch: {}", git.getBranch());
        log.info("=== Application Startup Completed ===");
    }
}