package com.igitras.hikari.domain;

import com.google.common.base.MoreObjects;
import com.igitras.hikari.sync.RepositorySyncTaskConfig;
import com.igitras.hikari.sync.audit.RepositorySyncTaskEntityListener;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Repository Tasks.
 *
 * @author mason
 */
@Table
@Entity(name = "git_repo")
@EntityListeners({RepositorySyncTaskEntityListener.class})
public class GitRepoEntity extends AbstractPersistable<Long> implements RepositorySyncTaskConfig {

    private static final long serialVersionUID = 310426861950834109L;

    @NotNull
    @URL
    @Column(unique = true)
    private String repository;

    @NotNull
    private String relativePath;

    @Min(value = 10_000L)
    private Long refreshInterval;

    private boolean enabled = true;

    public String getRepository() {
        return repository;
    }

    public GitRepoEntity setRepository(String repository) {
        this.repository = repository;
        return this;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public GitRepoEntity setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    public Long getRefreshInterval() {
        return refreshInterval;
    }

    public GitRepoEntity setRefreshInterval(Long refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public GitRepoEntity setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("repository", repository)
                .add("relativePath", relativePath)
                .add("refreshInterval", refreshInterval)
                .add("enabled", enabled)
                .toString();
    }
}
