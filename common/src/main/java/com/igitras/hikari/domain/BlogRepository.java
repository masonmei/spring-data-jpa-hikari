package com.igitras.hikari.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 9/1/16.
 */
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
}
