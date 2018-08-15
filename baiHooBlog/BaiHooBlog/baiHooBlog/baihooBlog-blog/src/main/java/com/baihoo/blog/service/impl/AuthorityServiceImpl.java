package com.baihoo.blog.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baihoo.blog.domain.Authority;
import com.baihoo.blog.repository.AuthorityRepository;
import com.baihoo.blog.service.AuthorityService;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	@Qualifier("authorityRepository")
	private AuthorityRepository authorityRepository;
	
	/**
	 * 根據id查詢授權對象
	 */
	@Override
	public Authority findById(Long id) throws Exception{
		Optional<Authority> opAuth = authorityRepository.findById(id);
		return opAuth.get();
	}

}
