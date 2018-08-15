package org.baiHoo.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class MyDataSource  implements DataSource {
    
    private  String URL ="jdbc:oracle:thin:@192.168.1.8:1521/eStoreDb";    
    private  String NAME = "baiHoo";    
    private  String PASS = "root";    
    private  static String DRIVER ="oracle.jdbc.driver.OracleDriver";   
       //连接池 
      //private static LinkedList<Connection> pool = (LinkedList<Connection>) Collections.synchronizedList(new LinkedList<Connection>()); 
       private static LinkedList<Connection> pool = new LinkedList<Connection>(); 
       private static MyDataSource instance = new MyDataSource(); 

       static { 
    	   		//InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream("config/db.properties");
   				//Properties prop = new Properties();
               try { 
                       Class.forName(DRIVER); 
               } catch (Exception e) { 
                       e.printStackTrace();
               } 
       } 

       private MyDataSource() { 
    	   
       } 
        
      public MyDataSource(String uRL, String nAME, String pASS) {
		super();
		URL = uRL;
		NAME = nAME;
		PASS = pASS;
	}

	/** 
        * 获取数据源单例 
        * 
        * @return 数据源单例 
        */ 
       public MyDataSource instance() { 
               if (instance == null) instance = new MyDataSource(); 
               return instance; 
       } 

       /** 
        * 获取一个数据库连接 
        * 
        * @return 一个数据库连接 
        * @throws SQLException 
        */ 
       public Connection getConnection() throws SQLException { 
               synchronized (pool) { 
                       if (pool.size() > 0) return pool.removeFirst(); 
                       else return makeConnection(); 
               } 
       } 
       /** 
        * 连接归池 
        * 
        * @param conn 
        */ 
       public static void freeConnection(Connection conn) { 
               pool.addLast(conn); 
       } 

       private Connection makeConnection() throws SQLException { 
               return DriverManager.getConnection(URL, NAME, PASS); 
       } 

       public Connection getConnection(String username, String password) throws SQLException { 
               return DriverManager.getConnection(URL, username, password); 
       } 

       public PrintWriter getLogWriter() throws SQLException { 
               return null; 
       } 

       public void setLogWriter(PrintWriter out) throws SQLException { 

       } 

       public void setLoginTimeout(int seconds) throws SQLException { 

       } 

       public int getLoginTimeout() throws SQLException { 
               return 0; 
       } 

       public <T> T unwrap(Class<T> iface) throws SQLException { 
               return null; 
       } 

       public boolean isWrapperFor(Class<?> iface) throws SQLException { 
               return false; 
       }

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	} 

}
