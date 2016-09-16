package com.igitras.hikari.domain;

import com.google.common.base.MoreObjects;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Tag Entity.
 *
 * @author mason
 */
@Entity
@Table(name = "tag")
public class Tag extends AbstractPersistable<Long> {
    private static final long serialVersionUID = 1584436018024186949L;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "tags")
    private Set<BlogEntity> blogs = new HashSet<>();

    public Tag() {
    }

    public Tag(Long id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public Tag setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Tag setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<BlogEntity> getBlogs() {
        return blogs;
    }

    public Tag setBlogs(Set<BlogEntity> blogs) {
        this.blogs = blogs;
        return this;
    }

    @Override
    protected void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("blogs", blogs)
                .toString();
    }
}
