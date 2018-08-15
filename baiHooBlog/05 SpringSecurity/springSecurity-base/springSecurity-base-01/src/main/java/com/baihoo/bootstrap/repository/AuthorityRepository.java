package com.baihoo.bootstrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baihoo.bootstrap.domain.Authority;

/**
 * 實現JPA接口類
 * @author Administrator
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
