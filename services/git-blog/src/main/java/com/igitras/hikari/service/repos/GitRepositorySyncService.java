package com.igitras.hikari.service.repos;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Git Repository Sync Service.
 *
 * @author mason
 */
@Component
public class GitRepositorySyncService {
    private static final Logger LOG = LoggerFactory.getLogger(GitRepositorySyncService.class);
    private static final long DEFAULT_INTERVAL = 10 * 60 * 1000;

    @Autowired
    private ContextLifecycleScheduledTaskRegistrar taskRegistrar;

    @Autowired
    private TaskScheduler taskScheduler;

    private final Map<String, GitRepositorySyncTaskContext> taskContextRegistry = new ConcurrentHashMap<>();

    /**
     * Add Sync Task.
     *
     * @param context task context
     */
    public synchronized void addTask(GitRepositorySyncTaskContext context) {
        String remoteUrl = context.getRemoteUrl();
        if (taskContextRegistry.containsKey(remoteUrl)) {
            LOG.debug("Repository: {} has already been cloned.", remoteUrl);
            return;
        }

        GitRepositorySyncTask task = new GitRepositorySyncTask(context);
        long interval = context.getRefreshInterval() == null ? DEFAULT_INTERVAL : context.getRefreshInterval();
        taskRegistrar.scheduleFixedDelayTask(new IntervalTask(task, interval));
    }

    /**
     * Remove sync Task.
     *
     * @param context task context
     */
    public synchronized void removeTask(GitRepositorySyncTaskContext context) {
        String remoteUrl = context.getRemoteUrl();
        if (!taskContextRegistry.containsKey(remoteUrl)) {
            LOG.debug("Repository: {} not scheduled for sync.", remoteUrl);
            return;
        }

        taskRegistrar.getFixedRateTaskList().stream().filter(intervalTask -> {
            Runnable runnable = intervalTask.getRunnable();
            if (runnable instanceof GitRepositorySyncTask) {
                GitRepositorySyncTask task = (GitRepositorySyncTask) runnable;
                return task.getContext().equals(context);
            }
            return false;
        }).forEach(intervalTask -> {
            Runnable runnable = intervalTask.getRunnable();
            GitRepositorySyncTask task = (GitRepositorySyncTask) runnable;
            task.getContext().setStopped(true);
        });
    }

    /**
     * Get a copy or the task registry.
     *
     * @return tasks registry copy
     */
    public Map<String, GitRepositorySyncTaskContext> getTaskContextRegistry() {
        return Maps.newHashMap(taskContextRegistry);
    }
}
