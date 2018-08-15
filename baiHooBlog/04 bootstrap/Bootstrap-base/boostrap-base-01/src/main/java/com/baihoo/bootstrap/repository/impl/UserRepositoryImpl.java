package com.baihoo.bootstrap.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.baihoo.bootstrap.domain.User;
import com.baihoo.bootstrap.repository.UserRepository;

/**
 * UserRepositoryImpl 实现 UserRepository 
 * @author Administrator
 *
 */
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
	
	private static AtomicLong counter = new AtomicLong(); //计数，每次添加用户，计数器递加
	
	private final ConcurrentMap<Long , User> userMap = new ConcurrentHashMap<Long , User>();
	/**
	 * 创建或修改用户
	 */
	public User saveOrUpdateUser(User user) {
		Long id = user.getId();
		if(id == null) {//id等于null,新建
			id = counter.incrementAndGet();//递增的值
			user.setId(id);
		}
		this.userMap.put(id, user);
		return user;
	}

	/**
	 * 删除用户
	 */
	public void deleteUser(Long id) {
		this.userMap.remove(id);
	}

	/**
	 * 根据id查询用户
	 */
	public User getUserById(Long id) {
		
		return this.userMap.get(id);
	}

	/**
	 * 获取用户列表
	 */
	public List<User> listUsers() {
		
		return new ArrayList<User> (this.userMap.values());
	}

}
