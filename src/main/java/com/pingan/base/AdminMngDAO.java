package com.pingan.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pingan.util.DBOperation;

/**
 * 检查管理员登陆用户名和密码
 * @author 56453
 *
 */
public class AdminMngDAO {
	
	private DBOperation dbOper;
	
	public AdminMngDAO(DBOperation dbOper) {
		
		this.dbOper = dbOper;
		
	}

	/**
	 * 检查用户名和密码，和DB admin表中数据核对 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUsernameAndPwd(String username, String password) {
		
		boolean result = false;
		
		String sql = "select * from adminTbl where username=? and password=?";
		
		String[] param = { username, password };
		
		ResultSet rs = null;
		
		try {
			
			rs = dbOper.executeQuery(sql, param);
			
			if (rs.next()) {
				
				result = true;
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if (rs != null) {
					
					rs.close();
					
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		return result;
		
	}

}
