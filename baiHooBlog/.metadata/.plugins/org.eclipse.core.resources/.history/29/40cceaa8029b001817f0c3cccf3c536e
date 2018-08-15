package org.baiHoo.db;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.baiHoo.anno.OraFieldName;
import org.baiHoo.anno.OraTableName;
import org.junit.Test;



public class MyOraDbUtils {
	private static DataSource dataSource;
	private static ThreadLocal <Connection>threadLocal = new ThreadLocal<Connection>();
	static{
		// TODO 需要修改的地方
		//String uRL="jdbc:oracle:thin:@192.168.0.99:1521/fdmp";
		//String nAME="fdmp"; 
		//String pASS="cape";
		InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream("config/db.properties");
		Properties prop = new Properties();
		try {
			if(in !=null) {
				prop.load(in);
				String uRL = prop.getProperty("uRL");
				String nAME = prop.getProperty("nAME");
				String pASS = prop.getProperty("pASS");
				System.out.println(uRL +",\t"+ nAME  +",\t"+pASS);
				dataSource = new MyDataSource( uRL,  nAME,  pASS) ;
			}else {
				dataSource = new MyDataSource( "jdbc:oracle:thin:@192.168.0.99:1521/fdmp",  "fdmp",  "cape") ;
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	public DataSource getDatasource(){
		return dataSource;
	}
	public static Connection getConnection(){
		try{
			Connection connection = threadLocal.get();
			if(connection==null){
				connection = dataSource.getConnection();
				connection.setAutoCommit(false);//每次（crud）增删改查都会对应一次事务的开启，为能够更好的为事务过滤器服务!相当于web层是对数据访问层的处理才会开启事务，从而避免事务链接资源无故消耗（）
				threadLocal.set(connection);
			}
			return connection;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public  static void startTranscation(){
		try{
			Connection connection= getConnection();
			connection.setAutoCommit(false);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static void commitTranscation(){
		try{
			Connection connection = getConnection();
			connection.commit();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static void closebleTranscation(){
		try{
			Connection connection =getConnection();
			connection.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			threadLocal.remove();
		}
	}

	/**
	 * 
	 * @param clazz
	 * @return List<java 类字段属性>
	 */
	public static<T> List<String> classFields(Class<T> clazz){
		Field[] fields = clazz.getDeclaredFields();
		List<String> fieldList = new ArrayList<String>();
		for(Field field :fields){
			field.setAccessible(true);
			fieldList.add(field.getName());
		}
		return fieldList;
	}

	/**
	 * 通过map封装Java实体属性字段 ，字段注解Oracle表字段名称
	 * @param clazz
	 * @return Map < java 类属性字段 , java 类字段对应注解Oracle字段名称 >
	 * @throws Exception
	 */
	public static<T> Map<String , String> classOraFieldInMap(Class<T> clazz) throws Exception{
		Field[] fields = clazz.getDeclaredFields();
		Map<String , String> map = new HashMap<String , String>();
		for(Field field :fields){
			field.setAccessible(true);
			//字段注解对象
			OraFieldName oraField = field.getAnnotation(OraFieldName.class);
			//注解对象方法值
			Method annotationMethodOfValue = oraField.getClass().getDeclaredMethod("fieldName", new Class[]{});
			 String value = (String) annotationMethodOfValue.invoke(oraField,new Object[]{});
			 map.put(field.getName(), value);
		}
		return map;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return Map < java 类字段对应注解Oracle字段名称 , java 类属性字段 >
	 * @throws Exception
	 */
	public static<T> Map<String , String> oraClassFieldInMap(Class<T> clazz) throws Exception{
		Field[] fields = clazz.getDeclaredFields();
		Map<String , String> map = new HashMap<String , String>();
		for(Field field :fields){
			field.setAccessible(true);
			//字段注解对象
			OraFieldName oraField = field.getAnnotation(OraFieldName.class);
			//注解对象方法值
			Method annotationMethodOfValue = oraField.getClass().getDeclaredMethod("fieldName", new Class[]{});
			 String value = (String) annotationMethodOfValue.invoke(oraField,new Object[]{});
			 map.put(value,field.getName());
		}
		return map;
	}
	/**
	 * 
	 * 通过map对象封装Oracle表字段，对应Java实体字段值
	 * @param bean
	 * @return map < Oracle字段名称  ,  对应Java字段名称的值>
	 */
	@SuppressWarnings("all")
	public static<T> Map<String , String> getOraValInMap(T bean){
		
		Map<String , String> oravalmap = new HashMap<String , String>();
		try {
			//获取对象类
			Class clazz = bean.getClass();
			Map<String, String> map = classOraFieldInMap(clazz);
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; propertyDescriptors != null
					&& i < propertyDescriptors.length; i++) {
				Method getMethod = propertyDescriptors[i].getReadMethod();
				String methodName = getMethod.getName();
				Method classMethod = clazz.getDeclaredMethod(methodName,
						new Class[] {});
				classMethod.setAccessible(true);
				Object value = classMethod.invoke(bean, new Object[] {});
				if (value != null) {
					Pattern pattern = Pattern.compile("get");
					Matcher matcher = pattern.matcher(methodName);
					if(matcher.find()) {//匹配关键字get
						Class<?> returnType = classMethod.getReturnType();
						String fieldName = initFirstLowerCase(matcher.replaceAll("").trim());//替换为空
						if("java.util.Date".equals(returnType.getName())){
							Date date = (java.util.Date)value;
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String dateString = formatter.format(date);
							dateString = "to_date('"+dateString+"','yyyy-MM-dd HH24:mi:ss')";
							oravalmap.put(map.get(fieldName), dateString);
						}else if ("java.lang.String".equals(returnType.getName())) {
							String str = (java.lang.String)value;
							oravalmap.put(map.get(fieldName), str);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oravalmap;
	}
	/**
	 * 获取表名称
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static  String getTableName(Object bean) throws Exception{
		//字段注解对象
		OraTableName oraTable = bean.getClass().getAnnotation(OraTableName.class);
		//注解对象方法值
		Method annotationMethodOfValue = oraTable.getClass().getDeclaredMethod("tableName", new Class[]{});
		String tableName = (String) annotationMethodOfValue.invoke(oraTable,new Object[]{});
		return tableName;
	}
	/**
	 * 通过表明获取Oracle主键
	 * @param tableName
	 * @return
	 * @throws SQLException 
	 */
	public static String getPrimaryKey(String tableName) throws SQLException {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT CU.*, C.DATA_TYPE ");	
		sql.append(" FROM USER_CONS_COLUMNS CU, USER_CONSTRAINTS AU, USER_TAB_COLUMNS C ");		  
		sql.append(" WHERE CU.CONSTRAINT_NAME = AU.CONSTRAINT_NAME ");		 
		sql.append(" AND C.COLUMN_NAME = CU.COLUMN_NAME ");		   
		sql.append(" AND C.TABLE_NAME = CU.TABLE_NAME ");		   
		sql.append(" AND AU.CONSTRAINT_TYPE = 'P' ");				  
		sql.append(" AND AU.TABLE_NAME = ");		   
		sql.append(" '"+tableName+"' ");
		PreparedStatement  pStemt = getConnection().prepareStatement(sql.toString());
		ResultSet rst = pStemt.executeQuery();
		while (rst.next()) {
			return rst.getString("COLUMN_NAME");
		}
		commitTranscation();
		closebleTranscation();
		return "";
	}
	/**
	 * 
	 * @return
	 */
	@Test
	public static String generateUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
  public static String initFirstLowerCase(String str) {
    	if(str!=null && !str.trim().equals("")) {
    		char[] ch = str.toCharArray();
            if (ch[0] >= 'A' && ch[0] <= 'Z')
                ch[0] = (char) (ch[0] + 32);
            return new String(ch);
    	}else {
    		return "";
    	}
    }
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
public static String initFirstUpperCase(String str) {
  	if(str!=null && !str.trim().equals("")) {
  		char[] ch = str.toCharArray();
          if (ch[0] >= 'a' && ch[0] <= 'z')
              ch[0] = (char) (ch[0] - 32);
          return new String(ch);
  	}else {
  		return "";
  	}
  }
} 
