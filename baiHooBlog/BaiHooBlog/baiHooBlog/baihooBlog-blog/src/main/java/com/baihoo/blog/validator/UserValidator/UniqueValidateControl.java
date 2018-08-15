package com.baihoo.blog.validator.UserValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.baihoo.blog.anno.UsernameUnique;
import com.baihoo.blog.exception.CustomException;
import com.baihoo.blog.utils.ReflectUtils;

/**
 * 控制是否对字段进行唯一校验
 * @author Administrator
 *
 */
public class UniqueValidateControl<T> {
	/**
	 * @param bean 实体表
	 * @param uniquefield  实体表唯一约束字段
	 */
	private T bean ; 
	private String uniquefield;
	/**
	 * 构造函数私有化
	 */
	private UniqueValidateControl() {
		
	}
	/**
	 * 
	 * @param bean  实体表
	 * @param uniquefield 实体表唯一约束字段
	 */
	public UniqueValidateControl(T bean, String uniquefield) {
		super();
		this.bean = bean;
		this.uniquefield = uniquefield;
	}
	/**
	 * 设置是否唯一约束
	 * @param isvalidate
	 * @throws CustomException
	 */
	public void setIsvalidate(boolean isvalidate) throws CustomException {
		try {
			Field field = bean.getClass().getDeclaredField(this.uniquefield);
			field.setAccessible(true);
			UsernameUnique anno = field.getAnnotation(UsernameUnique.class);
			if(anno == null ) {
				return ;
			}else {
				//注解对象通过它的属性字段设置值
				//注解中的常量，默认是public static final 修饰类型
				Field annoField = anno.getClass().getField("isvalidate");
				ReflectUtils.setFinalStatic(annoField, isvalidate);
				System.out.println(annoField.get(anno));
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new CustomException("唯一约束控制，传参有误！！！");
		}
	}
}
