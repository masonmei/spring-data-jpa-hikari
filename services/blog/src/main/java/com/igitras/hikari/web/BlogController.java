package com.igitras.hikari.web;

import com.igitras.hikari.domain.BlogEntity;
import com.igitras.hikari.domain.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Blog Controller.
 *
 * @author mason
 */
@RestController
@RequestMapping("blogs")
public class BlogController {
    @Autowired
    private BlogRepository repository;

    @GetMapping
    public List<BlogEntity> search() {
        return repository.findAll();
    }

    @GetMapping(value = "{id}")
    public BlogEntity find(@PathVariable("id") Long id) {
        return repository.findOne(id);
    }

    @PostMapping
    public BlogEntity create(@RequestBody BlogEntity blogEntity) {
        return repository.save(blogEntity);
    }

    @PutMapping(value = "{id}")
    public BlogEntity update(@PathVariable(value = "id") Long id, @RequestBody BlogEntity entity) {
        return Optional.ofNullable(repository.findOne(id))
                .map(blogEntity -> {
                    blogEntity.setAuthor(entity.getAuthor())
                            .setContent(entity.getContent())
                            .setSummary(entity.getSummary())
                            .setTitle(entity.getTitle());
                    return repository.saveAndFlush(blogEntity);
                })
                .orElseGet(() -> null);
    }

    @PatchMapping("{id}")
    public BlogEntity patch(@PathVariable Long id, @RequestBody BlogEntity entity) {
        return Optional.ofNullable(repository.findOne(id))
                .map(blogEntity -> {
                    if (entity.getAuthor() != null) {
                        blogEntity.setAuthor(entity.getAuthor());
                    }

                    if (entity.getContent() != null) {
                        blogEntity.setContent(entity.getContent());
                    }

                    return repository.saveAndFlush(blogEntity);
                })
                .orElseGet(() -> null);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable Long id) {
        repository.delete(id);
    }
}
