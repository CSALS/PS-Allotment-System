import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Registration extends JFrame {

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfId;
	private JTextField tfBranch;
	private JTextField tfAge;
	private JTextField tfCg;
	private JTextField tfMobile1;
	private JTextField tfMobile2;
	private JTextField tfMobile3;
	private JPasswordField tfPass;
	private JLabel lblConfirmPassword;
	private JPasswordField tfPassConfirm;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public Registration() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 745);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(158, 142, 66, 15);
		contentPane.add(lblName);
		
		JLabel lblCollegid = new JLabel("College_ID");
		lblCollegid.setBounds(158, 216, 98, 15);
		contentPane.add(lblCollegid);
		
		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setBounds(158, 313, 66, 15);
		contentPane.add(lblBranch);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(158, 397, 66, 15);
		contentPane.add(lblYear);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(158, 500, 66, 15);
		contentPane.add(lblAge);
		
		JLabel lblCgpa = new JLabel("CGPA");
		lblCgpa.setBounds(158, 575, 66, 15);
		contentPane.add(lblCgpa);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(405, 237, 98, 26);
		contentPane.add(lblPassword);
		
		tfName = new JTextField();
		tfName.setBounds(242, 140, 124, 19);
		contentPane.add(tfName);
		tfName.setColumns(10);
		
		tfId = new JTextField();
		tfId.setColumns(10);
		tfId.setBounds(242, 212, 124, 19);
		contentPane.add(tfId);
		
		tfBranch = new JTextField();
		tfBranch.setColumns(10);
		tfBranch.setBounds(242, 311, 124, 19);
		contentPane.add(tfBranch);
		
		tfAge = new JTextField();
		tfAge.setColumns(10);
		tfAge.setBounds(242, 498, 124, 19);
		contentPane.add(tfAge);
		
		tfCg = new JTextField();
		tfCg.setColumns(10);
		tfCg.setBounds(242, 573, 124, 19);
		contentPane.add(tfCg);
		
		JLabel lblRegistration = new JLabel("REGISTRATION");
		lblRegistration.setFont(new Font("Dialog", Font.BOLD, 23));
		lblRegistration.setBounds(378, 12, 242, 55);
		contentPane.add(lblRegistration);
		
		JButton btnRegister = new JButton("REGISTER");

		btnRegister.setBounds(526, 378, 114, 25);
		contentPane.add(btnRegister);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(405, 177, 56, 16);
		contentPane.add(lblMobile);
		
		tfMobile1 = new JTextField();
		tfMobile1.setColumns(10);
		tfMobile1.setBounds(496, 174, 124, 19);
		contentPane.add(tfMobile1);
		
		tfMobile2 = new JTextField();
		tfMobile2.setColumns(10);
		tfMobile2.setBounds(644, 174, 124, 19);
		contentPane.add(tfMobile2);
		
		tfMobile3 = new JTextField();
		tfMobile3.setColumns(10);
		tfMobile3.setBounds(790, 174, 124, 19);
		contentPane.add(tfMobile3);
		
		tfPass = new JPasswordField();
		tfPass.setBounds(515, 244, 105, 22);
		contentPane.add(tfPass);
		
		lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(405, 297, 98, 26);
		contentPane.add(lblConfirmPassword);
		
		tfPassConfirm = new JPasswordField();
		tfPassConfirm.setBounds(515, 299, 105, 22);
		contentPane.add(tfPassConfirm);
		
		JComboBox comboYear = new JComboBox();
		comboYear.setBounds(236, 393, 66, 22);
		comboYear.addItem("2");
		comboYear.addItem("4");
		contentPane.add(comboYear);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\dbms\\psms.PNG"));
		lblNewLabel.setBounds(12, 41, 313, 71);
		contentPane.add(lblNewLabel);
		
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//what happens if register button is pressed
				String name = tfName.getText();
				String id = tfId.getText();
				String branch = tfBranch.getText();
				String year = (String) comboYear.getSelectedItem();
				String age = tfAge.getText();
				String cg = tfCg.getText();
				String m1 = tfMobile1.getText();
				String m2 = tfMobile2.getText();
				String m3 = tfMobile3.getText();
				String pass = new String (tfPass.getPassword());
				String passConfirm = new String (tfPassConfirm.getPassword());
				if(name.equals("") || id.equals("") || branch.equals("") || year.equals("") || pass.equals("") || passConfirm.equals("") || age.equals("") || cg.equals("") || (m1.equals("") && m2.equals("") && m3.equals("")) ){
					JOptionPane.showMessageDialog(null, "Please enter all details", "Invalid Credentials", 2);
				}
				else if (!(id.substring(0,4).equals("2015") || id.substring(0,4).equals("2017")) ) {
					JOptionPane.showMessageDialog(null, "ID number should start with 2015/2017", "Invalid Credentials", 2);
				}
				else {
					if(pass.equals(passConfirm) == false) {
						JOptionPane.showMessageDialog(null,"Passwords don't match. Please enter the password again","Error", 2);
						tfPassConfirm.setText("");
					}
					else {
						if(cg.equalsIgnoreCase("NC")) {
							JOptionPane.showMessageDialog(null,"Sorry.People with backlogs can't register","Can't Register", 2);
							dispose();
							(new LogIn()).setVisible(true);
						}
						else {
							
							if(id.length()!=8) {
								JOptionPane.showMessageDialog(null,"Invalid ID","Can't Register", 2);
								tfId.setText("");
							}
							else {
									int flag=1;
									try {
										int x = Integer.parseInt(age);
									}catch(NumberFormatException nfe) {
										JOptionPane.showMessageDialog(null,"Invalid Age","Can't Register", 2);
										tfAge.setText("");
										flag=0;
									}
									try {
										float y = Float.parseFloat(cg);
									}catch(NumberFormatException nfe1) {
										JOptionPane.showMessageDialog(null,"Invalid CG","Can't Register", 2);
										tfCg.setText("");
										flag=0;
									}
									try {
										if(m1.equals("")==false) {
											long x;  x = Long.parseLong(m1); 
										}	
									}catch(NumberFormatException nfe2) {
										JOptionPane.showMessageDialog(null,"Mobile 1 can't contain characters","Can't Register", 2);
										tfMobile1.setText("");
										flag=0;
									}
									try {
										if(m2.equals("")==false) {
											long x;  x = Long.parseLong(m2); 
										}	
									}catch(NumberFormatException nfe3) {
										JOptionPane.showMessageDialog(null,"Mobile 2 can't contain characters","Can't Register", 2);
										tfMobile2.setText("");
										flag=0;
									}
									try {
										if(m3.equals("")==false) {
											long x;  x = Long.parseLong(m3); 
										}	
									}catch(NumberFormatException nfe4) {
										JOptionPane.showMessageDialog(null,"Mobile 3 can't contain characters","Can't Register", 2);
										tfMobile3.setText("");
										flag=0;
									}
									if(flag != 0) {
										Connection con = null; CallableStatement mystat = null;
										try {
											Class.forName("com.mysql.jdbc.Driver");
											con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
											PreparedStatement ps; 
											//ps.close();
											//mystat = con.prepareCall("{ call printUserId() }");
											//mystat.execute();
											//System.out.println(mystat);
											//insert copy here
											if(m1.equals("")==false) {
												if(m1.length()!=10) {
													JOptionPane.showMessageDialog(null,"Length of mobile 1 should be 10","Can't Register", 2);
													tfMobile2.setText("");
													flag=0;
												}
											}
											if(m2.equals("")==false) {
												
												if(m2.length()!=10) {
													JOptionPane.showMessageDialog(null,"Length of mobile 2 should be 10","Can't Register", 2);
													tfMobile2.setText("");
													flag=0;
												}
											}
											if(m3.equals("")==false) {
												
												if(m3.length()!=10) {
													JOptionPane.showMessageDialog(null,"Length of mobile 3 should be 10","Can't Register", 2);
													tfMobile3.setText("");
													flag=0;
												}
											}
											if(m1.equals(m2) && m1.equals("")==false && m2.equals("")==false) {
												JOptionPane.showMessageDialog(null,"Mobile 1 and mobile 2 can't be same","Can't Register", 2);
												tfMobile2.setText("");
												flag=0;
											}
											if(m2.equals(m3) && m2.equals("")==false && m3.equals("")==false) {
												JOptionPane.showMessageDialog(null,"Mobile 2 and mobile 3 can't be same","Can't Register", 2);
												tfMobile3.setText("");
												flag=0;
											}
											if(m1.equals(m3) && m1.equals("")==false && m3.equals("")==false) {
												JOptionPane.showMessageDialog(null,"Mobile 1 and mobile 3 can't be same","Can't Register", 2);
												tfMobile3.setText("");
												flag=0;
											}
											if(Float.parseFloat(cg) > 10 || Float.parseFloat(cg) <= 0) {
												JOptionPane.showMessageDialog(null,"Invalid CG","Can't Register", 2);
												tfCg.setText("");
												flag=0;
											}
											if(flag!=0) {
												/*ps = con.prepareStatement("insert into student values ('"+id+"','"+name+"','"+pass+"','"+Integer.parseInt(age)+"','"+Integer.parseInt(year)+"','"+branch+"','"+Integer.parseInt(cg)+"',null)");
												ps.executeUpdate();*/
												if(id.substring(0,4).equals("2017")) {
													year = "2";
													comboYear.setSelectedItem("2");
												}
												else {
													year = "4";
													comboYear.setSelectedItem("4");
												}
												mystat = con.prepareCall("{call addStudent('"+id+"','"+name+"','"+pass+"','"+Integer.parseInt(age)+"','"+Integer.parseInt(year)+"','"+branch+"','"+Float.parseFloat(cg)+"',null)}");
												mystat.execute();
												JOptionPane.showMessageDialog(null, "Registered Successfully", "Message", 1);
												dispose();
												(new LogIn()).setVisible(true);
						
												if(m1.equals("")==false) {
													if(m1.length() == 10) {
														/*(ps = con.prepareStatement("insert into student_mobile values ('"+id+"' , '"+m1+"')");
														ps.executeUpdate();*/
														mystat = con.prepareCall("{call addMobileOfStudent('"+id+"','"+m1+"')}");
														mystat.execute();
													}
												}
												if(m2.equals("")==false) {
													if(m2.length() == 10) {
														mystat = con.prepareCall("{call addMobileOfStudent('"+id+"','"+m2+"')}");
														mystat.execute();
													}
												}
												if(m3.equals("")==false) {
													if(m3.length() == 10) {
														mystat = con.prepareCall("{call addMobileOfStudent('"+id+"','"+m3+"')}");
														mystat.execute();
													}
												}
											}
											
										}catch(SQLException e) {
											//e.printStackTrace();
											JOptionPane.showMessageDialog(null,"Invalid ID"," ID belongs to someone else", 2); tfId.setText(""); }
										catch(Exception e1) {
											e1.printStackTrace();
											System.out.println("error"); }
										//dont'add mobile numbers first... add student id first so that we won't get foreign key violation
										
									}
							}

							//CGPA can't exceed 10 ... mobile number shouldn't have any character other than number.. check all these
							//two mobile numbers shouldn't be equal.
						}
					}

				}
				
			}
		});
		
	}
}



/* 									if(m1.equals("")==false) {
												if(m1.length()!=10) {
													JOptionPane.showMessageDialog(null,"Length of mobile 1 should be 10","Can't Register", 2);
													tfMobile2.setText("");
													flag=0;
												}
												else {
													ps = con.prepareStatement("insert into student_mobile values ('"+id+"' , '"+Integer.parseInt(m1)+"')");
													ps.executeUpdate();
													//mystat = con.prepareCall("{call addMobileOfStudent(id,Integer.parseInt(m1)}");
													//mystat.execute();
												}
												
												//ps.close();
											}
											if(m2.equals("")==false) {
												
												if(m2.length()!=10) {
													JOptionPane.showMessageDialog(null,"Length of mobile 2 should be 10","Can't Register", 2);
													tfMobile2.setText("");
													flag=0;
												}
												else {
													ps = con.prepareStatement("insert into student_mobile values ('"+id+"' , '"+Integer.parseInt(m2)+"')");
													ps.executeUpdate();
												}
												//ps.close();
											}
											if(m3.equals("")==false) {
												
												if(m3.length()!=10) {
													JOptionPane.showMessageDialog(null,"Length of mobile 3 should be 10","Can't Register", 2);
													tfMobile3.setText("");
													flag=0;
												}
												else {
													ps = con.prepareStatement("insert into student_mobile values ('"+id+"' , '"+Integer.parseInt(m3)+"')");
													ps.executeUpdate();
												}
												//ps.close();
											}
											if(flag!=0) {
												ps = con.prepareStatement("insert into student values ('"+id+"','"+name+"','"+pass+"','"+Integer.parseInt(age)+"','"+Integer.parseInt(year)+"','"+branch+"','"+Integer.parseInt(cg)+"')");
												ps.executeUpdate();
												JOptionPane.showMessageDialog(null, "Registered Successfully", "Message", 1);
												dispose();
												(new LogIn()).setVisible(true);
											}
											
											*/
