package com.igitras.hikari.domain;

import static com.igitras.hikari.common.RepositoryStatus.INITIALIZED;
import static com.igitras.hikari.common.RepositoryType.GITHUB_PUBLIC;

import static javax.persistence.EnumType.STRING;

import com.google.common.base.MoreObjects;
import com.igitras.hikari.common.RepositoryStatus;
import com.igitras.hikari.common.RepositoryType;
import com.igitras.hikari.sync.audit.RepositorySyncTaskEntityListener;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Repository Tasks.
 *
 * @author mason
 */
@Table
@Entity(name = "sync_repo")
@EntityListeners({RepositorySyncTaskEntityListener.class})
public class SyncRepo extends AbstractPersistable<Long>  {

    private static final long serialVersionUID = 310426861950834109L;

    /**
     * The remote Repository uri.
     */
    @NotNull
    @URL
    @Column(unique = true)
    private String repository;

    /**
     * The repository type. Must be one of {@link RepositoryType}.
     */
    @NotNull
    @Enumerated(value = STRING)
    private RepositoryType repositoryType = GITHUB_PUBLIC;

    /**
     * Repository Status, must be one of {@link RepositoryStatus}.
     */
    @NotNull
    @Enumerated(value = STRING)
    private RepositoryStatus repositoryStatus = INITIALIZED;

    /**
     * The name will be display for the menu bar.
     * <p>
     * Default value the is the last section of relative path.
     */
    @NotNull
    private String tagName;

    /**
     * The relative path is where put the repository inside the repository.
     */
    @NotNull
    @Column(unique = true)
    private String relativePath;

    /**
     * The repository refresh interval.
     */
    @Min(value = 10_000L)
    private Long refreshInterval;

    public String getRepository() {
        return repository;
    }

    public SyncRepo setRepository(String repository) {
        this.repository = repository;
        return this;
    }

    public RepositoryType getRepositoryType() {
        return repositoryType;
    }

    public SyncRepo setRepositoryType(RepositoryType repositoryType) {
        this.repositoryType = repositoryType;
        return this;
    }

    public RepositoryStatus getRepositoryStatus() {
        return repositoryStatus;
    }

    public SyncRepo setRepositoryStatus(RepositoryStatus repositoryStatus) {
        this.repositoryStatus = repositoryStatus;
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    public SyncRepo setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public SyncRepo setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    public Long getRefreshInterval() {
        return refreshInterval;
    }

    public SyncRepo setRefreshInterval(Long refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("repository", repository)
                .add("repositoryType", repositoryType)
                .add("repositoryStatus", repositoryStatus)
                .add("tagName", tagName)
                .add("relativePath", relativePath)
                .add("refreshInterval", refreshInterval)
                .toString();
    }
}
