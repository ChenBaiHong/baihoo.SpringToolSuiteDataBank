package com.baihoo.blog.exception;

import com.baihoo.blog.enums.ExceptionResultEnum;

/**
 * 自定义异常类
 * @author Administrator
 *
 */
public class CustomRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param 状态码
	 */
	private Integer statusCode;  

	
	public CustomRuntimeException(ExceptionResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.statusCode = resultEnum.getStatusCode();
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
}
