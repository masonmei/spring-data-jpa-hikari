package com.igitras.hikari.mvc.rest;

import static com.igitras.hikari.utils.FileUtil.resolveFolder;

import static java.io.File.separator;
import static java.lang.String.format;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.domain.SyncRepo;
import com.igitras.hikari.domain.SyncRepoRepository;
import com.igitras.hikari.service.MenuItem;
import com.igitras.hikari.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Menu Resources.
 *
 * @author mason
 */
@RestController
@RequestMapping(value = "api/menus")
public class MenuResource {

    private static final Logger LOG = LoggerFactory.getLogger(MenuResource.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private SyncRepoRepository syncRepoRepository;

    @Autowired
    private AppProperties properties;

    @GetMapping
    public MenuItem loadMenu() throws FileNotFoundException {
        File directory = resolveFolder(properties.getDownloadFolder());
        return menuService.loadMenuItem(directory);
    }

    @GetMapping("{repoId}")
    public MenuItem loadRepoMenu(@PathVariable Long repoId) throws FileNotFoundException {
        return Optional.ofNullable(syncRepoRepository.findOne(repoId))
                .map(syncRepo -> {
                    try {
                        File directory = resolveFolder(properties.getDownloadFolder());
                        directory = new File(directory, syncRepo.getRelativePath());
                        MenuItem menuItem = menuService.loadMenuItem(directory);
                        normalizeMenu(menuItem, syncRepo);
                        return menuItem;
                    } catch (FileNotFoundException e) {
                        LOG.warn("load menus of repo {} failed.", syncRepo);
                        return null;
                    }
                })
                .orElseGet(() -> null);
    }

    /**
     * Normalize the menuItem with Repository Synchronise Entity.
     *
     * @param menuItem menu item
     * @param syncRepo repository synchronise task.
     */
    protected void normalizeMenu(MenuItem menuItem, SyncRepo syncRepo) {
        Assert.notNull(menuItem, "MenuItem must not be null.");
        if (syncRepo == null) {
            return;
        }

        processMenuRelativePath(menuItem, syncRepo.getRelativePath());
    }

    /**
     * Normalize the menuItem with Repository Synchronise Entity.
     *
     * @param menuItem menu item
     * @param prefix   prefix
     */
    protected void processMenuRelativePath(MenuItem menuItem, String prefix) {
        menuItem.setRelativePath(format("%s%s%s", prefix, separator, menuItem.getRelativePath()));
        menuItem.getChildren()
                .forEach(item -> processMenuRelativePath(item, prefix));
    }
}
