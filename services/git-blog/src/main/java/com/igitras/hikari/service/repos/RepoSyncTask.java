package com.igitras.hikari.service.repos;

/**
 * Repo synchronise task.
 *
 * @author mason
 */
public interface RepoSyncTask extends Runnable {

    /**
     * Get the task identifier.
     *
     * @return task identifier
     */
    String getIdentifier();

    /**
     * Get the task context.
     *
     * @return task context
     */
    RepoSyncTaskContext getTaskContext();
}
