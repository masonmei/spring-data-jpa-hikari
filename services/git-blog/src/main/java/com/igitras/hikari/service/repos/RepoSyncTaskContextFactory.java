package com.igitras.hikari.service.repos;

import com.igitras.hikari.domain.SyncRepo;
import com.igitras.hikari.service.repos.github.GithubRepoSyncTask;
import com.igitras.hikari.service.repos.github.GithubRepoSyncTaskContext;

import java.io.File;

/**
 * Repository synchronise task context factory.
 *
 * @author mason
 */
public abstract class RepoSyncTaskContextFactory {

    /**
     * Build a repository synchronise task context with given repoEntity under base folder.
     *
     * @param baseFolder base folder
     * @param repoEntity repo entity
     * @return sync task context
     */
    public static RepoSyncTaskContext buildContext(File baseFolder, SyncRepo repoEntity) {
        DefaultRepoSyncTaskContext context;
        switch (repoEntity.getRepositoryType()) {
            case GITHUB_PUBLIC:
            case GIT_PUBLIC:
                context = new GithubRepoSyncTaskContext();
                break;
            case GIT_PRIVATE:
            case GITHUB_PRIVATE:
            case SVN_PRIVATE:
            case SVN_PUBLIC:
            default:
                throw new UnsupportedOperationException("Unsupported type operation.");
        }
        context.setRepositoryType(repoEntity.getRepositoryType());
        context.setRepository(repoEntity.getRepository())
                .setRelativePath(repoEntity.getRelativePath())
                .setRefreshInterval(repoEntity.getRefreshInterval())
                .setBaseFolder(baseFolder);
        return context;
    }

    /**
     * Build the synchronise task with given context.
     *
     * @param context task context
     * @return task
     */
    public static RepoSyncTask buildTask(RepoSyncTaskContext context) {
        if (context instanceof GithubRepoSyncTaskContext) {
            return new GithubRepoSyncTask((GithubRepoSyncTaskContext) context);
        }
        throw new UnsupportedOperationException("Unsupported type operation.");
    }
}
