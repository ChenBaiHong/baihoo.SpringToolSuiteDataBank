package org.baiHoo.db;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
			OraQueryRunner oraRunner = new OraQueryRunner();
			Object objBean = oraRunner.queryBean(MyOraDbUtils.getConnection(), sb.toString(), template.getBeanClazz());
			MyOraDbUtils.commitTranscation();
			MyOraDbUtils.closebleTranscation();
			if(objBean!=null) {
				//获取数据对象
				template=null;
				result = new DataObject(objBean);
			}else {
				result=null;
			}
		}
		return result;
	}
	
	public static int updateEntity(String asname , DataObject dataEntity) throws SQLException {
		int result = 0;
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
			OraQueryRunner oraRunner = new OraQueryRunner();
			result= oraRunner.updateBean(MyOraDbUtils.getConnection(), sql);
			MyOraDbUtils.commitTranscation();
			MyOraDbUtils.closebleTranscation();
		}
		return result;
	}
	public static int insertEntity(String asname , DataObject dataEntity) throws Exception {
		int result = 0;
		if("default".equals(asname)) {
			OraQueryRunner oraRunner = new OraQueryRunner();
			dataEntity.set(dataEntity.getPrimaryKey(), MyOraDbUtils.generateUuid());
			result = oraRunner.insertBean(MyOraDbUtils.getConnection(), dataEntity.getBean());
			MyOraDbUtils.commitTranscation();
			MyOraDbUtils.closebleTranscation();
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
