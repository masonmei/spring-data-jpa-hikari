package com.igitras.hikari.service.repos.github;

import com.igitras.hikari.service.repos.DefaultRepoSyncTask;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Github Repository synchronise task.
 *
 * @author mason
 */
public class GithubRepoSyncTask extends DefaultRepoSyncTask {

    private static final Logger LOG = LoggerFactory.getLogger(GithubRepoSyncTask.class);

    public GithubRepoSyncTask(GithubRepoSyncTaskContext context) {
        super(context);
    }

    @Override
    public void pullRepository() {
        try {
            File repositoryControl = getTaskContext().getRepositoryControl();
            PullCommand pullCommand = new Git(new FileRepository(repositoryControl)).pull();
            pullCommand.call();
        } catch (IOException | GitAPIException e) {
            LOG.warn("Call git pull failed.");
            FileUtils.deleteQuietly(getTaskContext().getRepositoryRoot());
            cloneRepository();
        }
    }

    @Override
    public void cloneRepository() {
        CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(getTaskContext().getRepository())
                .setDirectory(getTaskContext().getRepositoryRoot())
                .setBranch("master")
                .setCloneSubmodules(true);
        try {
            cloneCommand.call();
        } catch (GitAPIException e) {
            LOG.warn("Clone git repository {} failed, reason: {}", getTaskContext().getRepository(), e);
        }
    }
}
