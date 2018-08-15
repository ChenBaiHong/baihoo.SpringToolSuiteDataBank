package org.baiHoo.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.baiHoo.anno.OraTableName;

import java.util.Set;
import java.util.Vector;

public class OraQueryRunner {
	
	@Override
	public String toString() {
		return "MyQueryRunner []";
	}
	/**
	 * 操作删改
	 * @param connection
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	public void update(Connection connection , String sql , Object [] params) throws SQLException{
		PreparedStatement preparedStatement = null;
		preparedStatement = connection.prepareStatement(sql);
		for(int i = 0 ; i<params.length ; i++){
			preparedStatement.setObject(i+1, params[i]);
		}
		preparedStatement.executeUpdate();
	}
	/**
	 * 操作查询
	 * @param connection
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public <T> T queryBean(Connection connection , String sql , Object [] params, Class <T> clazz) throws SQLException{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		for(int i = 0 ; i<params.length ; i++){
			preparedStatement.setObject(i+1, params[i]);
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		HandleResultSet handleBean = new HandleBean(clazz);
		return handleBean.getBean(resultSet);
	}
	public <T> T expendTempateBean(T bean , Class <T> clazz) {
		
		return null;
	}
	/**
	 * 
	 * @param connection
	 * @param sql
	 * @param params
	 * @param handleBean
	 * @return
	 * @throws SQLException
	 */
	public Object queryBean(Connection connection , String sql , Object [] params, HandleBean handleBean) throws SQLException{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		for(int i = 0 ; i<params.length ; i++){
			preparedStatement.setObject(i+1, params[i]);
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		return handleBean.getBean(resultSet);
	}
	public Object queryBean(Connection connection , String sql ,Class clazz) throws SQLException{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		HandleResultSet handleBean = new HandleBean(clazz);
		return handleBean.getBean(resultSet);
	}
	/**
	 * 查询出一个结果集的Bean对象
	 * @param sql
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public <T>Vector<T> queryBeanVector(Connection connection , String sql ,  Class<T> clazz) throws SQLException{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		HandleResultSet handleBean = new HandleBean(clazz);
		return handleBean.getBeanVector(resultSet);
		
	}
	/**
	 * 查询数据的长度 ，带条件的
	 * @param connection
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Integer queryScaler(Connection connection , String sql ,Object [] params) throws SQLException{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		for(int i = 0 ; i<params.length ; i++){
			preparedStatement.setObject(i+1, params[i]);
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		HandleResultSet handleBean = new HandleBean();
		return handleBean.getQueryScaler(resultSet);
	}
	/**
	 * 查询数据的长度 ，不带条件的
	 * @param connection
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public  Integer queryScaler(Connection connection , String sql) throws SQLException{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		HandleResultSet handleBean = new HandleBean();
		return handleBean.getQueryScaler(resultSet);
	}
	
	public Integer insertBean(Connection connection , Object  bean) throws Exception {
			Map<String , String> map = MyOraDbUtils.getOraValInMap(bean);
			//字段注解对象
			OraTableName oraTable = bean.getClass().getAnnotation(OraTableName.class);
			//注解对象方法值
			Method annotationMethodOfValue = oraTable.getClass().getDeclaredMethod("tableName", new Class[]{});
			String tableName = (String) annotationMethodOfValue.invoke(oraTable,new Object[]{});
			StringBuffer sb = new StringBuffer("insert into "+tableName);
			sb.append("(");
			Set<Entry<String, String>> entrySet = map.entrySet();
			Iterator<Entry<String, String>> iterator = entrySet.iterator();
			String keys = "";
			String values = "";
			while(iterator.hasNext()) {
				Entry<String, String> entry =  iterator.next();
				keys+=entry.getKey()+",";
				if(entry.getValue().contains("to_date")) {
					values+=""+entry.getValue()+",";
				}else {
					values+="'"+entry.getValue()+"',";
				}
			}
			if("".equals(keys) || "".equals(values)) {
				return 0;
			}
			keys = keys.substring(0, keys.length()-1);
			values = values.substring(0,values.length()-1);
			sb.append(keys);
			sb.append(")");
			sb.append("values");
			sb.append("(");
			sb.append(values);
			sb.append(")");
			System.out.println(sb.toString());
			Statement statement = connection.createStatement();
			boolean isExe = statement.execute(sb.toString());
		return isExe?1:0;
	}
	public Integer updateBean(Connection connection , String sql) throws SQLException {
		System.out.println(sql);
		Statement statement = connection.createStatement();
		boolean isExe = statement.execute(sql);
		return isExe?1:0;
	}
	
	public Integer insertBatchBean(Connection connection , List<? extends Object> beanList) {
		for(int i = 0;i<beanList.size();i++) {
			try {
				insertBean(connection ,   beanList.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	public Integer updateBatchBean(Connection connection , List beanList) {
		return 1;
	}
	/*****************************************************************************************
									内部类操作接口 
										反射对象获取结果集   
										继承接口类      
									这是一种设计模式 策略模式
	****************************************************************************************/
	//一个结果集接口
	interface HandleResultSet{
		/**
		 * 获取class 对象一个完整的封装对象
		 * @param resultSet
		 * @return
		 */
		public <T extends Object >T getBean(ResultSet resultSet);
		/**
		 * 获取class对象一个完整的封装list集合对象
		 * @param resultSet
		 * @return
		 */
		public Vector getBeanVector(ResultSet resultSet);
		/**
		 * 获取数据的长度
		 * @param resultSet
		 * @return
		 */
		public Integer getQueryScaler(ResultSet resultSet);
	}
	//继承结果集接口操作bean , 可以是static
	static class HandleBean implements HandleResultSet{
		private Class  clazz ;
		private List<String> classFields =null;
		private Map<String , String> claOraMap = null;
		private Map<String , String> oraClaMap = null;
		public HandleBean(Class  clazz){
			this.clazz=clazz;
			classFields = MyOraDbUtils.classFields(clazz);
			try {
				claOraMap = MyOraDbUtils.classOraFieldInMap(clazz);
				oraClaMap = MyOraDbUtils.oraClassFieldInMap(clazz);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public HandleBean() {
			super();
		}
		
		public <T extends Object >T getBean(ResultSet resultSet) {
			try{
				if(!resultSet.next()){
					return null;
				}
				//创建封装结果集到bean
				T bean = (T) clazz.newInstance();//如果对象不被Class实例化,那么这个实例出来的bean对象就是空壳，可能会抛出空指针
				//得到结果集元数据
				ResultSetMetaData metaData = resultSet.getMetaData();
				for(int i = 0 ; i<metaData.getColumnCount() ; i++){
					String name = metaData.getColumnName(i+1);
					Object value = resultSet.getObject(name);//来自数据库的值
					Field field = bean.getClass().getDeclaredField(oraClaMap.get(name));
					if(field!=null){
						field.setAccessible(true);
						if("number".equalsIgnoreCase(metaData.getColumnTypeName(i + 1)) ){
							if(value==null) {
								field.set(bean, "");
							}else {
								BigDecimal  deci = (BigDecimal)value;
								field.set(bean, deci.toString());
							}
						}else {
							field.set(bean, value);
						}
					}
				}
				return bean;
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		public Vector  getBeanVector(ResultSet resultSet) {
			Vector vector = new Vector();
			try {
				ResultSetMetaData metaData = resultSet.getMetaData();
				Vector column = new Vector();
				while(resultSet.next() && metaData.getColumnCount()>0){
					// 创建封装结果集到bean
					Object bean = clazz.newInstance();
					// 得到结果集元数据
					for (int i = 0; i < metaData.getColumnCount(); i++) {
						String name = metaData.getColumnName(i + 1);
						Object value = resultSet.getObject(name);//来自数据库的值
						
						Field field = bean.getClass().getDeclaredField(oraClaMap.get(name));
						if(field!=null){
							field.setAccessible(true);
							if("number".equalsIgnoreCase(metaData.getColumnTypeName(i + 1)) ){
								BigDecimal  deci = (BigDecimal)value;
								field.set(bean, deci.toString());
							}else {
								field.set(bean, value);
							}
						}
					}
					//循环一列后形成数据封装到bean的对象，添加到封装列的集合中去
					column.add(bean);
				}
				vector.add(column);
				return vector;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		public Integer getQueryScaler(ResultSet resultSet) {
			try {
				int count = 0;
				if(resultSet.next()){
					count =resultSet.getInt(1);
				}
				return count;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}

