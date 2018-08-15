package com.baihoo.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.baihoo.jpa.domain.User;

/**
 * UserRepository 接口
 * @author Administrator
 *
 */
public interface UserRepository  extends CrudRepository<User , Long>{

}
