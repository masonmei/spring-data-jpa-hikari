package com.igitras.hikari.service;

import static org.springframework.util.StringUtils.trimTrailingCharacter;

import static java.io.File.separatorChar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author mason
 */
public class MenuItem implements Comparable<MenuItem>, Serializable {
    private static final long serialVersionUID = 3681291570707388260L;

    private String name;
    private String relativePath;
    @JsonIgnore
    private MenuItem parent;
    private SortedSet<MenuItem> children = new TreeSet<>();

    @Override
    public int compareTo(MenuItem o) {
        return this.name.compareTo(o.getName());
    }

    public String getName() {
        return name;
    }

    public MenuItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public MenuItem setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    public MenuItem getParent() {
        return parent;
    }

    public MenuItem setParent(MenuItem parent) {
        this.parent = parent;
        return this;
    }

    public SortedSet<MenuItem> getChildren() {
        return children;
    }

    public MenuItem addChild(MenuItem menuItem) {
        children.add(menuItem);
        return this;
    }

    public String calculateParentPath() {
        return trimTrailingCharacter(relativePath.substring(0, relativePath.lastIndexOf(name)), separatorChar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuItem menuItem = (MenuItem) o;
        return Objects.equal(name, menuItem.name) && Objects.equal(relativePath, menuItem.relativePath);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, relativePath);
    }
}
