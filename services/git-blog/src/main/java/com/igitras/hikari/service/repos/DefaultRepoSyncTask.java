package com.igitras.hikari.service.repos;

import static com.igitras.hikari.common.RepositoryStatus.INITIALIZED;
import static com.igitras.hikari.common.RepositoryStatus.TERMINATED;

import com.igitras.hikari.common.RepositoryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Git Repository Sync Task.
 *
 * @author mason
 */
public abstract class DefaultRepoSyncTask implements RepoSyncTask {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultRepoSyncTask.class);

    private final RepoSyncTaskContext context;
    private RepositoryStatus status = INITIALIZED;

    public DefaultRepoSyncTask(DefaultRepoSyncTaskContext context) {
        Assert.notNull(context, "SyncTaskContext must not be null.");
        this.context = context;
    }

    @Override
    public String getIdentifier() {
        return getTaskContext().getRepository();
    }

    @Override
    public RepoSyncTaskContext getTaskContext() {
        return context;
    }

    @Override
    public void run() {
        if (status == TERMINATED) {
            return;
        }

        if (context.getRepositoryControl().exists()) {
            pullRepository();
        } else {
            cloneRepository();
        }
    }



    public abstract void pullRepository();

    public abstract void cloneRepository();
}
