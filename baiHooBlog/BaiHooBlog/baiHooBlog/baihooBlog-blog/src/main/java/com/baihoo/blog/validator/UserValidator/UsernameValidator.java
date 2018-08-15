package com.baihoo.blog.validator.UserValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.baihoo.blog.anno.UsernameUnique;
import com.baihoo.blog.db.mysqldb.DataObject;
import com.baihoo.blog.db.mysqldb.DatabaseUtils;
import com.baihoo.blog.domain.User;
import com.baihoo.blog.service.UserService;

/**
 * 針對用戶的校驗器
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class UsernameValidator  implements ConstraintValidator< UsernameUnique, Object>{
	
	@Resource
	private UserService userService;
	/**
	 * @param clazz  			实体class类
	 * @param property		实体方法属性
	 * @param constraintAnnotation  约束注解
	 */
	private Class<? extends Object> clazz;
	private String property;
	private UsernameUnique constraintAnnotation;
	@Override
	public void initialize(UsernameUnique constraintAnnotation) {
		try {
			this.constraintAnnotation = constraintAnnotation;
			Method enMeth = constraintAnnotation.getClass().getDeclaredMethod("entity", new Class[] {});
			enMeth.setAccessible(true);
			this.clazz = (Class<? extends Object>) enMeth.invoke(constraintAnnotation, new Object[] {});
			
			Method proMeth = constraintAnnotation.getClass().getDeclaredMethod("property", new Class[] {});
			proMeth.setAccessible(true);
			this.property = (String) proMeth.invoke(constraintAnnotation, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("username  Unique Validation Init...");
	}
	/**
	 * 是否唯一校验, 默认是要唯一校验
	 * @return
	 * @throws Exception
	 */
	public boolean isValidate() throws Exception {
		/**
		 * 获取是否唯一校验判断值
		 */
		Field isValiField= UsernameUnique.class.getField("isvalidate");
		isValiField.setAccessible(true);
		return (boolean) isValiField.get(constraintAnnotation);
	}

	/**
	 * 用戶名作唯一校驗
	 * @param value
	 * @param context
	 * @return	
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			DataObject userDo = new DataObject(clazz);
			userDo.set(property, value);
			userDo=DatabaseUtils.expandEntity("default", userDo);
			//对象不为空，并且必须要校验
			if(userDo.getBean()!=null && isValidate()==true) {
				return false;
			}else {
				return true;
			}
			//return userService.isUsernameUnique((String)value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
