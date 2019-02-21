package com.pingan.service;

import com.pingan.base.StudentMngDAO;
import com.pingan.entity.StudentEntity;
import com.pingan.util.DBOperation;

public class StudentMngService {
	
	private static DBOperation dbOper = DBOperation.getInstance();
	
	private StudentMngDAO studentDAO = new StudentMngDAO(dbOper);
	
	// 管理员用户表
	String sqlCrtAdminTbl = "create table if not exists adminTbl("
			+ "id int primary key,"
			+ "name varchar(32),"
			+ "username varchar(32),"
			+ "password varchar(32))";
	
	// 检查DB是否已初始化
	String sqlChkExistDb = "select 1 from adminTbl";
	
	// 插入默认管理员admin/admin
	String sqlIntDefaultAdmin = "insert into adminTbl(id, name, username, password) values(1, '管理员', 'admin', 'admin')";
	
	// 创建student信息表
	String sqlCrtStudentTbl = "create table if not exists student("
			+ "id int primary key,"
			+ "sno varchar(16),"
			+ "name varchar(32),"
			+ "score varchar(32),"
			+ "department varchar(32),"
			+ "email varchar(32),"
			+ "tel varchar(32))";
	
	String sqlDropStudentTbl = "drop table student";
	
	String sqlDropAdminTbl = "drop table adminTbl";

	/**
	 * 1数据库初始化
	 */
	public void initDbTables() {
		
//		dbOper.exeute(sqlDropStudentTbl);
//		
//		dbOper.exeute(sqlDropAdminTbl);
		
		// 如果admin表已经存在，则数据库已经初始化过，无需再次初始化，支持return返回
		if (!dbOper.exeute(sqlChkExistDb)) {
			
			// 初始化管理员adminTbl表
			dbOper.exeute(sqlCrtAdminTbl);
			
			// 插入默认管理员账户admini/admin
			dbOper.exeute(sqlIntDefaultAdmin);
			
			// 创建student信息表
			dbOper.exeute(sqlCrtStudentTbl);
			
		}
	}

	/**
	 * 分页查询
	 * @param currPageNum
	 * @return
	 */
	public String[][] qryStudentList(int currPageNum) {
		
		return studentDAO.qryStudentList(currPageNum);
		
	}

	public String[][] queryByName(String param) {
		
		return studentDAO.queryStudentByName(param);
		
	}

	public boolean addNewStudent(StudentEntity stu) {
		
		return studentDAO.add(stu);
		
	}

	public void deleteStudent(String sname, String sno) {
		
		studentDAO.delete(sname, sno);
		
	}

	public boolean modifyStudentInfo(StudentEntity stu) {
		
		return studentDAO.update(stu);
		
	}

	public StudentEntity qryStudentInfo(String sname, String sno) {
		
		return studentDAO.queryStudentByNameAndNo(sname, sno);
		
	}

	public boolean qryStudentBySno(String sno) {
		
		return studentDAO.queryStudentBySno(sno);
		
	}

}
