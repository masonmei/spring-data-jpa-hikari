package com.igitras.hikari.service.repos;

import static com.igitras.hikari.service.repos.RepoSyncTaskContextFactory.buildTask;

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
public class RepositorySyncService {
    private static final Logger LOG = LoggerFactory.getLogger(RepositorySyncService.class);
    private static final long DEFAULT_INTERVAL = 10 * 60 * 1000;

    @Autowired
    private ContextLifecycleScheduledTaskRegistrar taskRegistrar;

    @Autowired
    private TaskScheduler taskScheduler;

    private final Map<String, RepoSyncTask> taskRegistry = new ConcurrentHashMap<>();

    /**
     * Add Sync Task.
     *
     * @param context task context
     */
    public synchronized void addTask(RepoSyncTaskContext context) {
        RepoSyncTask repoSyncTask = buildTask(context);
        if (taskRegistry.containsKey(repoSyncTask.getIdentifier())) {
            LOG.debug("Repository: {} has already been cloned.", context.getRepository());
            return;
        }

        long interval = context.getRefreshInterval() == null ? DEFAULT_INTERVAL : context.getRefreshInterval();
        taskRegistrar.scheduleFixedRateTask(new IntervalTask(repoSyncTask, interval));
        taskRegistry.put(repoSyncTask.getIdentifier(), repoSyncTask);
    }

    /**
     * Remove sync Task.
     *
     * @param context task context
     */
    public synchronized void removeTask(RepoSyncTaskContext context) {
        RepoSyncTask repoSyncTask = buildTask(context);
        if (!taskRegistry.containsKey(repoSyncTask.getIdentifier())) {
            LOG.debug("Repository: {} not scheduled for sync.", context.getRepository());
            return;
        }

        taskRegistrar.destroy();
        taskRegistry.remove(repoSyncTask.getIdentifier());
        taskRegistrar.getFixedRateTaskList().clear();
        taskRegistry.values()
                .forEach(task -> taskRegistrar.addFixedRateTask(task, task.getTaskContext().getRefreshInterval()));
        taskRegistrar.afterPropertiesSet();
    }

    /**
     * Get a copy or the task registry.
     *
     * @return tasks registry copy
     */
    public Map<String, RepoSyncTask> getTaskRegistry() {
        return Maps.newHashMap(taskRegistry);
    }
}
