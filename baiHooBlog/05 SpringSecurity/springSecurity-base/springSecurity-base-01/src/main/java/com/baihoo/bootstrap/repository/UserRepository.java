package com.baihoo.bootstrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baihoo.bootstrap.domain.User;

/**
 * UserRepository 接口
 * @author Administrator
 *
 */
public interface UserRepository  extends JpaRepository<User, Long>{
		/**
		 * 通過名稱查詢用戶
		 * @param username
		 * @return
		 */
		public User findByUsername(String username);
		/**
		 * 通過用戶名和密碼查詢用戶
		 * @param username
		 * @param password
		 * @return
		 */
		public User findByUsernameAndPassword(String username , String password);
}
