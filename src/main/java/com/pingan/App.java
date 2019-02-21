package com.pingan;

import com.pingan.service.StudentMngService;
import com.pingan.util.DBOperation;
import com.pingan.view.AdminLoginFrm;

/**
 * 0  学生信息管理系统：
 * 1. 使用图形用户界面；
 * 2. 用数据库建立1或2个学生信息表；（不限使用哪种数据库）
 * 3. 能连接数据库并实现查询、增、删、改等功能。
 *
 */
public class App 
{
	
	public static void main(String[] args) {
		
		// 数据库表初始化
		new StudentMngService().initDbTables();
		
		// 启动登陆界面
		new AdminLoginFrm();
		
		// 释放数据库连接资源
		DBOperation.getInstance().release();
		
	}

}
