package com.igitras.hikari.service.repos;

import com.igitras.hikari.common.RepositoryType;
import org.hibernate.validator.constraints.URL;

import java.io.File;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Repository Sync Task Context.
 *
 * @author mason
 */
public abstract class DefaultRepoSyncTaskContext implements RepoSyncTaskContext {

    @NotNull
    @URL
    private String repository;

    @NotNull
    private RepositoryType repositoryType;

    @NotNull
    private File baseFolder;

    @NotNull
    private String relativePath;

    @Min(value = 300_000L)
    private Long refreshInterval;

    @Override
    public String getRepository() {
        return repository;
    }

    public DefaultRepoSyncTaskContext setRepository(String repository) {
        this.repository = repository;
        return this;
    }

    @Override
    public RepositoryType getRepositoryType() {
        return repositoryType;
    }

    public DefaultRepoSyncTaskContext setRepositoryType(RepositoryType repositoryType) {
        this.repositoryType = repositoryType;
        return this;
    }

    public File getBaseFolder() {
        return baseFolder;
    }

    public DefaultRepoSyncTaskContext setBaseFolder(File baseFolder) {
        if (this.baseFolder != null) {
            throw new IllegalStateException("Root Folder of Repository synchronise task context not modifiable");
        }

        if (!baseFolder.isDirectory()) {
            throw new IllegalArgumentException("Root Folder of Repository synchronise task context must be a folder.");
        }

        if (!baseFolder.canWrite()) {
            throw new IllegalStateException("Root Folder of Repository synchronise task context must be writable.");
        }

        this.baseFolder = baseFolder;
        return this;
    }

    @Override
    public String getRelativePath() {
        return relativePath;
    }

    public DefaultRepoSyncTaskContext setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    @Override
    public Long getRefreshInterval() {
        return refreshInterval;
    }

    public DefaultRepoSyncTaskContext setRefreshInterval(Long refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    @Override
    public File getRepositoryRoot() {
        File repositoryRoot = new File(getBaseFolder(), getRelativePath());

        if (!repositoryRoot.exists()) {
            repositoryRoot.mkdirs();
        }

        if (!repositoryRoot.isDirectory()) {
            throw new IllegalArgumentException("Repository Root must be a folder.");
        }

        if (!repositoryRoot.canWrite()) {
            throw new IllegalStateException("Root Folder of Repository synchronise task context must be writable.");
        }

        return repositoryRoot;
    }

}
