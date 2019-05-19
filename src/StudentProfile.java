import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.ImageIcon;

public class StudentProfile extends JFrame {

	private JPanel contentPane;
	private JTextField tBranch;
	private JTextField tCg;
	private JTextField tPass;
	private JTextField tMobile;
	private JTextField tAge;
	private JTable mobileTable;
	private JTable studentTable;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	//ADD ADDRESS ALSO
	public StudentProfile(String studentId) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 745);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setBounds(43, 263, 66, 15);
		contentPane.add(lblBranch);
		
		JLabel lblCgpa = new JLabel("CGPA");
		lblCgpa.setBounds(43, 319, 66, 15);
		contentPane.add(lblCgpa);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(24, 415, 98, 26);
		contentPane.add(lblPassword);
		
		tBranch = new JTextField();
		tBranch.setColumns(10);
		tBranch.setBounds(127, 261, 124, 19);
		contentPane.add(tBranch);
		
		tCg = new JTextField();
		tCg.setColumns(10);
		tCg.setBounds(127, 317, 124, 19);
		contentPane.add(tCg);
		
		tPass = new JTextField();
		tPass.setColumns(10);
		tPass.setBounds(127, 418, 124, 19);
		contentPane.add(tPass);
		
		JLabel lblRegistration = new JLabel("EDIT DETAILS\n");
		lblRegistration.setFont(new Font("Dialog", Font.BOLD, 23));
		lblRegistration.setBounds(520, 13, 242, 55);
		contentPane.add(lblRegistration);
		
		JButton btnRegister = new JButton("UPDATE\n");
		btnRegister.setBackground(new Color(255, 255, 224));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//update button pressed
				String pass = tPass.getText();
				String cg = tCg.getText();
				String branch = tBranch.getText();
				String age = tAge.getText();
				String mobile = tMobile.getText();
				if(pass.equals("") && cg.equals("") && branch.equals("") && age.equals("") && mobile.equals("")) {
					JOptionPane.showMessageDialog(null, "No details to be updated","", 1);
				}
				else if(!mobile.equals("") && mobile.length()!=10) {
					 JOptionPane.showMessageDialog(null, "Mobile length should be 10","can't update", 2);
					 tMobile.setText("");
				}
				else {
					Connection con = null;
		             try {
		                 Class.forName("com.mysql.jdbc.Driver");
		                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
		                 PreparedStatement ps;
		                 if(pass.equals("") == false) {
		                	 String sql = "update student set password='"+pass+"' where studentId='"+studentId+"' ";
		                	 ps = con.prepareStatement(sql);
		                	 ps.executeUpdate();
		                 }
		                 int flag = 1;
		                 if(cg.equals("") == false) {
		                	 CallableStatement cs = con.prepareCall("{call updateCgpa(?,?,?)}");
		                	 cs.setFloat(1, Float.parseFloat(cg));
		                	 cs.setString(2, studentId);
		                	 cs.registerOutParameter(3,java.sql.Types.INTEGER);
		                	 cs.execute();
		                	 flag = cs.getInt(3);
		                	 if(flag == 0) {
		                		 throw new NumberFormatException();
		                	 }
		                 }
		                 if(branch.equals("") == false) {
		                	 String sql = "update student set branch='"+branch+"' where studentId='"+studentId+"' ";
		                	 ps = con.prepareStatement(sql);
		                	 ps.executeUpdate();
		                 }
		                 if(age.equals("") == false) {
		                	 String sql = "update student set age='"+Integer.parseInt(age)+"' where studentId='"+studentId+"' ";
		                	 ps = con.prepareStatement(sql);
		                	 ps.executeUpdate();
		                 }
		                 if(mobile.equals("") == false) {
		                	 try {
		                		 float m = Float.parseFloat(mobile);
		                	 }catch(Exception e) { throw new NumberFormatException(); }
		                	
		                	 CallableStatement cs = con.prepareCall("{call addMobileOfStudent(?,?)}");
		                	 cs.setString(1, studentId); cs.setString(2, mobile);
		                	 cs.execute();
		                 }
		                 JOptionPane.showMessageDialog(null, "updated","update", 1);
		                Statement stmt = con.createStatement();
		                 ResultSet rs = stmt.executeQuery("select * from student where studentId='"+studentId+"'");
		     			studentTable.setModel(DbUtils.resultSetToTableModel(rs));
		     			ResultSet rs1 = stmt.executeQuery("select * from student_mobile where student_id='"+studentId+"' ");
		     			mobileTable.setModel(DbUtils.resultSetToTableModel(rs1));
		     			TableColumnModel columnModel = mobileTable.getColumnModel();
		    			columnModel.getColumn(0).setPreferredWidth(180);
		    			columnModel.getColumn(1).setPreferredWidth(180);
		    			
		    			TableColumnModel columnModel1 = studentTable.getColumnModel();
		    			columnModel1.getColumn(0).setPreferredWidth(180);
		    			columnModel1.getColumn(1).setPreferredWidth(180);
		    			columnModel1.getColumn(2).setPreferredWidth(180);
		    			columnModel1.getColumn(3).setPreferredWidth(50);
		    			columnModel1.getColumn(4).setPreferredWidth(50);
		    			columnModel1.getColumn(5).setPreferredWidth(100);
		    			columnModel1.getColumn(6).setPreferredWidth(80);
		    			columnModel1.getColumn(7).setPreferredWidth(250);
		                 
		             }catch(NumberFormatException nfe) {
		            	 JOptionPane.showMessageDialog(null, "Enter valid number","can't update", 2);
		             }
		             catch(MySQLIntegrityConstraintViolationException fke) {
		            	 JOptionPane.showMessageDialog(null, "Mobile exists already","can't update", 2);
		             }
		             catch(Exception e) { e.printStackTrace();  JOptionPane.showMessageDialog(null, "can't update","can't update", 2);  }
				}
			}
		});
		btnRegister.setBounds(89, 554, 114, 25);
		contentPane.add(btnRegister);
		
		JButton btnBack = new JButton("BACK");
		btnBack.setBackground(new Color(255, 255, 224));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				(new StudentHome(studentId)).setVisible(true);
			}
		});
		btnBack.setBounds(66, 110, 114, 35);
		contentPane.add(btnBack);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(43, 477, 56, 16);
		contentPane.add(lblMobile);
		
		tMobile = new JTextField();
		tMobile.setColumns(10);
		tMobile.setBounds(127, 475, 124, 19);
		contentPane.add(tMobile);
		
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
			
			JLabel lblAge_1 = new JLabel("Age");
			lblAge_1.setBounds(43, 369, 56, 16);
			contentPane.add(lblAge_1);
			
			tAge = new JTextField();
			tAge.setColumns(10);
			tAge.setBounds(127, 366, 124, 19);
			contentPane.add(tAge);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(535, 457, 370, 89);
			contentPane.add(scrollPane);
			
			mobileTable = new JTable();
			mobileTable.setBackground(Color.WHITE);
			scrollPane.setViewportView(mobileTable);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(500, 262, 497, 55);
			contentPane.add(scrollPane_1);
			
			studentTable = new JTable();
			studentTable.setBackground(Color.WHITE);
			scrollPane_1.setViewportView(studentTable);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from student where studentId='"+studentId+"'");
			studentTable.setModel(DbUtils.resultSetToTableModel(rs));
			ResultSet rs1 = stmt.executeQuery("select * from student_mobile where student_id='"+studentId+"' ");
			mobileTable.setModel(DbUtils.resultSetToTableModel(rs1));
			
			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setIcon(new ImageIcon("D:\\dbms\\psms.PNG"));
			lblNewLabel.setBounds(12, 13, 310, 84);
			contentPane.add(lblNewLabel);
			
			TableColumnModel columnModel = mobileTable.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(180);
			columnModel.getColumn(1).setPreferredWidth(180);
			
			TableColumnModel columnModel1 = studentTable.getColumnModel();
			columnModel1.getColumn(0).setPreferredWidth(180);
			columnModel1.getColumn(1).setPreferredWidth(180);
			columnModel1.getColumn(2).setPreferredWidth(180);
			columnModel1.getColumn(3).setPreferredWidth(50);
			columnModel1.getColumn(4).setPreferredWidth(50);
			columnModel1.getColumn(5).setPreferredWidth(100);
			columnModel1.getColumn(6).setPreferredWidth(80);
			columnModel1.getColumn(7).setPreferredWidth(250);
		}catch(Exception e) { e.printStackTrace(); }
	}
}
