package com.baihoomuch.cloud.dto;

import java.math.BigDecimal;

/**
 * Description: microservice-simple-provider-user
 * auther Administrator on 2018/7/5
 */
public class UserDto {
        /**
         * @param objectid 唯一识别ID , 并自动增长
         * @param username 用户名称
         * @Param password 用户密码
         * @param name 真实名称
         * @param age 年龄
         * @param balance  账目清单
         */

        private Long  objectid;
        private String username;
        private String password;
        private String name;
        private Integer age;
        private BigDecimal balance;
        
		public Long getObjectid() {
			return objectid;
		}
		public void setObjectid(Long objectid) {
			this.objectid = objectid;
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
		public void setPassword(String password) {
			this.password = password;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public BigDecimal getBalance() {
			return balance;
		}
		public void setBalance(BigDecimal balance) {
			this.balance = balance;
		}
}
