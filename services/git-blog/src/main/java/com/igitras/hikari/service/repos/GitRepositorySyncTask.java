package com.igitras.hikari.service.repos;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

/**
 * Git Repository Sync Task.
 *
 * @author mason
 */
public class GitRepositorySyncTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(GitRepositorySyncTask.class);

    private final GitRepositorySyncTaskContext context;

    public GitRepositorySyncTaskContext getContext() {
        return context;
    }

    public GitRepositorySyncTask(GitRepositorySyncTaskContext context) {
        Assert.notNull(context, "SyncTaskContext must not be null.");
        this.context = context;
    }

    @Override
    public void run() {
        if (context.isStopped()) {
            return;
        }

        if (context.getTargetFolder()
                .exists()) {
            pullRepository();
        } else {
            cloneRepository();
        }
    }

    protected void pullRepository() {
        try {
            File gitDirectory = new File(context.getTargetFolder(), ".git");
            PullCommand pullCommand = new Git(new FileRepository(gitDirectory)).pull();
            pullCommand.call();
        } catch (IOException | GitAPIException e) {
            LOG.warn("Call git pull failed.");
            FileUtils.deleteQuietly(context.getTargetFolder());
            cloneRepository();
        }
    }

    protected void cloneRepository() {
        CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(context.getRemoteUrl())
                .setDirectory(context.getTargetFolder())
                .setBranch("master")
                .setCloneSubmodules(true);
        try {
            cloneCommand.call();
        } catch (GitAPIException e) {
            LOG.warn("Clone git repository {} failed, reason: {}", context.getRemoteUrl(), e);
        }
    }
}
