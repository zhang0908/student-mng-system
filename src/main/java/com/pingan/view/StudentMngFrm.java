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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.pingan.entity.StudentEntity;
import com.pingan.service.StudentMngService;

/**
 * 
 */
public class StudentMngFrm extends JFrame {
	
	private StudentMngService studentMnsgService = new StudentMngService();

	private final int maxPageNum = 99;

	private JPanel northPnl = new JPanel();
	private JPanel southPnl = new JPanel();
	private JPanel centerPnl = new JPanel();
	private JButton firstBtn = new JButton("首页");
	private JButton lastBtn = new JButton("末页");
	private JButton nextBtn = new JButton("下一页");
	private JButton preBtn = new JButton("上一页");
	private JButton addBtn = new JButton("添加");
	private JButton deleteBtn = new JButton("删除");
	private JButton updateBtn = new JButton("修改");
	private JButton findBtn = new JButton("查找");
	private JLabel pageNumLbl = new JLabel("第 " + pageNum + "/99 页");
	private JTextField findCondTfd = new JTextField("");
	public static JTable jTable;
	private JScrollPane jScrollPane;
	private DefaultTableModel myTableModel;

	public static String[] studentTblCols = { "编号", "姓名", "学号", "学分", "院系", "邮箱", "联系电话"};
	
	public static int pageNum = 1;

	public StudentMngFrm() {
		
		//初始化控件
		initComponent();
		
		// 控件监听器初始化
		initComponentListener();
		
	}
	
public static void main(String[] args) {
		
		// 启动登陆界面
		new StudentMngFrm();
		
	}

	private void initComponent() {
		
		setTitle("学生信息管理系统");
		
		Dimension dim = this.getToolkit().getScreenSize();
		this.setBounds((int)(dim.width * 0.2), (int)(dim.height * 0.2), (int)(dim.width * 0.7), (int)(dim.height * 0.7));

		northPnl.setLayout(new GridLayout(1, 2));
		
		JPanel north1Pnl = new JPanel();
		north1Pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel north2Pnl = new JPanel();
		north2Pnl.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		findCondTfd.setPreferredSize(new Dimension(300, 38));
		findCondTfd.setToolTipText("学生姓名");
		north1Pnl.add(findCondTfd);
		findBtn.setFont(new Font("字体", Font.PLAIN, 20));
		north1Pnl.add(findBtn);
		addBtn.setFont(new Font("字体", Font.PLAIN, 20));
		north2Pnl.add(addBtn);
		deleteBtn.setFont(new Font("字体", Font.PLAIN, 20));
		north2Pnl.add(deleteBtn);
		updateBtn.setFont(new Font("字体", Font.PLAIN, 20));
		north2Pnl.add(updateBtn);
		
		northPnl.add(north2Pnl);
		northPnl.add(north1Pnl);

		centerPnl.setLayout(new GridLayout(1, 1));

		String[][] result = studentMnsgService.qryStudentList(pageNum);
		
		myTableModel = new DefaultTableModel(result, studentTblCols){
			// 不可编辑，但可选行
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
		jTable = new JTable(myTableModel);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
		jTable.setDefaultRenderer(Object.class, cr);
		fillStudentListTbl(jTable, result);

		jScrollPane = new JScrollPane(jTable);
		centerPnl.add(jScrollPane);

		southPnl.setLayout(new FlowLayout(FlowLayout.CENTER));

		pageNumLbl.setHorizontalAlignment(JLabel.CENTER);

		firstBtn.setFont(new Font("字体", Font.PLAIN, 20));
		southPnl.add(firstBtn);
		preBtn.setFont(new Font("字体", Font.PLAIN, 20));
		southPnl.add(preBtn);
		pageNumLbl.setFont(new Font("字体", Font.PLAIN, 20));
		southPnl.add(pageNumLbl);
		nextBtn.setFont(new Font("字体", Font.PLAIN, 20));
		southPnl.add(nextBtn);
		lastBtn.setFont(new Font("字体", Font.PLAIN, 20));
		southPnl.add(lastBtn);

		this.add(northPnl, BorderLayout.NORTH);
		this.add(centerPnl, BorderLayout.CENTER);
		this.add(southPnl, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	

	/**
	 * 控件监听器
	 */
	private void initComponentListener() {
		
		findCondTfd.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findStudentByName();
				}
			}
		});
		
		findBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findStudentByName();
			}
		});
		
		// 查找按钮添加匿名类键盘事件监听器
		findBtn.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findStudentByName();
				}
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewStudentFrm(studentMnsgService);
			}
		});
		
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedStudent();
			}

		});
		
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				updateStudentInfoAction();
			}
		});
		
		firstBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pageNum = 1;
				String[][] result = studentMnsgService.qryStudentList(pageNum);
				fillStudentListTbl(jTable, result);
				pageNumLbl.setText("第" + pageNum + "/99 页");
			}
		});
		
		preBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pageNum--;
				if (pageNum <= 0) {
					pageNum = 1;
				}
				String[][] result = studentMnsgService.qryStudentList(pageNum);
				fillStudentListTbl(jTable, result);
				pageNumLbl.setText("第" + pageNum + "/99 页");
			}
		});
		

		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pageNum++;
				if (pageNum > maxPageNum) {
					pageNum = maxPageNum;
				}
				String[][] result = studentMnsgService.qryStudentList(pageNum);
				fillStudentListTbl(jTable, result);
				pageNumLbl.setText("第" + pageNum + "/99 页");
			}
		});
		

		lastBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pageNum = maxPageNum;
				String[][] result = studentMnsgService.qryStudentList(pageNum);
				fillStudentListTbl(jTable, result);
				pageNumLbl.setText("第" + pageNum + "/99 页");
			}
		});
		
	}
	
	/**
	 * 删除选中的学生信息
	 */
	private void deleteSelectedStudent() {
		
		int selectedRow = jTable.getSelectedRow();
		
		if (selectedRow == -1) {
			
			JOptionPane.showMessageDialog(this, "请选中学生信息，再执行删除操作。", "删除学生信息", JOptionPane.OK_OPTION);
			
			return;
			
		} else {
			
			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this, "确认删除选中的学生信息？", "删除学生信息", JOptionPane.OK_CANCEL_OPTION)) {
				
				String sname = jTable.getValueAt(selectedRow, 1).toString();
				String sno = jTable.getValueAt(selectedRow, 2).toString();
				
				// 删除学生信息
				studentMnsgService.deleteStudent(sname, sno);
				
				// 删除后，重新刷新table表
				String[][] result = studentMnsgService.qryStudentList(pageNum);
				fillStudentListTbl(jTable, result);
				pageNumLbl.setText("第" + pageNum + "/99 页");
				
			}
			
		}
		
	}
	

	/**
	 * 修改学生信息
	 */
	private void updateStudentInfoAction() {
		
		int selectedRow = jTable.getSelectedRow();
		
		if (selectedRow == -1) {
			
			JOptionPane.showMessageDialog(this, "请选中学生信息，再执行修改操作。", "修改学生信息", JOptionPane.OK_OPTION);
			
			return;
			
		} else {
			
			String sname = jTable.getValueAt(selectedRow, 1).toString();
			String sno = jTable.getValueAt(selectedRow, 2).toString();
			
			// 删除学生信息
			StudentEntity studentEntity = studentMnsgService.qryStudentInfo(sname, sno);
			
			NewStudentFrm newStudentFrm = new NewStudentFrm(studentMnsgService, "modify");
			
			newStudentFrm.fillStudentInfo(studentEntity);
			
		}
		
	}

	/**
	 * 填充学生信息table表
	 * @param jTable
	 * @param result
	 */
	public static void fillStudentListTbl(JTable jTable, String[][] result) {
		((DefaultTableModel) jTable.getModel()).setDataVector(result, studentTblCols);
		jTable.setRowHeight(30);
		TableColumn column1 = jTable.getColumnModel().getColumn(0);
		column1.setPreferredWidth(50);
		column1.setMaxWidth(50);
		column1.setMinWidth(50);
		TableColumn column2 = jTable.getColumnModel().getColumn(1);
		column2.setPreferredWidth(120);
		column2.setMaxWidth(120);
		column2.setMinWidth(120);
		TableColumn column3 = jTable.getColumnModel().getColumn(2);
		column3.setPreferredWidth(120);
		column3.setMaxWidth(120);
		column3.setMinWidth(120);
		TableColumn column4 = jTable.getColumnModel().getColumn(3);
		column4.setPreferredWidth(120);
		column4.setMaxWidth(120);
		column4.setMinWidth(120);
		TableColumn column5 = jTable.getColumnModel().getColumn(4);
		column5.setPreferredWidth(300);
		column5.setMaxWidth(300);
		column5.setMinWidth(300);
		TableColumn column6 = jTable.getColumnModel().getColumn(5);
		column6.setPreferredWidth(300);
		column6.setMaxWidth(300);
		column6.setMinWidth(300);
	}


	/**
	 * 根据名称查找学生
	 */
	private void findStudentByName() {
		pageNum = 0;
		String name = findCondTfd.getText();
		
		if (name == null || "".equals(name)) {
			JOptionPane.showMessageDialog(this, "请输入待查询学生的姓名。");
			findCondTfd.requestFocus();
			return;
		}
		String[][] result = studentMnsgService.queryByName(name);
		findCondTfd.setText("");
		fillStudentListTbl(StudentMngFrm.jTable, result);
		pageNumLbl.setText("查询");
	}

}
