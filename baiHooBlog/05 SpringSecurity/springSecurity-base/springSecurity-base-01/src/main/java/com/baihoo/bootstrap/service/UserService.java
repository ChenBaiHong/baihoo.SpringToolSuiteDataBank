package com.baihoo.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baihoo.bootstrap.domain.User;
import com.baihoo.bootstrap.repository.UserRepository;

/**
 * 
 * UserService 服務層<br>
 * UserService 實現 UserDetailsService 接口，重寫其方法！這是必須<br>
 * 
 * @author Administrator
 *
 */
@Service("userservice")
@SuppressWarnings("all")
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null ) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		Boolean locked = user.isAccountNonLocked();
		
		return user;
	}
	
	public User findByUsernameAndPassword(String username , String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}
}
