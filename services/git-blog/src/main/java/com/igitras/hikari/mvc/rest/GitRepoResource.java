package com.igitras.hikari.mvc.rest;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

import com.igitras.hikari.domain.GitRepoEntity;
import com.igitras.hikari.domain.GitRepoRepository;
import com.igitras.hikari.mvc.dto.GitRepoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Git Repositories controller.
 *
 * @author mason
 */
@RestController
@RequestMapping(value = "git-repos")
public class GitRepoResource {

    @Autowired
    private GitRepoRepository repository;

    @GetMapping
    public Page<GitRepoDto> search(Pageable page) {
        GitRepoEntity gitRepo = new GitRepoEntity();
        Example<GitRepoEntity> example = Example.of(gitRepo, ExampleMatcher.matching()
                .withStringMatcher(CONTAINING));
        return repository.findAll(example, page)
                .map(source -> new GitRepoDto().setId(source.getId())
                        .setEnabled(source.isEnabled())
                        .setRefreshInterval(source.getRefreshInterval())
                        .setRelativePath(source.getRelativePath())
                        .setRepository(source.getRepository()));
    }

    @GetMapping("{id}")
    public GitRepoDto find(@PathVariable Long id) {
        return Optional.of(repository.findOne(id))
                .map(source -> new GitRepoDto().setId(source.getId())
                        .setEnabled(source.isEnabled())
                        .setRefreshInterval(source.getRefreshInterval())
                        .setRelativePath(source.getRelativePath())
                        .setRepository(source.getRepository()))
                .orElseGet(() -> null);
    }

    @PostMapping
    public GitRepoDto create(@RequestBody GitRepoDto dto) {
        GitRepoEntity gitRepoEntity = new GitRepoEntity().setRefreshInterval(dto.getRefreshInterval())
                .setEnabled(dto.isEnabled())
                .setRelativePath(dto.getRelativePath())
                .setRepository(dto.getRepository());
        return Optional.of(repository.save(gitRepoEntity))
                .map(source -> new GitRepoDto().setId(source.getId())
                        .setEnabled(source.isEnabled())
                        .setRefreshInterval(source.getRefreshInterval())
                        .setRelativePath(source.getRelativePath())
                        .setRepository(source.getRepository()))
                .orElseGet(() -> null);
    }

    @PutMapping("{id}")
    public GitRepoDto update(@PathVariable Long id, @RequestBody GitRepoDto dto) {
        return Optional.ofNullable(repository.findOne(id))
                .map(gitRepoEntity -> {
                    gitRepoEntity.setRepository(dto.getRepository())
                            .setRelativePath(dto.getRelativePath())
                            .setRefreshInterval(dto.getRefreshInterval())
                            .setEnabled(dto.isEnabled());
                    GitRepoEntity source = repository.save(gitRepoEntity);
                    return new GitRepoDto().setId(source.getId())
                            .setEnabled(source.isEnabled())
                            .setRefreshInterval(source.getRefreshInterval())
                            .setRelativePath(source.getRelativePath())
                            .setRepository(source.getRepository());
                })
                .orElseGet(() -> null);
    }

    @DeleteMapping("{id}")
    public GitRepoDto delete(@PathVariable Long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(gitRepoEntity -> {
                    repository.delete(gitRepoEntity);
                    return new GitRepoDto().setId(gitRepoEntity.getId())
                            .setEnabled(gitRepoEntity.isEnabled())
                            .setRefreshInterval(gitRepoEntity.getRefreshInterval())
                            .setRelativePath(gitRepoEntity.getRelativePath())
                            .setRepository(gitRepoEntity.getRepository());
                })
                .orElseGet(() -> null);
    }

}
