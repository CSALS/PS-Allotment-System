import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.ImageIcon;

public class PS_Stations_Search extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField tfPref1;
	private JTextField tfPref2;
	private JTextField tfPref3;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public PS_Stations_Search(String studentId,String adminORstudent) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 745);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSearchStations = new JLabel("SEARCH STATIONS");
		lblSearchStations.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSearchStations.setBounds(482, 13, 175, 32);
		contentPane.add(lblSearchStations);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 284, 827, 219);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBackground(new Color(255, 255, 224));
		btnSearch.setBounds(799, 214, 97, 24);
		contentPane.add(btnSearch);
		
		JComboBox boxAccom = new JComboBox();
		boxAccom.addItem("Any");
		boxAccom.addItem("Yes");
		boxAccom.addItem("No");
		boxAccom.setBounds(172, 215, 82, 22);
		contentPane.add(boxAccom);
		
		JLabel lblAccommodation = new JLabel("Accommodation");
		lblAccommodation.setBounds(172, 186, 97, 16);
		contentPane.add(lblAccommodation);
		
		JLabel lblStipend = new JLabel("Stipend\r\n");
		lblStipend.setBounds(320, 186, 97, 16);
		contentPane.add(lblStipend);
		
		JComboBox comboStipend = new JComboBox();
		comboStipend.setBounds(320, 215, 82, 22);
		comboStipend.addItem("Any");
		comboStipend.addItem("No");
		comboStipend.addItem("Yes");
		contentPane.add(comboStipend);
		
		JLabel lblBranch = new JLabel("Branch\r\n");
		lblBranch.setBounds(576, 195, 97, 16);
		contentPane.add(lblBranch);
		
		JComboBox comboBranch = new JComboBox();
		comboBranch.setBounds(556, 215, 82, 22);
		comboBranch.addItem("Any");
		comboBranch.addItem("IT");
		comboBranch.addItem("Electrical");
		comboBranch.addItem("Mechanical");
		contentPane.add(comboBranch);
		
		JLabel lblCity = new JLabel("City\r\n");
		lblCity.setBounds(719, 195, 97, 16);
		contentPane.add(lblCity);
		
		JComboBox comboCity = new JComboBox();
		comboCity.setBounds(699, 215, 82, 22);
		comboCity.addItem("Any");
		comboCity.addItem("Hyderabad");
		comboCity.addItem("Chennai");
		comboCity.addItem("Bangalore");
		comboCity.addItem("Delhi");
		comboCity.addItem("Mumbai");
		contentPane.add(comboCity);
		
		JComboBox comboYear = new JComboBox();
		comboYear.setBounds(438, 226, 82, 22);
		comboYear.addItem("Any");
		comboYear.addItem("2nd");
		comboYear.addItem("4th");
		contentPane.add(comboYear);
		
		

		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(443, 197, 56, 16);
		contentPane.add(lblYear);
		
		tfPref1 = new JTextField();
		tfPref1.setBounds(995, 224, 116, 22);
		contentPane.add(tfPref1);
		tfPref1.setColumns(10);
		
		tfPref2 = new JTextField();
		tfPref2.setColumns(10);
		tfPref2.setBounds(995, 272, 116, 22);
		contentPane.add(tfPref2);
		
		tfPref3 = new JTextField();
		tfPref3.setColumns(10);
		tfPref3.setBounds(995, 332, 116, 22);
		contentPane.add(tfPref3);
		
		JLabel labelDisplay = new JLabel("Select Preferences With Station ID");
		labelDisplay.setBounds(904, 164, 221, 16);
		contentPane.add(labelDisplay);
		
		JLabel label = new JLabel("1");
		label.setBounds(919, 227, 56, 16);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("2");
		label_1.setBounds(919, 275, 56, 16);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("3");
		label_2.setBounds(919, 335, 56, 16);
		contentPane.add(label_2);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBackground(new Color(255, 255, 224));

		btnSubmit.setBounds(963, 393, 97, 25);
		contentPane.add(btnSubmit);
		
		if(adminORstudent.equals("ADMIN")) {
			//if admin wants to search
			btnSubmit.setVisible(false);
			tfPref1.setVisible(false);
			tfPref2.setVisible(false);
			tfPref3.setVisible(false);
			labelDisplay.setVisible(false);
			label.setVisible(false);
			label_1.setVisible(false);
			label_2.setVisible(false);
		}
		
		JButton btnBack = new JButton("BACK");
		btnBack.setBackground(new Color(255, 255, 224));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				if(adminORstudent.equals("ADMIN")) {
					(new PS_Stations_Admin(studentId)).setVisible(true);
				}
				else {
					(new StudentHome(studentId)).setVisible(true);
				}
			}
		});
	
		btnBack.setBounds(12, 137, 97, 25);
		contentPane.add(btnBack);
		
		Connection con = null; 
		try {
			//These sql executes after the form is opened (before any button is pressed)
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
			Statement stmt = con.createStatement(); 
			//Statement stmt2 = con.createStatement();
			String sql = "select * from station";
			ResultSet rs = stmt.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setIcon(new ImageIcon("D:\\dbms\\psms.PNG"));
			lblNewLabel.setBounds(12, 30, 300, 72);
			contentPane.add(lblNewLabel);
			
			
			if(adminORstudent.equals("STUDENT")) {
				sql = "select * from student_preference where sid='"+studentId+"'";
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					tfPref1.setText(""+rs.getInt(2));
					if(rs.next()) {
						tfPref2.setText(""+rs.getInt(2));
						if(rs.next()) {
							tfPref3.setText(""+rs.getInt(2));
						}
					}
					btnSubmit.setVisible(false);
					labelDisplay.setText("Preferences already selected");
				}
			}
			}catch(Exception e) {
			System.out.println(e);
			}
		 /* FILTERING PROCESS */
		 //if 2nd year selects preference with 0 slots then error message will be displayed.
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//what happens if search button is pressed
				Connection con = null;  
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
					Statement stmt = con.createStatement();
					String sql;
					String stipend = (String) comboStipend.getSelectedItem();
					String accom = (String) boxAccom.getSelectedItem();
					String branch = (String) comboBranch.getSelectedItem();
					String city = (String) comboCity.getSelectedItem();
					String year = (String)comboYear.getSelectedItem();
					int second = 1000000 , fourth = 100000;
					if(year.equals("2nd")) {
						second = 0;
					}
					else if(year.equals("4th")) {
						//4th yearite
						fourth = 0;
					}
					else {
						second = fourth = 0;
					}
					
					if(stipend.equals("Any")) {
						
						if(accom.equals("Any")) {  // && branch.equals("Any") && city.equals("Any")
							if(branch.equals("Any")) {
								if(city.equals("Any")) {
									sql = "Select * from station where slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"'";
								}
								else {
									sql = "select * from station where city = '"+city+"' and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"')  ";
								}
							}
							else {
								if(city.equals("Any")) {
									sql = "select * from station where domain = '"+branch+"' and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
								}
								else {
									sql = "select * from station where domain = '"+branch+"' AND city = '"+city+"' and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
								}
							}
						}
						else {
							if(branch.equals("Any")) {
								if(city.equals("Any")) {
									sql = "Select * from station where accommodation = '"+accom+"' and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
								}
								else {
									sql = "select * from station where city = '"+city+"' AND accommodation = '"+accom+"' and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
								}
							}
							else {
								if(city.equals("Any")) {
									sql = "select * from station where domain = '"+branch+"' AND accommodation = '"+accom+"' and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
								}
								else {
									sql = "select * from station where domain = '"+branch+"' AND city = '"+city+"' AND accommodation = '"+accom+"' and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
								}
							}
						}
					}
					else {
						if(stipend.equals("Yes")) {
							if(accom.equals("Any")) {  // && branch.equals("Any") && city.equals("Any")
								if(branch.equals("Any")) {
									if(city.equals("Any")) {
										sql = "Select * from station where stipend>=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
									else {
										sql = "select * from station where city = '"+city+"' AND stipend >= 0  and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
								}
								else {
									if(city.equals("Any")) {
										sql = "select * from station where domain = '"+branch+"'  and stipend>=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
									else {
										sql = "select * from station where domain = '"+branch+"' AND city = '"+city+"'  and stipend>=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
								}
							}
							else {
								if(branch.equals("Any")) {
									if(city.equals("Any")) {
										sql = "Select * from station where accommodation = '"+accom+"'  and stipend>=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
									else {
										sql = "select * from station where city = '"+city+"' AND accommodation = '"+accom+"'  and stipend>=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"')  ";
									}
								}
								else {
									if(city.equals("Any")) {
										sql = "select * from station where domain = '"+branch+"' AND accommodation = '"+accom+"'  and stipend>=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"')  ";
									}
									else {
										sql = "select * from station where domain = '"+branch+"' AND city = '"+city+"' AND accommodation = '"+accom+"'  and stipend>=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
								}
							}							
						}
						else { //no stipend
							if(accom.equals("Any")) {  // && branch.equals("Any") && city.equals("Any")
								if(branch.equals("Any")) {
									if(city.equals("Any")) {
										sql = "Select * from station where stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
									else {
										sql = "select * from station where city = '"+city+"' and stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
								}
								else {
									if(city.equals("Any")) {
										sql = "select * from station where domain = '"+branch+"'  and stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
									else {
										sql = "select * from station where domain = '"+branch+"' AND city = '"+city+"'  and stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
								}
							}
							else {
								if(branch.equals("Any")) {
									if(city.equals("Any")) {
										sql = "Select * from station where accommodation = '"+accom+"'  and stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
									else {
										sql = "select * from station where city = '"+city+"' AND accommodation = '"+accom+"'  and stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
								}
								else {
									if(city.equals("Any")) {
										sql = "select * from station where domain = '"+branch+"' AND accommodation = '"+accom+"'  and stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
									else {
										sql = "select * from station where domain = '"+branch+"' AND city = '"+city+"' AND accommodation = '"+accom+"'  and stipend=0 and (slots2ndyear >= '"+second+"' OR slots4thyear >= '"+fourth+"') ";
									}
								}
							}							
						}
					}
					ResultSet rs = stmt.executeQuery(sql);
					table.setModel(DbUtils.resultSetToTableModel(rs));
					}catch(Exception e) {
						e.printStackTrace();
					}
			}
		});
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//what happens when submit is pressed
				String pref1 = tfPref1.getText(); int p1;
				String pref2 = tfPref2.getText(); int p2; 
				String pref3 = tfPref3.getText(); int p3;
				int flag=1;
				try {
					p1 = Integer.parseInt(pref1);
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Enter valid preference number 1", "Can't subtmit", 2);
					tfPref1.setText("");
					flag=0;
				}
				try {
					p2 = Integer.parseInt(pref2);
				}catch(NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Enter valid preference number 2", "Can't subtmit", 2);
					tfPref2.setText("");
					flag=0;
				}
				try {
					p3 = Integer.parseInt(pref3);
				}catch(NumberFormatException e3) {
					JOptionPane.showMessageDialog(null, "Enter valid preference number 3", "Can't subtmit", 2);
					tfPref3.setText("");
					flag=0;
				}
				//preferences can't be alphabets check that
				if(flag != 0) {
					if(pref1.equals(pref2) || pref1.equals(pref3) || pref2.equals(pref3)) {
						JOptionPane.showMessageDialog(null,"Preferences cannot be equal","Can't submit", 2);
						//tfPref1.setText(""); tfPref2.setText(""); tfPref3.setText("");
					}
					else {
						Connection con = null;
						try {
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
							Statement stmt = con.createStatement();
							CallableStatement cs = con.prepareCall("{call maxStationId(?)}");
				            cs.registerOutParameter(1,java.sql.Types.INTEGER); cs.execute();
							int maxStation = cs.getInt(1);
							PreparedStatement ps;
							p1 = Integer.parseInt(pref1); p2 = Integer.parseInt(pref2); p3 = Integer.parseInt(pref3);
							if(p1<0 || p2<0 || p3<0 || p1>maxStation || p2>maxStation || p3>maxStation) {
								/*flag=0;
								JOptionPane.showMessageDialog(null,"Invalid preference numbers","Can't submit", 2);
								tfPref1.setText(""); tfPref2.setText(""); tfPref3.setText("");*/
								throw new MySQLIntegrityConstraintViolationException();
							}
							if(flag != 0) {
								cs = con.prepareCall("{call addPreference(?,?,?)}");
								cs.setString(1,studentId);
								cs.setInt(2,p1);  cs.setInt(3,1);
								cs.execute();
								cs.setInt(2,p2); cs.setInt(3,2);
								cs.execute();
								cs.setInt(2,p3); cs.setInt(3,3);
								cs.execute();
								labelDisplay.setText("Preferences selected");
								btnSubmit.setVisible(false);
							}	
						}catch(MySQLIntegrityConstraintViolationException ee) {
							JOptionPane.showMessageDialog(null,"Enter valid preference number","Can't submit",2);
							tfPref1.setText(""); tfPref2.setText(""); tfPref3.setText("");
						}
						catch(Exception e) {
							e.printStackTrace();
							System.out.println("PS_STATIONS_SEARCH SQL ERROR"); }
					}
				}
				
				
			}
		});
		
	}
}
