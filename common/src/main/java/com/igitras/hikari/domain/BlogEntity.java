package com.igitras.hikari.domain;

import static javax.persistence.FetchType.LAZY;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by mason on 9/1/16.
 */
@Table
@Entity(name = "blog")
public class BlogEntity extends AbstractPersistable<Long> {

    @NotNull
    @Size(min = 5)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "summary")
    @Basic
    @Lob
    private String summary;

    @Column(name = "content")
    @Basic(fetch = LAZY)
    @Lob
    private String content;

    public String getTitle() {
        return title;
    }

    public BlogEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BlogEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public BlogEntity setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogEntity setContent(String content) {
        this.content = content;
        return this;
    }
}
