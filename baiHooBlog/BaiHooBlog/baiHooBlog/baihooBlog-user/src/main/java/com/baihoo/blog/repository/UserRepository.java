package com.baihoo.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.baihoo.blog.domain.User;

/**
 * UserRepository 接口 繼承jpa繼承
 * @author Administrator
 *
 */
public interface UserRepository  extends JpaRepository<User, Long>{

	/**
	 * 通過用戶姓名，分頁查詢<br>
	 * 注意：分页返回的对象都是Page对象
	 * @param name
	 * @param page
	 * @return
	 */
	Page<User> findByNameLike(String name , Pageable page);
	/**
	 * 通過用戶名，查詢用戶，用戶名，將不能重複
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
}
