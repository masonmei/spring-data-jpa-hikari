package com.igitras.hikari.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Blog Repository.
 *
 * @author mason
 */
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {

    Page<BlogEntity> findAllByTagsNameContains(Pageable pageable, String name);
}
