package com.baihoo.blog.utils;

import javax.validation.ConstraintViolation;
/**
 * 个人封装得静态工具
 * @author Administrator
 *
 */
public class CommonUtils {
	/**
	 * 获取约束校验国际化信息
	 * @param cv
	 * @return
	 */
	public static String getConstraintViolationMessage(ConstraintViolation cv) {
		String message = cv.getMessage();
		message = message.replace("{", "").replace("}", "");
		return message;
	}
	/**
	 * 获取国际化信息
	 * @param message
	 * @return
	 */
	public static String getI18nMessage(String message) {
		message = message.replace("{", "").replace("}", "");
		return message;
	}
}