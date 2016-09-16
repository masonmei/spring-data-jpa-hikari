package com.igitras.hikari.service.repos;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Test cases for class .
 *
 * @author mason
 */
public class GitRepositorySyncTaskTest {

    private GitRepositorySyncTaskContext context;

    @Before
    public void setUp() throws Exception {
        context = new GitRepositorySyncTaskContext()
                .setRemoteUrl("https://github.com/masonmei/mason-demo.git")
                .setTargetFolder(new File("./target/mason-demo"));
    }

    @Test
    public void run() throws Exception {
        new GitRepositorySyncTask(context).run();
    }

    @Test
    public void pullRepository() throws Exception {
        new GitRepositorySyncTask(context).pullRepository();
    }

    @Test
    public void cloneRepository() throws Exception {
        new GitRepositorySyncTask(context).cloneRepository();
    }

}