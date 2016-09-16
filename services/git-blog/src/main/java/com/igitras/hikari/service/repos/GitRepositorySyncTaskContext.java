package com.igitras.hikari.service.repos;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.URL;

import java.io.File;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Git Repository Sync Task Context.
 *
 * @author mason
 */
public class GitRepositorySyncTaskContext {

    @NotNull
    @URL
    private String remoteUrl;

    @NotNull
    private File targetFolder;

    @Min(value = 300_000L)
    private Long refreshInterval;

    private boolean stopped;

    public GitRepositorySyncTaskContext() {
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public boolean isStopped() {
        return stopped;
    }

    public GitRepositorySyncTaskContext setStopped(boolean stopped) {
        this.stopped = stopped;
        return this;
    }

    public GitRepositorySyncTaskContext setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
        return this;
    }

    public File getTargetFolder() {
        return targetFolder;
    }

    public GitRepositorySyncTaskContext setTargetFolder(File targetFolder) {
        this.targetFolder = targetFolder;
        return this;
    }

    public Long getRefreshInterval() {
        return refreshInterval;
    }

    public GitRepositorySyncTaskContext setRefreshInterval(Long refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GitRepositorySyncTaskContext that = (GitRepositorySyncTaskContext) o;
        return Objects.equal(remoteUrl, that.remoteUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(remoteUrl);
    }
}
