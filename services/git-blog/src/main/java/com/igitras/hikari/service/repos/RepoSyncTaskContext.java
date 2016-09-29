package com.igitras.hikari.service.repos;

import com.igitras.hikari.common.RepositoryType;

import java.io.File;

/**
 * Repository synchronized task context.
 *
 * @author mason
 */
public interface RepoSyncTaskContext {

    /**
     * Get the repository uri.
     *
     * @return repository uri
     */
    String getRepository();

    /**
     * Get the repository type.
     *
     * @return repository type
     */
    RepositoryType getRepositoryType();

    /**
     * Get the relative path.
     *
     * @return repository store relative path.
     */
    String getRelativePath();

    /**
     * Get the repository refresh interval in ms.
     *
     * @return sync period in ms
     */
    Long getRefreshInterval();

    /**
     * Get the folder where to save the repository content.
     * <p>
     * The repository root must be exist and writable.
     *
     * @return repository root.
     */
    File getRepositoryRoot();

    /**
     * Get the folder where the repository control stores.
     *
     * @return repository control
     */
    File getRepositoryControl();
}
