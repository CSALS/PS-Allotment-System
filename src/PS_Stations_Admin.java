import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.Font;
public class PS_Stations_Admin extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JTextField tf5;
	private JTextField tf6;
	private JTextField tf7;
	private JTextField tf8;
	private JTextField tfName;
	private JTextField tfDomain;
	private JTextField tfAddress;
	private JTextField tfStipend;
	private JTextField tf2nd;
	private JTextField tf4th;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_6;
	private JTextField textField_5;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTable table_1;
	private JTextField tfSid1;
	private JTextField tfSid2;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public PS_Stations_Admin(String adminId) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 745);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPsStationsAdmin = new JLabel("PS STATIONS ADMIN");
		lblPsStationsAdmin.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPsStationsAdmin.setBounds(470, 13, 263, 40);
		contentPane.add(lblPsStationsAdmin);
		
		JButton btnStartAllottment = new JButton("Start Allottment");
		btnStartAllottment.setBackground(new Color(255, 255, 224));
		btnStartAllottment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//what happens when start allottment button is pressed
				Connection con = null;  
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
					Statement stmt = con.createStatement();
					String sql ;
					sql = "select * from student,station where stationId=allotted_station_id";
					ResultSet r = stmt.executeQuery(sql);
					if(r.next() == false) {
						sql = "select studentId from student where studentId NOT IN (select sid from student_preference)";
						ResultSet rs = stmt.executeQuery(sql);
						if(rs.next()) {
							PreparedStatement ps = con.prepareStatement("insert into student_notify values ('"+rs.getString(1)+"','"+Integer.parseInt(adminId)+"') ");
							ps.executeUpdate();
							while(rs.next()) {
								ps = con.prepareStatement("insert into student_notify values ('"+rs.getString(1)+"','"+Integer.parseInt(adminId)+"') ");
								ps.executeUpdate();
							}
							JOptionPane.showMessageDialog(null,"Notified those who didn't lock ","Can't do allottment", 2);
						}
						else {
							Statement stmt1 = con.createStatement();
							Statement stmt2 = con.createStatement();
							Statement stmt3 = con.createStatement();
							//do the allotment process
				
							//FOR 2nd YEARITES
							sql = "select studentId from student where year=2 order by cgpa DESC";
							ResultSet rs1 = stmt1.executeQuery(sql);
							//rs1 contains all student ids in descending order of CG
							while(rs1.next()) {
								//System.out.println(rs.getString(1));
								String id = rs1.getString(1);
								sql = "select * from student_preference where sid='"+id+"' order by priority ";
								ResultSet rs2 = stmt2.executeQuery(sql);
								//rs2 contains all preferences of student id
								while(rs2.next()) {
									int stationId = rs2.getInt(2);
									sql = "select * from station where stationId='"+stationId+"' AND slots2ndYear >= 1  ";
									ResultSet rs3 = stmt3.executeQuery(sql);
									if(rs3.next()) {
										int slots2nd = rs3.getInt(7); System.out.print(stationId + " " + slots2nd);
										slots2nd--;
										//station can allotted then break
										PreparedStatement ps = con.prepareStatement("update station set slots2ndYear='"+slots2nd+"' where stationId='"+stationId+"' ");
										ps.executeUpdate();
										ps = con.prepareStatement("update student set allotted_station_id='"+stationId+"' where studentId = '"+id+"'   ");
										ps.executeUpdate();
										//ps = con.prepareStatement("delete from student_notify"); ps.executeUpdate();
										
										break;
									}
								}	
							}
							//FOR 4th YEARITES
							sql = "select studentId from student where year=4 order by cgpa DESC";
							ResultSet r1 = stmt1.executeQuery(sql);
							//rs1 contains all student ids in descending order of CG
							while(r1.next()) {
								String id = r1.getString(1);
								sql = "select * from student_preference where sid='"+id+"' order by priority ";
								ResultSet rs2 = stmt2.executeQuery(sql);
								//rs2 contains all preferences of student id
								while(rs2.next()) {
									int stationId = rs2.getInt(2);
									sql = "select * from station where stationId='"+stationId+"' AND slots4thYear >= 1 ";
									ResultSet rs3 = stmt3.executeQuery(sql);
									
									if(rs3.next()) {
										int slots4th = rs3.getInt(8); System.out.print(stationId + " " + slots4th);
										slots4th--;
										//station can allotted then break
										PreparedStatement ps = con.prepareStatement("update station set slots4thYear='"+slots4th+"' where stationId='"+stationId+"'  ");
										ps.executeUpdate();
										ps = con.prepareStatement("update student set allotted_station_id='"+stationId+"' where studentId = '"+id+"'    ");
										ps.executeUpdate();
										//ps = con.prepareStatement("delete from student_notify"); ps.executeUpdate();
								
										break;
									}
								}	
							}
							
							CallableStatement cs = con.prepareCall("{call deleteNotify()}");
							cs.execute();
							
							Statement stmt5 = con.createStatement(); 
							String sql1 = "select * from student";
							ResultSet rs6 = stmt.executeQuery(sql1);
							table.setModel(DbUtils.resultSetToTableModel(rs6));
							JOptionPane.showMessageDialog(null,"Completed the allottment process","Done",1);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Already done","Can't do allottment",1);
					}
					
				}
				catch(MySQLIntegrityConstraintViolationException eee) {
					JOptionPane.showMessageDialog(null,"Already notified","Can't do allottment",1);
				}
				catch(SQLException ee) {
					ee.printStackTrace();
				}
					//JOptionPane.showMessageDialog(null,"Already notified","Done",1); }
				catch(Exception e) { e.printStackTrace(); }
			}
		});
		btnStartAllottment.setBounds(48, 155, 156, 25);
		contentPane.add(btnStartAllottment);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setBackground(new Color(255, 255, 224));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				(new LogIn()).setVisible(true);
			}
		});
		btnLogOut.setBounds(771, 45, 97, 25);
		contentPane.add(btnLogOut);
		
		JButton btnSearchStations = new JButton("Search Stations");
		btnSearchStations.setBackground(new Color(255, 255, 224));
		btnSearchStations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				(new PS_Stations_Search(adminId,"ADMIN")).setVisible(true);
			}
		});
		btnSearchStations.setBounds(247, 155, 183, 25);
		contentPane.add(btnSearchStations);
		
		JLabel lblStudentDetails = new JLabel("Student Details");
		lblStudentDetails.setBounds(508, 116, 116, 16);
		contentPane.add(lblStudentDetails);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(470, 159, 459, 139);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		Connection con = null; 
		try {
			//These sql executes after the form is opened (before any button is pressed)
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
			Statement stmt = con.createStatement(); 
			String sql = "select * from student";
			ResultSet rs = stmt.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			JButton btnAddStation = new JButton("Add station");
			btnAddStation.setBackground(new Color(255, 255, 224));
			btnAddStation.setBounds(40, 250, 97, 25);
			contentPane.add(btnAddStation);
			
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(28, 300, 396, 385);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JPanel addStations = new JPanel();
			addStations.setBackground(Color.WHITE);
			addStations.setBounds(137, 379, 369, 228);
			addStations.setLayout(null);
			JPanel removeStations = new JPanel();
			removeStations.setBackground(Color.WHITE);
			removeStations.setBounds(137, 379, 369, 228);
			removeStations.setLayout(null);
			JPanel updateStations = new JPanel();
			updateStations.setBackground(Color.WHITE);
			updateStations.setBounds(137, 379, 369, 228);
			updateStations.setLayout(null);
	
			CardLayout c = new CardLayout();
			panel.setLayout(c);
			panel.add(addStations,"1"); panel.add(removeStations,"2"); 
 
 JLabel lblStationId = new JLabel("station id");
 lblStationId.setBounds(69, 152, 56, 16);
 removeStations.add(lblStationId);
 
 textField_7 = new JTextField();
 textField_7.setBounds(167, 149, 95, 22);
 removeStations.add(textField_7);
 textField_7.setColumns(10);
 

 JButton btnDelete = new JButton("DELETE");
 btnDelete.addActionListener(new ActionListener() {
 	public void actionPerformed(ActionEvent arg0) {
 		String id = textField_7.getText();
 		System.out.println(id);
 		int sid=0; int flag=1;
 		try {
			sid = Integer.parseInt(id);
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Enter valid id", "Can't update", 2);
			flag=0;
		}
 		if(flag != 0) {
 	 			Connection con = null;
 	 	 		try {
 	 				Class.forName("com.mysql.jdbc.Driver");
 	 				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
 	 				Statement stmt = con.createStatement();
 	 				String sql = "select * from student_preference where sid = '"+sid+"'";
 	 				ResultSet rs = stmt.executeQuery(sql);
 	 				if(rs.next()) {
 	 					JOptionPane.showMessageDialog(null,"Station already picked in preferences", "can't delete", 2);
 	 				}
 	 				else {
 	 					sql = "delete from station where stationId = '"+sid+"' ";
 	 					PreparedStatement ps = con.prepareStatement(sql);
 	 					ps.executeUpdate();
 	 					JOptionPane.showMessageDialog(null,"Deleted the station if exists","DONE",1);
 	 				}
 	 	 			
 	 	 		}catch(Exception e) { e.printStackTrace(); }
 		}
 		
 		
 	}
 });
 btnDelete.setBounds(117, 217, 97, 25);
 removeStations.add(btnDelete);panel.add(updateStations,"3");
 
 JLabel label = new JLabel("station name");
 label.setBounds(44, 38, 74, 16);
 updateStations.add(label);
 
 textField = new JTextField();
 textField.setBounds(150, 35, 116, 22);
 updateStations.add(textField);
 textField.setColumns(10);
 
 textField_1 = new JTextField();
 textField_1.setBounds(131, 86, 116, 22);
 updateStations.add(textField_1);
 textField_1.setColumns(10);
 
 JLabel label_1 = new JLabel("domain");
 label_1.setBounds(46, 89, 56, 16);
 updateStations.add(label_1);
 
 JLabel label_2 = new JLabel("accommodation");
 label_2.setBounds(12, 149, 90, 16);
 updateStations.add(label_2);
 
 JButton btnUpdate = new JButton("UPDATE");
 
 btnUpdate.setBounds(275, 170, 97, 25);
 updateStations.add(btnUpdate);
 
 textField_3 = new JTextField();
 textField_3.setBounds(131, 197, 116, 22);
 updateStations.add(textField_3);
 textField_3.setColumns(10);
 
 JLabel lblCity = new JLabel("city");
 lblCity.setBounds(30, 200, 56, 16);
 updateStations.add(lblCity);
 
 JLabel label_4 = new JLabel("stipend");
 label_4.setBounds(30, 250, 56, 16);
 updateStations.add(label_4);
 
 textField_4 = new JTextField();
 textField_4.setBounds(131, 247, 116, 22);
 updateStations.add(textField_4);
 textField_4.setColumns(10);
 
 JLabel label_5 = new JLabel("slots for 2nd year");
 label_5.setBounds(12, 293, 106, 16);
 updateStations.add(label_5);
 
 textField_6 = new JTextField();
 textField_6.setBounds(172, 342, 116, 22);
 updateStations.add(textField_6);
 textField_6.setColumns(10);
 
 JLabel lblNewLabel = new JLabel("slots for 4th year");
 lblNewLabel.setBounds(44, 345, 116, 16);
 updateStations.add(lblNewLabel);
 
 textField_5 = new JTextField();
 textField_5.setColumns(10);
 textField_5.setBounds(131, 290, 116, 22);
 updateStations.add(textField_5);
 
 JLabel lblIdOfThat = new JLabel("ID of that station");
 lblIdOfThat.setBounds(275, 65, 80, 16);
 updateStations.add(lblIdOfThat);
 
 textField_8 = new JTextField();
 textField_8.setBounds(268, 120, 116, 22);
 updateStations.add(textField_8);
 textField_8.setColumns(10);
 
 JComboBox comboBox = new JComboBox();
 comboBox.setBounds(131, 146, 74, 22);
 comboBox.addItem("Yes"); comboBox.addItem("No");
 updateStations.add(comboBox);
			
			//PANEL-1   - ADD STATIONS
			JLabel lblName = new JLabel("station name");
			lblName.setBounds(56, 54, 74, 16);
			addStations.add(lblName);
		
			tfName = new JTextField();
			tfName.setBounds(162, 51, 116, 22);
			addStations.add(tfName);
			tfName.setColumns(10);
			
			JLabel lblDomain = new JLabel("domain");
			lblDomain.setBounds(58, 105, 56, 16);
			addStations.add(lblDomain);
			
			tfDomain = new JTextField();
			tfDomain.setColumns(10);
			tfDomain.setBounds(143, 102, 116, 22);
			addStations.add(tfDomain);
			
			JLabel lblAccom = new JLabel("accommodation");
			lblAccom.setBounds(24, 165, 90, 16);
			addStations.add(lblAccom);
			
			JLabel lblAddress = new JLabel("city");
			lblAddress.setBounds(42, 216, 56, 16);
			addStations.add(lblAddress);
			
			tfAddress = new JTextField();
			tfAddress.setColumns(10);
			tfAddress.setBounds(143, 213, 116, 22);
			addStations.add(tfAddress);
			
			JLabel lblStipend = new JLabel("stipend");
			lblStipend.setBounds(42, 266, 56, 16);
			addStations.add(lblStipend);
			
			tfStipend = new JTextField();
			tfStipend.setColumns(10);
			tfStipend.setBounds(143, 263, 116, 22);
			addStations.add(tfStipend);
			
			JLabel lbl2nd = new JLabel("slots for 2nd year");
			lbl2nd.setBounds(24, 309, 106, 16);
			addStations.add(lbl2nd);
			
			tf2nd = new JTextField();
			tf2nd.setColumns(10);
			tf2nd.setBounds(143, 306, 116, 22);
			addStations.add(tf2nd);
			
			JLabel lbl4th = new JLabel("slots for 4th year");
			lbl4th.setBounds(24, 353, 106, 16);
			addStations.add(lbl4th);
			
			tf4th = new JTextField();
			tf4th.setColumns(10);
			tf4th.setBounds(143, 350, 116, 22);
			addStations.add(tf4th);
			
			
			JButton btnAdd = new JButton("ADD");
			btnAdd.setBackground(new Color(255, 255, 224));
			
			btnAdd.setBounds(287, 186, 97, 25);
			addStations.add(btnAdd);
			
			JComboBox comboAccom = new JComboBox();
			comboAccom.setBounds(143, 162, 56, 22);
			addStations.add(comboAccom);
			comboAccom.addItem("Yes");
			comboAccom.addItem("No");
			
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String name = tfName.getText();
					String domain = tfDomain.getText();
					String accom = (String)comboAccom.getSelectedItem();
					String address = tfAddress.getText();
					String stipend = tfStipend.getText();
					String slots2nd = tf2nd.getText();
					String slots4th = tf4th.getText();
					int flag=1;
					if(name.equals("") || domain.equals("") || accom.equals("") || address.equals("") || stipend.equals("") || slots2nd.equals("") || slots4th.equals("")) {
						JOptionPane.showMessageDialog(null,"Fill all details","Can't add",2);
						flag=0;
					}
					if(flag != 0) {
						try {
							int stipendNum = Integer.parseInt(stipend);
						}catch(NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null,"Fill stipend in integer format","Can't add",2);
							flag=0;
							tfStipend.setText("");
						}
						try {
							
							int slots2ndNum = Integer.parseInt(slots2nd);
						
						}catch(NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null,"Fill slots 2nd yr in integer format","Can't add",2);
							flag=0;
							tf2nd.setText("");
						}
						try {
						
							int slots4thNum = Integer.parseInt(slots4th);
						}catch(NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null,"Fill slots 4th in integer format","Can't add",2);
							flag=0;
							tf4th.setText("");
						}
					}
					
					if(flag != 0) {
						Connection con = null;  
						try {
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
							//
							//count the number of stations first.
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery("select max(stationId) from station");
							int maxStationId=0;
							if(rs.next()) maxStationId = rs.getInt(1); maxStationId++;
							String sql = "insert into station values ('"+maxStationId+"','"+name+"','"+domain+"','"+accom+"','"+address+"','"+Integer.parseInt(stipend)+"','"+Integer.parseInt(slots2nd)+"','"+Integer.parseInt(slots4th)+"')";
							PreparedStatement ps = con.prepareStatement(sql);
							ps.executeUpdate();
							JOptionPane.showMessageDialog(null, "ADDED", "ADDED", 1);
						}catch(Exception e) { e.printStackTrace(); }
					}
				}
			});
			
			btnUpdate.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		String name = textField.getText();
					String domain = textField_1.getText();
					String accom = (String) comboBox.getSelectedItem();
					String address = textField_3.getText();
					String stipend = textField_4.getText();
					String slots2nd = textField_5.getText();
					String slots4th = textField_6.getText();
					String id = textField_8.getText();  int sid=0; int flag=1;
					try {
						sid = Integer.parseInt(id);
					}catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Enter valid id", "Can't update", 2);
						flag=0;
					}
					if(flag != 0) {
						if(id.equals("")) {
							JOptionPane.showMessageDialog(null, "Enter an id", "Can't update", 2);
						}
						else {
							Connection con = null;  
							try {
								Class.forName("com.mysql.jdbc.Driver");
								con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
								Statement stmt = con.createStatement();
								ResultSet rs = stmt.executeQuery("select * from station where stationId='"+id+"'");
								if(rs.next() == false) {
									JOptionPane.showMessageDialog(null, "Station doesn't exist", "Can't update", 2);
								}
								else {
									if(name.equals("") && domain.equals("") && accom.equals("") && address.equals("") && stipend.equals("") && slots2nd.equals("") && slots4th.equals("")) {
										JOptionPane.showMessageDialog(null, "No details entered to update", "Can't update", 2);
									}
									else {
										PreparedStatement ps;
										if(name.equals("") == false) {
											ps = con.prepareStatement("update station set stationName='"+name+"' where stationId='"+sid+"'");
											ps.executeUpdate();
										}
										if(domain.equals("") == false) {
											ps = con.prepareStatement("update station set domain='"+domain+"' where stationId='"+sid+"'");
											ps.executeUpdate();
										}
										if(accom.equals("") == false) {
											ps = con.prepareStatement("update station set accommodation='"+accom+"' where stationId='"+sid+"'");
											ps.executeUpdate();
										}
										if(address.equals("") == false) {
											ps = con.prepareStatement("update station set city='"+address+"' where stationId='"+sid+"'");
											ps.executeUpdate();
										}
										if(stipend.equals("") == false) {
											ps = con.prepareStatement("update station set stipend='"+Integer.parseInt(stipend)+"' where stationId='"+sid+"'");
											ps.executeUpdate();
										}
										if(slots2nd.equals("") == false) {
											ps = con.prepareStatement("update station set slots2ndYear='"+Integer.parseInt(slots2nd)+"' where stationId='"+sid+"'");
											ps.executeUpdate();
										}
										if(slots4th.equals("") == false) {
											ps = con.prepareStatement("update station set slots4thYear='"+Integer.parseInt(slots4th)+"' where stationId='"+sid+"'");
											ps.executeUpdate();
										}
										JOptionPane.showMessageDialog(null, "updated successfully", "updated", 1);
									}
								}
							}catch(Exception e) { e.printStackTrace(); }
						}
					}
					
					
					
			 	}
			 });
			
			JButton btnRemoveStation = new JButton("Remove station");
			btnRemoveStation.setBackground(new Color(255, 255, 224));
			btnRemoveStation.setBounds(149, 250, 126, 25);
			contentPane.add(btnRemoveStation);
			
			JButton btnUpdateStation = new JButton("Update station");
			btnUpdateStation.setBackground(new Color(255, 255, 224));
			btnUpdateStation.setBounds(287, 250, 122, 25);
			contentPane.add(btnUpdateStation);
			
			JLabel lblPsSwapRequests = new JLabel("PS Swap Requests");
			lblPsSwapRequests.setBounds(512, 330, 112, 16);
			contentPane.add(lblPsSwapRequests);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(472, 364, 316, 204);
			contentPane.add(scrollPane_1);
			
			table_1 = new JTable();
			scrollPane_1.setViewportView(table_1);
			
			Connection conn = null;
			try {
				
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
				Statement stmt1 = con.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select * from swap_requests");
				table_1.setModel(DbUtils.resultSetToTableModel(rs1));
				
				JLabel lblNewLabel_1 = new JLabel("id 2");
				lblNewLabel_1.setBounds(864, 422, 56, 16);
				contentPane.add(lblNewLabel_1);
				
				JLabel label_6 = new JLabel("id 1");
				label_6.setBounds(864, 391, 56, 16);
				contentPane.add(label_6);
				
				tfSid1 = new JTextField();
				tfSid1.setBounds(920, 384, 116, 22);
				contentPane.add(tfSid1);
				tfSid1.setColumns(10);
				
				tfSid2 = new JTextField();
				tfSid2.setColumns(10);
				tfSid2.setBounds(920, 416, 116, 22);
				contentPane.add(tfSid2);
				
				
				
				JButton btnApprove = new JButton("Approve");
				btnApprove.setBackground(new Color(255, 255, 224));
				btnApprove.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String sid1 = tfSid1.getText();
						String sid2 = tfSid2.getText();
						int flag=1;
						if(sid1.equals("") || sid2.equals("")) {
							JOptionPane.showMessageDialog(null,"Enter both IDs","Can't approve",2);
							flag=0;
						}
						if(flag != 0) {
							Connection con = null;
							try {
								Class.forName("com.mysql.jdbc.Driver");
								con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
								String sql = "select * from swap_requests where student1='"+sid1+"' AND student2='"+sid2+"' ";
								Statement stm = con.createStatement();
								ResultSet rs = stm.executeQuery(sql);
								if(rs.next() == false) {
									JOptionPane.showMessageDialog(null,"ID combination not present","Can't approve",2);
								}
								else {
									rs = stm.executeQuery("select status from swap_requests where student1='"+sid1+"' AND student2='"+sid2+"' ");
									String status = null;
									if(rs.next()) status = rs.getString(1);
										
									if(status.equals("")) {
										String a = "Approved";
										sql = "update swap_requests set status='"+a+"' where student1='"+sid1+"' AND student2='"+sid2+"' ";
										PreparedStatement ps = con.prepareStatement(sql);
										ps.executeUpdate();
										sql = "select allotted_station_id from student where studentId='"+sid1+"'";
										ResultSet rs1 = stm.executeQuery(sql);
										int station1 = 0 , station2 = 0;
										if(rs1.next()) station1 = rs1.getInt(1);
										sql = "select allotted_station_id from student where studentId='"+sid2+"'";
										ResultSet rs2 = stm.executeQuery(sql);
										if(rs2.next()) station2 = rs2.getInt(1);
										sql = "update student set allotted_station_id='"+station2+"' where studentId='"+sid1+"' ";
										ps = con.prepareStatement(sql); ps.executeUpdate();
										sql = "update student set allotted_station_id='"+station1+"' where studentId='"+sid2+"' ";
										ps = con.prepareStatement(sql); ps.executeUpdate();
										JOptionPane.showMessageDialog(null,"Approval complete","DONE",2);
										ResultSet rsf = stm.executeQuery("select * from swap_requests");
										table_1.setModel(DbUtils.resultSetToTableModel(rsf));
										rsf = stm.executeQuery("select * from student");
										table.setModel(DbUtils.resultSetToTableModel(rsf));
									}
									else {
										JOptionPane.showMessageDialog(null,"Already approved/rejected","Can't approve",2);
									}
								}	
							}catch(Exception e) { e.printStackTrace(); }
							
						}
					}
				});
				btnApprove.setBounds(898, 451, 97, 25);
				contentPane.add(btnApprove);
				
				JButton btnReject = new JButton("Reject");
				btnReject.setBackground(new Color(255, 255, 224));
				btnReject.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String sid1 = tfSid1.getText();
						String sid2 = tfSid2.getText();
						int flag=1;
						if(sid1.equals("") || sid2.equals("")) {
							JOptionPane.showMessageDialog(null,"Enter both IDs","Can't approve",2);
							flag=0;
						}
						if(flag != 0) {
							Connection con = null;
							try {
								Class.forName("com.mysql.jdbc.Driver");
								con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
								String sql = "select * from swap_requests where student1='"+sid1+"' AND student2='"+sid2+"' ";
								Statement stm = con.createStatement();
								ResultSet rs = stm.executeQuery(sql);
								if(rs.next() == false) {
									JOptionPane.showMessageDialog(null,"ID combination not present","Can't approve",2);
								}
								else {
									sql = "select status from swap_requests where student1='"+sid1+"' and student2='"+sid2+"' ";
									rs = stm.executeQuery(sql); String status = null;
									if(rs.next()) status = rs.getString(1);
									System.out.print(status);
									if(status.equals("") == false) {
										JOptionPane.showMessageDialog(null,"Already approved","",2);
									}
									else {
										String a = "Rejected";
										sql = "update swap_requests set status='"+a+"' where student1='"+sid1+"' AND student2='"+sid2+"' ";
										PreparedStatement ps = con.prepareStatement(sql);
										ps.executeUpdate();
										JOptionPane.showMessageDialog(null,"Rejection complete","DONE",2);
										ResultSet rs1 = stm.executeQuery("select * from swap_requests");
										table_1.setModel(DbUtils.resultSetToTableModel(rs1));
									}
								}	
							}catch(Exception e1) { e1.printStackTrace(); }
							
						}
					}
				});
				btnReject.setBounds(898, 489, 97, 25);
				contentPane.add(btnReject);
				
				JLabel lblNewLabel_2 = new JLabel("New label");
				lblNewLabel_2.setIcon(new ImageIcon("D:\\dbms\\psms.PNG"));
				lblNewLabel_2.setBounds(12, 24, 307, 46);
				contentPane.add(lblNewLabel_2);
				TableColumnModel columnModel1 = table_1.getColumnModel();
				columnModel1.getColumn(0).setPreferredWidth(150);
				columnModel1.getColumn(1).setPreferredWidth(150);
				columnModel1.getColumn(2).setPreferredWidth(150);
				
			}catch(Exception e) { e.printStackTrace(); }
			
			
			btnAddStation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					c.show(panel,"1");
				}
			});
			btnRemoveStation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					c.show(panel,"2");
				}
			});
			btnUpdateStation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					c.show(panel,"3");
				}
			});
			
			/*TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(190);
			columnModel.getColumn(1).setPreferredWidth(180);
			columnModel.getColumn(2).setPreferredWidth(180);
			columnModel.getColumn(3).setPreferredWidth(50);
			columnModel.getColumn(4).setPreferredWidth(100);
			columnModel.getColumn(5).setPreferredWidth(150);
			columnModel.getColumn(6).setPreferredWidth(100);
			columnModel.getColumn(7).setPreferredWidth(350);*/

		}catch(Exception e) { e.printStackTrace(); }
	}
}
