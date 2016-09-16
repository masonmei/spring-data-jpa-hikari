package com.igitras.hikari.service;

import static org.apache.commons.io.FileUtils.listFilesAndDirs;
import static org.apache.commons.io.filefilter.FileFilterUtils.and;
import static org.apache.commons.io.filefilter.FileFilterUtils.directoryFileFilter;
import static org.apache.commons.io.filefilter.FileFilterUtils.nameFileFilter;
import static org.apache.commons.io.filefilter.FileFilterUtils.notFileFilter;
import static org.springframework.util.StringUtils.trimLeadingCharacter;
import static org.springframework.util.StringUtils.trimTrailingCharacter;

import static java.io.File.separatorChar;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.awt.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Maintain the menus base the git repositories
 *
 * @author mason
 */
@Component
public class MenuService {

    @Cacheable("menu")
    public MenuItem loadMenuItem(File rootDirectory) throws FileNotFoundException {
        return buildMenu(loadMenuableDirectories(rootDirectory));
    }

    protected Collection<String> loadMenuableDirectories(File rootDirectory) throws FileNotFoundException {
        IOFileFilter notGitFolderFilter = notFileFilter(and(directoryFileFilter(), nameFileFilter(".git")));
        NotFileFilter notFileFilter = new NotFileFilter(TrueFileFilter.INSTANCE);
        return listFilesAndDirs(rootDirectory, notFileFilter, notGitFolderFilter).stream()
                .map(file -> file.getPath()
                        .substring(rootDirectory.getPath()
                                .length()))
                .collect(Collectors.toList());
    }

    protected MenuItem buildMenu(Collection<String> menuableDirectories) {
        Map<String, MenuItem> allItems = menuableDirectories.stream()
                .map(s -> new MenuItem().setRelativePath(calculateRelativePath(s))
                        .setName(calculateName(s)))
                .collect(Collectors.toMap(MenuItem::getRelativePath, s -> s));
        allItems.forEach((s, item) -> {
            if (StringUtils.isEmpty(s)) {
                return;
            }
            String parentPath = item.calculateParentPath();
            MenuItem parent = allItems.get(parentPath);
            parent.addChild(item);
            item.setParent(parent);
        });

        return allItems.get("");
    }

    private String calculateName(String relativePath) {
        relativePath = calculateRelativePath(relativePath);
        int indexOf = relativePath.lastIndexOf(File.separator);

        if (indexOf < 0) {
            indexOf = 0;
        }
        return calculateRelativePath(relativePath.substring(indexOf));
    }

    private String calculateRelativePath(String relativePath) {
        relativePath = trimLeadingCharacter(relativePath, separatorChar);
        relativePath = trimTrailingCharacter(relativePath, separatorChar);
        return relativePath;
    }


}
