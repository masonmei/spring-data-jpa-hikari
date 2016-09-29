package com.igitras.hikari.mvc.dto;

import static com.igitras.hikari.common.RepositoryStatus.INITIALIZED;
import static com.igitras.hikari.common.RepositoryType.GITHUB_PUBLIC;

import com.igitras.hikari.common.RepositoryStatus;
import com.igitras.hikari.common.RepositoryType;

import java.io.Serializable;

/**
 * @author mason
 */
public class GitRepoDto implements Serializable {
    private static final long serialVersionUID = 7437079564206045667L;

    private Long id;

    private String repository;

    private String relativePath;

    private String tagName;

    private Long refreshInterval;

    private RepositoryType repositoryType = GITHUB_PUBLIC;

    private RepositoryStatus repositoryStatus = INITIALIZED;


    public Long getId() {
        return id;
    }

    public GitRepoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRepository() {
        return repository;
    }

    public GitRepoDto setRepository(String repository) {
        this.repository = repository;
        return this;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public GitRepoDto setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    public GitRepoDto setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public Long getRefreshInterval() {
        return refreshInterval;
    }

    public GitRepoDto setRefreshInterval(Long refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    public RepositoryType getRepositoryType() {
        return repositoryType;
    }

    public GitRepoDto setRepositoryType(RepositoryType repositoryType) {
        this.repositoryType = repositoryType;
        return this;
    }

    public RepositoryStatus getRepositoryStatus() {
        return repositoryStatus;
    }

    public GitRepoDto setRepositoryStatus(RepositoryStatus repositoryStatus) {
        this.repositoryStatus = repositoryStatus;
        return this;
    }
}
