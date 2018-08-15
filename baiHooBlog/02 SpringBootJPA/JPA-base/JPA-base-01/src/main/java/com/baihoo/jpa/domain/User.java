package com.baihoo.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户实体
 * @author Administrator
 *
 */
@Entity
public class User {
	/**
	 * @param id
	 * @param name
	 * @param emil 
	 */
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) //自增策略
	private Long id; // 实体一个唯一标识
	@Column(name="name")
	private String name;
	@Column(name="email")
	private String email;
	
	protected User() { //無參構造函數,設爲protected防止直接使用
		
	}
	public User(Long id , String name, String email) {
		this.id  = id;
		this.name = name;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		
		return  String.format("user[id=%d , name='%s' , email='%s']", id , name , email);
	}
	
}
