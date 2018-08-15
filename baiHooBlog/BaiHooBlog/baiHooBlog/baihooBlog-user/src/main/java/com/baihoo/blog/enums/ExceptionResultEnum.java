package com.baihoo.blog.enums;

/**
 * 
 * @author Administrator
 * @description
 * 		把错误码及错误信息，组装起来统一管理。定义一个业务异常的枚举<br>
 */
public enum ExceptionResultEnum {
	
	UNKONW_ERROR(-1,"未知错误"),
	SUCCESS(0,"成功"),
	ERROR(1,"失败"),
	;
	private Integer statusCode;
	private String message;
	/**
	 * 
	 * @param statusCode 	状态码
	 * @param message		状态消息内容
	 */
	private ExceptionResultEnum(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public String getMessage() {
		return message;
	}
}
