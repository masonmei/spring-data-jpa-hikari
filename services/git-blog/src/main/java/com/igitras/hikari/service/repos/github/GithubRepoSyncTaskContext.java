package com.igitras.hikari.service.repos.github;

import com.igitras.hikari.service.repos.DefaultRepoSyncTaskContext;

import java.io.File;

/**
 * Github repository synchronise task context.
 *
 * @author mason
 */
public class GithubRepoSyncTaskContext extends DefaultRepoSyncTaskContext {
    private static final String REPO_CONTROL = ".git";

    @Override
    public File getRepositoryControl() {
        return new File(getRepositoryRoot(), REPO_CONTROL);
    }
}
