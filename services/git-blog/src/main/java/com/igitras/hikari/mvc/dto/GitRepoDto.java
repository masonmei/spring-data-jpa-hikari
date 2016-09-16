package com.igitras.hikari.mvc.dto;

import java.io.Serializable;

/**
 * @author mason
 */
public class GitRepoDto implements Serializable {
    private static final long serialVersionUID = 7437079564206045667L;

    private Long id;

    private String repository;

    private String relativePath;

    private Long refreshInterval;

    private boolean enabled;

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

    public Long getRefreshInterval() {
        return refreshInterval;
    }

    public GitRepoDto setRefreshInterval(Long refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public GitRepoDto setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
