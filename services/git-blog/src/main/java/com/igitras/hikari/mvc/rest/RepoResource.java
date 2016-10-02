package com.igitras.hikari.mvc.rest;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

import com.igitras.hikari.domain.SyncRepo;
import com.igitras.hikari.domain.SyncRepoRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Git Repositories controller.
 *
 * @author mason
 */
@RestController
@RequestMapping(value = "api/repos")
public class RepoResource {

    @Autowired
    private SyncRepoRepository repository;

    @GetMapping("all")
    public List<GitRepoDto> all() {

        return repository.findAll()
                .stream()
                .map(source -> new GitRepoDto().setId(source.getId())
                        .setRepositoryType(source.getRepositoryType())
                        .setTagName(source.getTagName())
                        .setRefreshInterval(source.getRefreshInterval())
                        .setRelativePath(source.getRelativePath())
                        .setRepository(source.getRepository()))
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<GitRepoDto> search(Pageable page) {
        SyncRepo gitRepo = new SyncRepo();
        Example<SyncRepo> example = Example.of(gitRepo, ExampleMatcher.matching()
                .withStringMatcher(CONTAINING));
        return repository.findAll(example, page)
                .map(source -> new GitRepoDto().setId(source.getId())
                        .setRepositoryType(source.getRepositoryType())
                        .setTagName(source.getTagName())
                        .setRefreshInterval(source.getRefreshInterval())
                        .setRelativePath(source.getRelativePath())
                        .setRepository(source.getRepository()));
    }

    @GetMapping("{id}")
    public GitRepoDto find(@PathVariable Long id) {
        return Optional.of(repository.findOne(id))
                .map(source -> new GitRepoDto().setId(source.getId())
                        .setRepositoryType(source.getRepositoryType())
                        .setTagName(source.getTagName())
                        .setRefreshInterval(source.getRefreshInterval())
                        .setRelativePath(source.getRelativePath())
                        .setRepository(source.getRepository()))
                .orElseGet(() -> null);
    }

    @PostMapping
    public GitRepoDto create(@RequestBody GitRepoDto dto) {
        SyncRepo syncRepoEntity = new SyncRepo().setRefreshInterval(dto.getRefreshInterval())
                .setRepositoryType(dto.getRepositoryType())
                .setTagName(dto.getTagName())
                .setRelativePath(dto.getRelativePath())
                .setRepository(dto.getRepository());
        return Optional.of(repository.save(syncRepoEntity))
                .map(source -> new GitRepoDto().setId(source.getId())
                        .setRepositoryType(source.getRepositoryType())
                        .setTagName(source.getTagName())
                        .setRefreshInterval(source.getRefreshInterval())
                        .setRelativePath(source.getRelativePath())
                        .setRepository(source.getRepository()))
                .orElseGet(() -> null);
    }

    @PutMapping("{id}")
    public GitRepoDto update(@PathVariable Long id, @RequestBody GitRepoDto dto) {
        return Optional.ofNullable(repository.findOne(id))
                .map(syncRepoEntity -> {
                    syncRepoEntity.setRepository(dto.getRepository())
                            .setRelativePath(dto.getRelativePath())
                            .setRefreshInterval(dto.getRefreshInterval())
                            .setRepositoryType(dto.getRepositoryType())
                            .setTagName(dto.getTagName());
                    SyncRepo source = repository.save(syncRepoEntity);
                    return new GitRepoDto().setId(source.getId())
                            .setRepositoryType(source.getRepositoryType())
                            .setTagName(source.getTagName())
                            .setRefreshInterval(source.getRefreshInterval())
                            .setRelativePath(source.getRelativePath())
                            .setRepository(source.getRepository());
                })
                .orElseGet(() -> null);
    }

    @DeleteMapping("{id}")
    public GitRepoDto delete(@PathVariable Long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(syncRepoEntity -> {
                    repository.delete(syncRepoEntity);
                    return new GitRepoDto().setId(syncRepoEntity.getId())
                            .setRepositoryType(syncRepoEntity.getRepositoryType())
                            .setTagName(syncRepoEntity.getTagName())
                            .setRefreshInterval(syncRepoEntity.getRefreshInterval())
                            .setRelativePath(syncRepoEntity.getRelativePath())
                            .setRepository(syncRepoEntity.getRepository());
                })
                .orElseGet(() -> null);
    }

}
