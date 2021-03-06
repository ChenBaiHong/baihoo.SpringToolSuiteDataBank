package com.baihoo.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.baihoo.blog.domain.User;

/**
 * 用戶服務層接口
 * @author Administrator
 *
 */
public interface UserService extends UserDetailsService{
	/**
	 * 用户账号，用户登录时的唯一标识,不可重複檢查
	 * @param username
	 * @return
	 */
	public boolean isUsernameUnique(String username) throws Exception;
	/**
	 * 查詢所有用戶
	 * @return
	 */
	public List<User> findAll();
	/**
	 * 通過用戶姓名，分頁查詢
	 * @param name
	 * @param page
	 * @return
	 */
	public Page<User> findByNameLike(String name , Pageable page) throws Exception;
	/**
	 * 通過用戶名稱查詢用戶
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) throws Exception;
	/**
	 * 注冊用戶
	 * @param user
	 * @return
	 */
	public User registerUser(User user) throws Exception;
	/**
	 * 通過id查詢用戶
	 * @param id
	 * @return
	 */
	public User findById(Long id) throws Exception;
	/**
	 * 更新或保存用戶
	 * @param user
	 * @return
	 */
	public User saveOrUpdate(User user) throws Exception;
	/**
	 * 通過id刪除用戶
	 * @param id
	 */
	public void deleteById(Long id) throws Exception;
	/**
	 * 刪除用戶
	 * @param user
	 */
	public void deleteUser(User user) throws Exception;
}
