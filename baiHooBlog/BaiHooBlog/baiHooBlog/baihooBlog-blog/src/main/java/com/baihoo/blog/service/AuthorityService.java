package com.baihoo.blog.service;

import com.baihoo.blog.domain.Authority;

/**
 * 
 * @author Administrator
 *
 */
public interface AuthorityService {
	
	/**
	 * 根據id查詢授權對象
	 * @param id
	 * @return
	 */
	public Authority findById(Long id) throws Exception;
}
