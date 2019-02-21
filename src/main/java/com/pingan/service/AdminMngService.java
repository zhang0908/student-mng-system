package com.pingan.service;

import com.pingan.base.AdminMngDAO;
import com.pingan.util.DBOperation;

public class AdminMngService {
	
	private static DBOperation dbOper = DBOperation.getInstance();
	
	private AdminMngDAO adminDAO = new AdminMngDAO(dbOper);

	/**
	 * 1检查用户名和密码
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public boolean checkAdminLogin(String userName, String pwd) {
		
		return adminDAO.checkUsernameAndPwd(userName, pwd);
		
	}

}
