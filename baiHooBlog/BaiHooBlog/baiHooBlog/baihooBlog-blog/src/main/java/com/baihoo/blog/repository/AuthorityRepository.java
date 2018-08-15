package com.baihoo.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baihoo.blog.domain.Authority;

/**
 * 實現JPA接口類
 * @author Administrator
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
