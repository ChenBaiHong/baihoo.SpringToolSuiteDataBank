package com.baihoo.blog.db.oradb;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 *<p>类名称:DataObjectUtils.java </p>
 *<p>类说明: 
 *
 *
 *</p>
 *
 * @author baiHoo.chen
 * @date 2018年6月17日
 */
public class DataObject {
	private Class<? extends Object>  clazz ;
	private Object bean;
	private String tableName;
	private String primaryKey;
	private Map<String , String> oravalmap;
	private Map<String , String> claOraMap;
	private Map<String , String> oraClaMap;
	
	public DataObject(String entityFullName) {
		 try {
			this.clazz = Class.forName(entityFullName);
			this.bean=clazz.newInstance();
			//通过map对象封装Oracle表字段，对应Java实体字段值
			this.oravalmap = BaiDbUtils.getOraValInMap(bean);
			//通过map封装Java实体属性字段 ，字段注解Oracle表字段名称
			this.claOraMap = BaiDbUtils.classOraFieldInMap(clazz);
			this.oraClaMap = BaiDbUtils.oraClassFieldInMap(clazz);
			//表名称
			this.tableName=BaiDbUtils.getTableName(bean);
			//表主键字段
			this.primaryKey=BaiDbUtils.getOraPrimaryKey(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public DataObject (Class<? extends Object> clazz) {
		try {
			this.clazz=clazz;
			this.bean=clazz.newInstance();
			//通过map对象封装Oracle表字段，对应Java实体字段值
			this.oravalmap = BaiDbUtils.getOraValInMap(bean);
			//通过map封装Java实体属性字段 ，字段注解Oracle表字段名称
			this.claOraMap = BaiDbUtils.classOraFieldInMap(clazz);
			this.oraClaMap = BaiDbUtils.oraClassFieldInMap(clazz);
			//表名称
			this.tableName=BaiDbUtils.getTableName(bean);
			//表主键字段
			this.primaryKey=BaiDbUtils.getOraPrimaryKey(tableName);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public DataObject (Object bean) {
		try {
			this.bean=bean;
			this.clazz =bean.getClass();
			//通过map对象封装Oracle表字段，对应Java实体字段值
			this.oravalmap = BaiDbUtils.getOraValInMap(bean);
			//通过map封装Java实体属性字段 ，字段注解Oracle表字段名称
			this.claOraMap = BaiDbUtils.classOraFieldInMap(clazz);
			this.oraClaMap = BaiDbUtils.oraClassFieldInMap(clazz);
			//表名称
			this.tableName=BaiDbUtils.getTableName(bean);
			//表主键字段
			this.primaryKey=BaiDbUtils.getOraPrimaryKey(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return
	 */
	public Map<String , String> getOravalmap() {
		return this.oravalmap;
	}
	/**
	 * 
	 * @return
	 */
	public String getTableName() {
		return this.tableName;
	}
	/**
	 * 
	 * @return
	 */
	public String getPrimaryKey() {
		return this.primaryKey;
	}
	/**
	 * 
	 * @return
	 */
	public Class<? extends Object>  getBeanClazz(){
		return this.clazz;
	}
	public Object getBean() {
		return this.bean;
	}
	/**
	 * 获取字段的类型
	 * @param propertyName ,传值是Java属性字段
	 * @return
	 * @throws Exception
	 */
	public  Type getPropertyType(String propertyName) throws Exception {
		Field field = clazz.getDeclaredField(propertyName);
		Type returnType = field.getType();
		return returnType;
	}
	/**
	 * 获取字段的类型
	 * @param propertyName ,传值是Java属性字段
	 * @return
	 * @throws Exception
	 
	public  Type getPropertyType(String propertyName) throws Exception {
		Field field = clazz.getField(propertyName);
		Type returnType = field.getType();
		return returnType;
	}
	*/
	/**
	 * 获取字段的类型
	 * @param propertyName ,传值是Oracle表字段
	 * @return
	 * @throws Exception
	 */
	public  Type getPropertyTypeByOra(String propertyName) throws Exception {
		String proName = oraClaMap.get(propertyName); 
		Field field = clazz.getDeclaredField(proName);
		Type returnType = field.getType();
		return returnType;
	}
	/**
	 * 
	 * @param propertyName
	 * @return
	 */
	public boolean isOraProperty(String propertyName) {
		 if(oraClaMap.get(propertyName)!=null) {
			 return true;
		 }else {
			 return false;
		 }
	}
	/**
	 * 类对象设置值
	 * @param propertyName
	 * @param value
	 * @throws Exception
	 */
	public void set(String propertyName , Object value) throws Exception {
		String proName = oraClaMap.get(propertyName); //是否传值是Oracle表字段，如果是就取对应Java类字段
		if(proName !=null && !"".equals(proName.trim())) {// oraClaMap取值是Java类字段不空
			this.oravalmap.put(propertyName, proObJvalConverStr(proName,value));
		}else {//否则传值就是Java类字段
			this.oravalmap.put(claOraMap.get(propertyName), proObJvalConverStr(propertyName,value));
			proName = propertyName;
		}
		proName = "set"+BaiDbUtils.initFirstUpperCase(proName);
		value.getClass().getGenericSuperclass().getClass();
		Method classMethod = clazz.getDeclaredMethod(proName,new Class[] {value.getClass()});
		classMethod.invoke(bean, new Object[] {value});
		
	}
	/**
	 * 获取类对象值
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public Object get(String propertyName) throws Exception {
		String proName = oraClaMap.get(propertyName); //是否传值是Oracle表字段，如果是就取对应Java类字段
		if(proName==null || "".equals(proName) ) {//
			proName = propertyName;
		}
		proName = "get"+BaiDbUtils.initFirstUpperCase(proName);
		Method classMethod = clazz.getDeclaredMethod(proName,new Class[] {});
		return classMethod.invoke(bean, new Object[] {});
	}
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private String proObJvalConverStr(String propertyName , Object value) throws Exception  {
		Type type = getPropertyType(propertyName);
		String typeName = type.toString();
		Pattern pattern = Pattern.compile("class");
		Matcher matcher = pattern.matcher(typeName);
		if(matcher.find()) {
			typeName = matcher.replaceAll("").trim();
		}
		if (typeName.equals("java.lang.Integer") 
				|| typeName.equals("int")
				|| typeName.equals("java.lang.Boolean")
				|| typeName.equals("boolean")
				|| typeName.equals("java.lang.Float")
				|| typeName.equals("float")
				|| typeName.equals("java.lang.Long")
				|| typeName.equals("long")
				|| typeName.equals("java.lang.Double")
				|| typeName.equals("double")
				|| typeName.equals("java.lang.Byte")
				|| typeName.equals("byte")) {
			return value+"";
		} else if (typeName.equals("java.util.Date")) {
			Date date = (java.util.Date)value;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(date);
			return "to_date('"+dateString+"','yyyy-MM-dd HH24:mi:ss')";
		} else {
			return value+"";
		}
	}
}
