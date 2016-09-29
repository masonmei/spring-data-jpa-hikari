package com.igitras.hikari.service.repos;

import com.igitras.hikari.common.RepositoryType;
import com.igitras.hikari.service.repos.github.GithubRepoSyncTask;
import com.igitras.hikari.service.repos.github.GithubRepoSyncTaskContext;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Test cases for class .
 *
 * @author mason
 */
public class GitRepositorySyncTaskTest {

    private GithubRepoSyncTaskContext context;

    @Before
    public void setUp() throws Exception {
        context = (GithubRepoSyncTaskContext) new GithubRepoSyncTaskContext()
                .setRepository("https://github.com/masonmei/mason-demo.git")
                .setRepositoryType(RepositoryType.GITHUB_PUBLIC)
                .setBaseFolder(new File("./"))
                .setRelativePath("target/mason-demo");
    }

    @Test
    public void run() throws Exception {
        new GithubRepoSyncTask(context).run();
    }

    @Test
    public void pullRepository() throws Exception {
        new GithubRepoSyncTask(context).pullRepository();
    }

    @Test
    public void cloneRepository() throws Exception {
        new GithubRepoSyncTask(context).cloneRepository();
    }

}