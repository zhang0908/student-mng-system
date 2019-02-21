package com.pingan.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pingan.entity.StudentEntity;
import com.pingan.util.DBOperation;

/**
 * 
 */
public class StudentMngDAO {
	
	private final int fieldNum = 9;
	private final int showNum = 15;
	
	private DBOperation dbOper;
	
	public StudentMngDAO (DBOperation dbOper) {
		
		this.dbOper = dbOper;
		
	}

	/**
	 * 修改学生信息
	 * @param stu
	 * @return
	 */
	public boolean update(StudentEntity stu) {
		
		try {
			
			String sql = "update student set score=?,department=?,email=?,tel=? where name=? and sno=?";
			
			String[] param = { stu.getScore(), stu.getDepartment(), stu.getEmail(), stu.getTel(), stu.getName(), stu.getSno() };
			
			return dbOper.executeUpdate(sql, param);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return false;
	}

	/**
	 * 删除学生信息
	 * @param stu
	 * @return
	 */
	public boolean delete(String sname, String sno) {
		
		String sql = "delete from student where name=? and sno=?";
		
		String[] param = { sname, sno };
		
		return dbOper.executeUpdate(sql, param);
		
	}

	/**
	 * 添加学生信息
	 * @param stu
	 * @return
	 */
	public boolean add(StudentEntity stu) {
		try {
			
			String sql = "insert into student(name,sno,score,department,email,tel) values(?,?,?,?,?,?)";
			
			String[] param = { stu.getName(), stu.getSno(), stu.getScore(), stu.getDepartment(), stu.getEmail(), stu.getTel() };
			
			return dbOper.executeUpdate(sql, param);
			
		} catch (Exception se) {
			
			se.printStackTrace();
			
		}
		return false;
	}

	/**
	 * 根据学生名称查询
	 * @param name
	 * @return
	 */
	public String[][] queryStudentByName(String name) {
		
		String[][] result = null;
		
		List<StudentEntity> studentList = new ArrayList<StudentEntity>();
		
		int i = 0;
		
		String sql = "select * from student where name like ?";
		
		// 模糊查询
		String[] param = { "%" + name + "%" };
		
		ResultSet rs = dbOper.executeQuery(sql, param);
		
		try {
			
			while (rs.next()) {
				
				studentList.add(assembleStudentEntity(rs, i++));
				
			}
			
			if (studentList.size() > 0) {
				
				result = new String[studentList.size()][fieldNum];
				
				for (int j = 0; j < studentList.size(); j++) {
					
					buildResult(result, studentList, j);
					
				}
				
			}
			
		} catch (Exception se) {
			
			se.printStackTrace();
			
		} finally {
			
			if (rs != null) {
				
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}

		return result;
	}

	/**
	 * 分页查询
	 * @param pageNum
	 * @return
	 */
	public String[][] qryStudentList(int pageNum) {
		String[][] result = null;
		
		List<StudentEntity> stus = new ArrayList<StudentEntity>();
		
		int i = 0;
		
		int beginNum = (pageNum - 1) * showNum;
		
		String sql = "select * from student limit ?,?";
		
		Integer[] param = { beginNum, showNum };
		
		ResultSet rs = dbOper.executeQuery(sql, param);
		
		try {
			
			while (rs.next()) {
				
				stus.add(assembleStudentEntity(rs, i++));
				
			}
			
			if (stus.size() > 0) {
				
				result = new String[stus.size()][fieldNum];
				
				for (int j = 0; j < stus.size(); j++) {
					
					buildResult(result, stus, j);
					
				}
				
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * 封装StudentEntity
	 * @param rs
	 * @param list
	 * @param i
	 * @throws SQLException
	 */
	private StudentEntity assembleStudentEntity(ResultSet rs, int no) throws SQLException {
		
		StudentEntity stu = new StudentEntity();
		
		stu.setId(no + 1);
		stu.setName(rs.getString("name"));
		stu.setSno(rs.getString("sno"));
		stu.setScore(rs.getString("score"));
		stu.setDepartment(rs.getString("department"));
		stu.setEmail(rs.getString("email"));
		stu.setTel(rs.getString("tel"));
		
		return stu;
	}

	/**
	 * 集合转数组
	 * @param result
	 * @param stus
	 * @param j
	 */
	private void buildResult(String[][] result, List<StudentEntity> stus, int j) {
		StudentEntity stu = stus.get(j);
		result[j][0] = String.valueOf(stu.getId());
		result[j][1] = stu.getName();
		result[j][2] = stu.getSno();
		result[j][3] = stu.getScore();
		result[j][4] = stu.getDepartment();
		result[j][5] = stu.getEmail();
		result[j][6] = stu.getTel();
	}

	/**
	 * 根据姓名和学号查询学生信息
	 * @param sname
	 * @param sno
	 * @return
	 */
	public StudentEntity queryStudentByNameAndNo(String sname, String sno) {
		
		String sql = "select * from student where name = ? and sno = ?";
		
		String[] param = { sname, sno };
		
		ResultSet rs = dbOper.executeQuery(sql, param);
		
		try {
			
			if (rs.next()) {
				
				return assembleStudentEntity(rs, 0);
				
			}
			
		} catch (Exception se) {
			
			se.printStackTrace();
			
		} finally {
			
			if (rs != null) {
				
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}

		return null;
	}

	/**
	 * 根据学号查询学生信息
	 * @param sno
	 * @return
	 */
	public boolean queryStudentBySno(String sno) {
		
		String sql = "select * from student where sno = ?";
		
		String[] param = { sno };
		
		ResultSet rs = dbOper.executeQuery(sql, param);
		
		try {
			
			if (rs.next()) {
				
				return true;
				
			}
			
		} catch (Exception se) {
			
			se.printStackTrace();
			
		} finally {
			
			if (rs != null) {
				
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}

		return false;
	}


}
