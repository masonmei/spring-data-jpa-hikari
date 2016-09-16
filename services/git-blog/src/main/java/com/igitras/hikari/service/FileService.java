package com.igitras.hikari.service;

import static org.apache.commons.io.FileUtils.listFiles;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * File list service.
 *
 * @author mason
 */
@Component
public class FileService {
    private static final String[] EXTENSIONS = {"md"};

    @Cacheable("blog-items")
    public List<BlogItem> loadFiles(File directory, String relativePath) {
        File readFileDirectory = new File(directory, relativePath);

        return listFiles(readFileDirectory, EXTENSIONS, true).stream()
                .map(file -> new BlogItem(directory, file))
                .collect(Collectors.toList());
    }
}
