import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Color;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField tfAid;
	private JPasswordField tfApass;
	private JTextField tfSid;
	private JPasswordField tfSpass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 745);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("ID\n");
		lblLogin.setBounds(152, 473, 66, 15);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password\n");
		lblPassword.setBounds(152, 505, 88, 15);
		contentPane.add(lblPassword);
		
		tfAid = new JTextField();
		tfAid.setBounds(266, 471, 124, 19);
		contentPane.add(tfAid);
		tfAid.setColumns(10);
		
		JButton adminSignIn = new JButton("Sign In");
		adminSignIn.setBackground(new Color(255, 255, 224));
		adminSignIn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		adminSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//admin sign in button is pressed
				String adminId = tfAid.getText();
				String adminPass = new String(tfApass.getPassword());
				if(adminId.equals("") || adminPass.equals("")) {
					JOptionPane.showMessageDialog(null,"please enter the credentials","Can't log in", 2);
				}
				else {
					Connection con = null;
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
						
						CallableStatement cs = con.prepareCall("{call checkAdminCredentials(?,?,?)}");
						cs.setString(1,adminId);
						cs.setString(2,adminPass);
						cs.registerOutParameter(3,java.sql.Types.CHAR);
						cs.execute();
						String str = cs.getString(3);
						if (!(str!=null && !str.isEmpty())) {
							JOptionPane.showMessageDialog(null, "Wrong Username/Password", "Invalid Credentials", 2);
							tfApass.setText("");
						}
						else {
							dispose();
							(new PS_Stations_Admin(adminId)).setVisible(true);
						}
						
					
					}catch(Exception e123) { e123.printStackTrace(); };
					
				}
			}
		});
		
		adminSignIn.setBounds(210, 558, 114, 25);
		contentPane.add(adminSignIn);
		
		JButton studentSignIn = new JButton("Sign In");
		studentSignIn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		studentSignIn.setBackground(new Color(255, 255, 224));
		
		studentSignIn.setBounds(722, 554, 114, 25);
		contentPane.add(studentSignIn);
		
		JLabel lblNotRegistredYet = new JLabel("Not Registred Yet?");
		lblNotRegistredYet.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNotRegistredYet.setBounds(488, 605, 192, 48);
		contentPane.add(lblNotRegistredYet);
		
		JButton buttonRegister = new JButton("Register");
		buttonRegister.setBackground(new Color(255, 255, 224));
		buttonRegister.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//What happens if register button is pressed
				dispose();
				(new Registration()).setVisible(true);
			}
		});
		buttonRegister.setBounds(531, 666, 114, 25);
		contentPane.add(buttonRegister);
		
		tfApass = new JPasswordField();
		tfApass.setBounds(266, 501, 124, 22);
		contentPane.add(tfApass);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("D:\\dbms\\p.PNG"));
		lblNewLabel_1.setBounds(230, 283, 160, 136);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\dbms\\s.PNG"));
		lblNewLabel.setBounds(740, 277, 149, 148);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\dbms\\login.PNG"));
		lblNewLabel_2.setBounds(173, -28, 806, 298);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblAdmin = new JLabel("ADMIN");
		lblAdmin.setForeground(new Color(0, 0, 139));
		lblAdmin.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAdmin.setBounds(126, 332, 114, 32);
		contentPane.add(lblAdmin);
		
		JLabel lblStudent = new JLabel("STUDENT");
		lblStudent.setForeground(new Color(0, 0, 139));
		lblStudent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblStudent.setBounds(603, 332, 114, 32);
		contentPane.add(lblStudent);
		
		tfSid = new JTextField();
		tfSid.setColumns(10);
		tfSid.setBounds(752, 468, 124, 19);
		contentPane.add(tfSid);
		
		tfSpass = new JPasswordField();
		tfSpass.setBounds(752, 498, 124, 22);
		contentPane.add(tfSpass);
		
		JLabel label_1 = new JLabel("Password\n");
		label_1.setBounds(638, 502, 88, 15);
		contentPane.add(label_1);
		
		JLabel label = new JLabel("ID\n");
		label.setBounds(638, 470, 66, 15);
		contentPane.add(label);
		
		
		studentSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//What happens if student Sign In button is pressed
				String studentId = tfSid.getText();
				String studentPass = new String (tfSpass.getPassword());
				
				if(studentId.equals("") || studentPass.equals("")) {
					JOptionPane.showMessageDialog(null,"please enter the credentials","Can't log in", 2);
				}
				else {
					Connection con = null;
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
						CallableStatement cs = con.prepareCall("{call checkStudentCredentials(?,?,?)}");
						cs.setString(1,studentId);
						cs.setString(2,studentPass);
						cs.registerOutParameter(3,java.sql.Types.CHAR);
						cs.execute();
						String str = cs.getString(3);
						if (!(str!=null && !str.isEmpty())) {
							//then it is null
							JOptionPane.showMessageDialog(null, "Wrong Username/Password", "Invalid Credentials", 2);
							tfSpass.setText("");
						}
						else {
							dispose();
							(new StudentHome(studentId)).setVisible(true);
						}	
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		
		
	}
}
