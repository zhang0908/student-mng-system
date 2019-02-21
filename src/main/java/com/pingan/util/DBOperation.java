package com.pingan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 1数据库操作，单例模式
 * 
 */
public class DBOperation {
	// 懒汉单例模式
	private static DBOperation dbOper;

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public static final String JDBC_URL = "jdbc:sqlite:test.db";
	public static final String JDBC_USERNAME = "test";
	public static final String JDBC_PASSWORD = "test";
	public static final String JDBC_DRIVER = "org.sqlite.JDBC";

	private DBOperation() {
		
		//初始化数据库连接
		initConnection();

	}

	/**
	 * 1单例模式
	 * @return
	 */
	public static DBOperation getInstance() {
		
		if (dbOper == null) {
			
			dbOper = new DBOperation();
			
		}
		
		return dbOper;
	}

	/**
	 * 1创建数据库连接
	 * 1如果connection不存在或已关闭，则重新创建
	 * @return
	 */
	private void initConnection() {
		
		try {
			
			// 如果connection不存在或已关闭，则重新创建
			if (conn == null || conn.isClosed()) {
				
				Class.forName(JDBC_DRIVER);
				
				conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
				
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
	

	/**
	 * 1执行更新UPDATE SQL
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean executeUpdate(String sql, Object[] obj) {
		
		try {
			
			ps = conn.prepareStatement(sql);
			
			//设置SQL参数
			for (int i = 0; i < obj.length; i++) {
				
				ps.setObject(i + 1, obj[i]);
				
			}
			
			int result = ps.executeUpdate();
			
			// 执行结果成功or失败
			return result == 1;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return false;
		
	}

	/**
	 * 1执行查询SQL
	 * @param sql
	 * @param obj
	 * @return
	 */
	public ResultSet executeQuery(String sql, Object[] obj) {
		
		try {
			
			initConnection();
			
			ps = conn.prepareStatement(sql);
			
			//设置SQL参数
			for (int i = 0; i < obj.length; i++) {
				
				ps.setObject(i + 1, obj[i]);
				
			}
			
			rs = ps.executeQuery();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return rs;
		
	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public boolean exeute(String sql) {
		
		Statement statement = null;
		
		try {
			
			statement = conn.createStatement();
			
			statement.execute(sql);
			
			statement.close();
			
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return false;
		
		//資源釋放Statement
		} finally {
			
			if (statement != null) {
				
				try {
					
					statement.close();
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
			}
		}
	}


	/**
	 * 1 数据库连接资源释放
	 */
	public void release() {
		
		try {
			
			if (rs != null) {
				
				rs.close();
				
			}
			
			if (ps != null) {
				
				ps.close();
				
			}
			
			if (conn != null) {
				
				conn.close();
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
