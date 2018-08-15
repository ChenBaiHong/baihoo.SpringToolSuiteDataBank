package com.baihoo.blog.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

/**
 * 權限對象
 * @author Administrator
 *
 */
@DynamicUpdate
@Entity(name="AUTHORITY")
public class Authority implements GrantedAuthority {


	/**
	 * @param id					主鍵， 自增长策略
	 * @param name			用户的唯一标识
	 * @param createTime 	創建時間
	 * @param updateTime 最後更新時間
	 * @param description 	描述
	 * @param version 		版本
	 */
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="ID")
	private Long id; 

	@Column(name="NAME" , nullable = false)
	private String name;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="VERSION")
	private Long version;
	
	/**
	 * 對多對，映射出處來自<br>
	 * com.baihoo.blog.domain.User.authorities
	 */
//	@ManyToMany(mappedBy="authorities")
//	private List<User> users;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String getAuthority() {
		//權限名稱
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}
//
//	public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", name=" + name + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", description=" + description + ", version=" + version + "]";
	}
}
