package com.igitras.hikari.sync.init;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.domain.GitRepoEntity;
import com.igitras.hikari.domain.GitRepoRepository;
import com.igitras.hikari.service.repos.GitRepositorySyncService;
import com.igitras.hikari.service.repos.GitRepositorySyncTaskContext;
import com.igitras.hikari.utils.FileUtil;
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
    private GitRepoRepository repository;

    @Autowired
    private GitRepositorySyncService syncService;

    @Override
    public void run(String... args) throws Exception {
        repository.findAll()
                .stream()
                .filter(GitRepoEntity::isEnabled)
                .forEach(gitRepoEntity -> {
                    try {
                        GitRepositorySyncTaskContext context =
                                new GitRepositorySyncTaskContext().setRemoteUrl(gitRepoEntity.getRepository())
                                        .setTargetFolder(buildTargetFolder(gitRepoEntity.getRelativePath()))
                                        .setRefreshInterval(gitRepoEntity.getRefreshInterval());
                        syncService.addTask(context);
                    } catch (FileNotFoundException e) {
                        LOG.warn("Schedule task for {} failed", gitRepoEntity);
                    }
                });
    }

    private File buildTargetFolder(String relativePath) throws FileNotFoundException {
        File downloadFolder = FileUtil.resolveFolder(properties.getDownloadFolder());
        return new File(downloadFolder, relativePath);
    }
}
