package com.igitras.hikari.sync.init;

import static com.igitras.hikari.common.RepositoryStatus.SYNCHRONIZED;
import static com.igitras.hikari.common.RepositoryStatus.TERMINATED;
import static com.igitras.hikari.service.repos.RepoSyncTaskContextFactory.buildContext;
import static com.igitras.hikari.utils.CollectionUtil.contains;
import static com.igitras.hikari.utils.FileUtil.resolveFolder;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.domain.SyncRepoRepository;
import com.igitras.hikari.service.repos.RepoSyncTaskContext;
import com.igitras.hikari.service.repos.RepositorySyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author mason
 */
@Component
public class ScheduleDatabaseTaskCommand implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleDatabaseTaskCommand.class);

    @Autowired
    private AppProperties properties;

    @Autowired
    private SyncRepoRepository repository;

    @Autowired
    private RepositorySyncService syncService;

    @Override
    public void run(String... args) throws FileNotFoundException {
        final File baseFolder = resolveFolder(properties.getDownloadFolder());
        repository.findAll()
                .stream()
                .filter(syncRepoEntity -> !contains(syncRepoEntity.getRepositoryStatus(), SYNCHRONIZED, TERMINATED))
                .forEach(syncRepoEntity -> {
                    try {
                        RepoSyncTaskContext taskContext = buildContext(baseFolder, syncRepoEntity);
                        syncService.addTask(taskContext);
                    } catch (Exception e) {
                        LOG.warn("Schedule task for {} failed, exception: {}", syncRepoEntity, e);
                    }
                });
    }

}
