package com.pingan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.pingan.entity.StudentEntity;
import com.pingan.service.StudentMngService;

/**
 * 
 * @author 56453
 *
 */
public class NewStudentFrm extends JFrame {
	
	private StudentMngService service;

	private JPanel centerPnl = new JPanel();
	private JPanel southPnl = new JPanel();
	private JButton addBtn = new JButton("添加");
	private JButton exitBtn = new JButton("退出");
	private JTextField nameTfd = new JTextField();
	private JTextField noTfd = new JTextField();
	private JTextField departmentTfd = new JTextField();
	private JTextField scoreTfd = new JTextField();
	private JTextField emailTxt = new JTextField();
	private JTextField telTfd = new JTextField();
	
	// 增加和修改使用同一个frame，通过action来区分
	private String action = "add";

	public NewStudentFrm(StudentMngService service) {
		
		// 控件初始化
		initComponent();
		
		this.service = service;
		
		//控件事件监听器初始化
		initComponentListener();
	}


	public NewStudentFrm(StudentMngService studentMnsgService, String action) {
		
		this(studentMnsgService);
		
		this.action = action;
		
	}


	/**
	 * 
	 */
	private void initComponent() {
		setTitle("录入新学生信息");
		Dimension dim = this.getToolkit().getScreenSize();
		this.setBounds((int)(dim.width * 0.3), (int)(dim.height * 0.3), 650, 400);
		
		centerPnl.setLayout(new GridLayout(7, 1));
		
		JLabel titleLbl = new JLabel("学生信息", JLabel.CENTER);
		centerPnl.add(titleLbl);
		titleLbl.setFont(new Font("字体", Font.PLAIN, 20));
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("          *姓名：", JLabel.RIGHT));
		p1.add(nameTfd);
		nameTfd.setPreferredSize(new Dimension(500, 30));
		centerPnl.add(p1);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.add(new JLabel("          *学号：", JLabel.RIGHT));
		p2.add(noTfd);
		noTfd.setPreferredSize(new Dimension(500, 30));
		centerPnl.add(p2);
		
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(new JLabel("           学分：", JLabel.RIGHT));
		p3.add(scoreTfd);
		scoreTfd.setPreferredSize(new Dimension(500, 30));
		centerPnl.add(p3);
		
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.add(new JLabel("           院系：", JLabel.RIGHT));
		p4.add(departmentTfd);
		departmentTfd.setPreferredSize(new Dimension(500, 30));
		centerPnl.add(p4);
		
		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		p5.add(new JLabel("           邮箱：", JLabel.RIGHT));
		p5.add(emailTxt);
		emailTxt.setPreferredSize(new Dimension(500, 30));
		centerPnl.add(p5);
		
		JPanel p6 = new JPanel();
		p6.setLayout(new FlowLayout(FlowLayout.LEFT));
		p6.add(new JLabel("           电话：", JLabel.RIGHT));
		p6.add(telTfd);
		telTfd.setPreferredSize(new Dimension(500, 30));
		centerPnl.add(p6);

		southPnl.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		addBtn.setFont(new Font("字体", Font.PLAIN, 20));
		southPnl.add(addBtn);
		exitBtn.setFont(new Font("字体", Font.PLAIN, 20));
		southPnl.add(exitBtn);

		this.add(centerPnl, BorderLayout.CENTER);
		this.add(southPnl, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * 控件监听器
	 */
	private void initComponentListener() {
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateStudentInfo()) {
					StudentEntity stu = new StudentEntity();
					assembleStudentEntity(stu);
					
					boolean isSuccess = false;
					
					if ("modify".contentEquals(action)) {
						
						isSuccess = service.modifyStudentInfo(stu);
						
					} else {
						
						//学号不能重复
						if (!service.qryStudentBySno(stu.getSno())) {
							
							isSuccess = service.addNewStudent(stu);
							
						} else {
							
							JOptionPane.showMessageDialog(null, "学号已存在，不能重复。");
							
						}
						
							
					}
					if (isSuccess) {
						clearComponent();
						if (StudentMngFrm.pageNum < 0 || StudentMngFrm.pageNum > 99) {
							StudentMngFrm.pageNum = 1;
						}
						String[][] result = service.qryStudentList(StudentMngFrm.pageNum);
						StudentMngFrm.fillStudentListTbl(StudentMngFrm.jTable, result);
					}
				}
				
				dispose();
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}


	private boolean validateStudentInfo() {
		if ("".equals(nameTfd.getText()) || "".equals(noTfd.getText())) {
			
			JOptionPane.showMessageDialog(this, "姓名和学号为必填信息。");
			
			return false;
		} else {
			return true;
		}
	}

	private void assembleStudentEntity(StudentEntity student) {
		student.setName(nameTfd.getText());
		student.setSno(noTfd.getText());
		student.setScore(scoreTfd.getText());
		student.setDepartment(departmentTfd.getText());
		student.setEmail(emailTxt.getText());
		student.setTel(telTfd.getText());
	}

	private void clearComponent() {
		nameTfd.setText("");
		noTfd.setText("");
		departmentTfd.setText("");
		emailTxt.setText("");
		scoreTfd.setText("");
		telTfd.setText("");
	}


	public void fillStudentInfo(StudentEntity entity) {
		
		nameTfd.setText(entity.getName());
		//姓名和学号不可修改
		nameTfd.setEnabled(false);
		noTfd.setText(entity.getSno());
		//姓名和学号不可修改
		noTfd.setEnabled(false);
		scoreTfd.setText(entity.getScore());
		departmentTfd.setText(entity.getDepartment());
		emailTxt.setText(entity.getEmail());
		telTfd.setText(entity.getTel());
		
		addBtn.setText("修改");
		
	}
}
