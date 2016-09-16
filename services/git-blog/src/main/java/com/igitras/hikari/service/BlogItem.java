package com.igitras.hikari.service;

import java.io.File;
import java.io.Serializable;

/**
 * @author mason
 */
public class BlogItem implements Serializable {

    private static final long serialVersionUID = 4845241382272403973L;

    private String name;
    private String relativePath;

    public BlogItem() {
    }

    public BlogItem(File directory, File file) {
        this.name = file.getName();
        this.relativePath = file.getPath().substring(directory.getPath().length());
    }

    public String getName() {
        return name;
    }

    public BlogItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public BlogItem setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }
}
