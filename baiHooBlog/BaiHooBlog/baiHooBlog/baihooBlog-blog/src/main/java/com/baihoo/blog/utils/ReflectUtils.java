package com.baihoo.blog.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 * @author Administrator
 *
 */
public class ReflectUtils {
	/**
	 * 修改静态属性字段的值
	 * 
	 * @param field 
	 * @param newValue
	 * @throws Exception
	 */
	public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}
