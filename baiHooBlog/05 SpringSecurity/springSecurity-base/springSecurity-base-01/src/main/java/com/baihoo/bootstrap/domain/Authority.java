package com.baihoo.bootstrap.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

/**
 * 用戶授權角色類
 * @author Administrator
 *
 */
@Entity(name="Authority")
public class Authority  implements GrantedAuthority , Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Size(min = 2, max = 32)
	@Column(name="auth_name" , nullable = false, length = 20) // 映射为字段，值不能为空
	private String authName;
	
	public Authority() {
		super();
	}
	@Override
	public String toString() {
		return "Authority [id=" + id + ", authName=" + authName + "]";
	}
	public Authority(Long id, String authName) {
		super();
		this.id = id;
		this.authName = authName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	@Override
	public String getAuthority() {
		//權限名稱
		return authName;
	}
}
