package com.baihoo.bootstrap.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用户实体
 * 
 * @author Administrator
 *
 */
@Entity(name="user") // 實體
public class User implements UserDetails, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動增長
	private Long id; // 实体一个唯一标识
	@Size(min = 2, max = 32)
	@Column(name="user_name" , nullable = false, length = 20) // 映射为字段，值不能为空
	private String username;
	@Size(max = 99)
	@Column(name="pass_word" , length = 99)
	private String password;
	@Size(max = 50)
	@Email
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	
	/**
	 * 加入中間表user_authority
	 * 		中間表加入列user_id，參考列為當前主鍵列
	 * 		中間表加入列authority_id， 參考當前表倒置到Authority表的主鍵列
	 */
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", 
						joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
						inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;
	public User() {

	}

	public User(Long id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	/**
	 * 密碼BCrypt加密
	 * @param password
	 */
	public void setPassword(String password) {
        PasswordEncoder  encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(password);
        this.password = encodePasswd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
        for(GrantedAuthority authority : this.authorities){
            simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return simpleAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		//重載默認是false,我們要改成true
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//重載默認是false,我們要改成true
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//重載默認是false,我們要改成true
		return true;
	}

	@Override
	public boolean isEnabled() {
		//重載默認是false,我們要改成true
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", authorities=" + authorities + "]";
	}
}
