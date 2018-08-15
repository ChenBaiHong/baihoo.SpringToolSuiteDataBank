package com.baihoo.blog.db.mysqldb;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.baihoo.blog.db.mysqldb.runner.QueryRunner;

import java.util.Set;

/**
 * 
 * 
 *<p>类名称:DatabaseUtil.java </p>
 *<p>类说明: 
 *
 *
 *</p>
 *
 * @author baiHoo.chen
 * @date 2018年6月17日
 */
public class DatabaseUtils {
	
	/**
	 * 
	 * @param asname
	 * @param entity
	 * @param template
	 * @return
	 * @throws SQLException 
	 */
	public static DataObject expandEntity(String asname , DataObject template) throws SQLException {
		DataObject result=null;
		try {
			if("default".equals(asname)) {
				Map<String , String> oravalMap = template.getOravalmap();
				StringBuffer sb = new StringBuffer("select * from "+template.getTableName());
				sb.append(" where 1=1 ");
				Set<Entry<String, String>> entrySet = oravalMap.entrySet();
				Iterator<Entry<String, String>> iterator = entrySet.iterator();
				while(iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					if(entry.getValue() !=null && entry.getValue() !="") {
						sb.append(" and ");
						sb.append(entry.getKey()+"= '"+entry.getValue()+"'");
					}
				}
				System.out.println(sb.toString());
				QueryRunner oraRunner = new QueryRunner();
				Object objBean = oraRunner.queryBean(DbUtils.getConnection(), sb.toString(), template.getBeanClazz());
				//获取数据对象
				result = new DataObject(objBean);
			}
		}finally {
			template=null;
			DbUtils.commitTranscation();
			DbUtils.closebleTranscation();
		}
		return result;
	}
	/**
	 * 
	 * @param asname
	 * @param dataEntity
	 * @return
	 * @throws SQLException
	 */
	public static int updateEntity(String asname , DataObject dataEntity) throws SQLException {
		int result = 0;
		try {
			if("default".equals(asname)) {
				Map<String , String> oravalMap = dataEntity.getOravalmap();
				StringBuffer sb = new StringBuffer(" update "+dataEntity.getTableName());
				sb.append(" set ");
				String pkCondition="";
				Set<Entry<String, String>> entrySet = oravalMap.entrySet();
				Iterator<Entry<String, String>> iterator = entrySet.iterator();
				while(iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					if(entry.getValue() !=null && entry.getKey() !=null 
							&& !"".equals(entry.getValue())  && !"".equals(entry.getKey())) {
						if(entry.getKey().equals(dataEntity.getPrimaryKey())) {
							pkCondition = entry.getKey()+"= '"+entry.getValue()+"'";
						}else {
							if(entry.getValue().contains("to_date")) {
								sb.append(entry.getKey()+"= "+entry.getValue()+"");
							}else {
								sb.append(entry.getKey()+"= '"+entry.getValue()+"'");
							}
							sb.append(",");
						}
					}
				}
				String sql = sb.substring(0, sb.length()-1);
				sql += " where 1=1 ";
				if(pkCondition !=null && !pkCondition.equals("")) {
					sql += " and ";
					sql += pkCondition;
				}else {
					return 0;
				}
				QueryRunner oraRunner = new QueryRunner();
				result= oraRunner.updateBean(DbUtils.getConnection(), sql);
			}
		}finally {
			DbUtils.commitTranscation();
			DbUtils.closebleTranscation();
		}

		return result;
	}
	/**
	 * 
	 * @param asname
	 * @param dataEntity
	 * @return
	 * @throws Exception
	 */
	public static int insertEntity(String asname , DataObject dataEntity) throws Exception {
		int result = 0;
		try {
			if("default".equals(asname)) {
				QueryRunner oraRunner = new QueryRunner();
				dataEntity.set(dataEntity.getPrimaryKey(), DbUtils.generateUuid());
				result = oraRunner.insertBean(DbUtils.getConnection(), dataEntity.getBean());
			}
		}finally {
			DbUtils.commitTranscation();
			DbUtils.closebleTranscation();
		}
		return result ;
	}
	public static int insertEntityBatch(String asname , DataObject[] dataEntitys) throws Exception {
		int result = 0;
		
		if("default".equals(asname)) {
			for (DataObject dataObject : dataEntitys) {
				insertEntity("default" , dataObject) ;
			}
			result=1;
		}
		return result ;
	}
}
