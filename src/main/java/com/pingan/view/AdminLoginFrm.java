package com.pingan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.pingan.service.AdminMngService;

/**
 * 1 管理员登陆界面
 * @author
 *
 */
public class AdminLoginFrm extends JFrame {

	private JPanel centerPnl = new JPanel();
	private JTextField nameTfd = new JTextField("");
	private JPasswordField pwdTfd = new JPasswordField("");
	
	private JPanel southPnl = new JPanel();
	private JButton loginBtn = new JButton("登录");
	private JButton resetBtn = new JButton("重置");

	public AdminLoginFrm() {
		
		initComponent();
		
		initComponentListener();
		
	}

	/**
	 * 控件监听器
	 */
	private void initComponentListener() {
		
		pwdTfd.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					validateUserLogin();
				}
			}});
		
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateUserLogin();
			}
		});
		
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameTfd.setText("");
				pwdTfd.setText("");
			}
		});
		
	}
	

	/**
	 * 1界面控件初始化
	 */
	private void initComponent() {
		
		this.setTitle("学生信息管理系统");
		
		Dimension dim = this.getToolkit().getScreenSize();
		this.setBounds((int)(dim.width * 0.2), (int)(dim.height * 0.2), (int)(dim.width * 0.5), (int)(dim.height * 0.5));

		centerPnl.setLayout(new GridLayout(9, 1));
		
		centerPnl.add(new JLabel());
		JLabel titleLbl = new JLabel("学生信息管理系统", JLabel.CENTER);
		titleLbl.setFont(new Font("字体", Font.PLAIN, 40));
		centerPnl.add(titleLbl);
		centerPnl.add(new JLabel());
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel nameLbl = new JLabel("用户名：");
		nameLbl.setFont(new Font("字体", Font.PLAIN, 30));
		namePanel.add(nameLbl);
		nameTfd.setPreferredSize(new Dimension(300, 50));
		namePanel.add(nameTfd);
		
		centerPnl.add(namePanel);
		JLabel pwdLbl = new JLabel("密    码：");
		pwdLbl.setFont(new Font("字体", Font.PLAIN, 30));
		pwdPanel.add(pwdLbl);
		pwdTfd.setPreferredSize(new Dimension(300, 50));
		pwdPanel.add(pwdTfd);
		centerPnl.add(pwdPanel);
		
		centerPnl.add(new JLabel());
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		loginBtn.setFont(new Font("字体", Font.PLAIN, 30));
		resetBtn.setFont(new Font("字体", Font.PLAIN, 30));
		btnPanel.add(loginBtn);
		btnPanel.add(resetBtn);
		
		centerPnl.add(btnPanel);

		this.add(centerPnl, BorderLayout.CENTER);
		this.add(southPnl, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * 用户名和密码验证
	 */
	private void validateUserLogin() {
		
		String userName = nameTfd.getText();
		String pwd = String.valueOf(pwdTfd.getPassword());
		
		// 用户名和密码合法性检查
		if (userName == null || userName.trim().length() == 0 
				|| pwd == null || pwd.trim().length() == 0) {
			
			JOptionPane.showMessageDialog(this, "请输入用户名和密码。");
			
			return;
			
		}
		
		// 用户名和密码校验通过，进入学生管理系统图形界面
		if (new AdminMngService().checkAdminLogin(userName, pwd)) {
			
			dispose();
			
			new StudentMngFrm();
			
		} else {
			//登陆失败
			JOptionPane.showMessageDialog(this, "登陆失败，请检查并重新输入用户名和密码。");
			
			nameTfd.setText("");
			
			pwdTfd.setText("");
			
		}
	
	}

}
