import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class PS_Stations_Student extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	public String text;
	private JTable table;
	private JTextField toSwapId;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public PS_Stations_Student(String studentId) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 745);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPsStationsSearch = new JLabel("PS STATIONS STUDENT\r\n");
		lblPsStationsSearch.setFont(new Font("Dialog", Font.BOLD, 22));
		lblPsStationsSearch.setBounds(339, 12, 293, 55);
		contentPane.add(lblPsStationsSearch);
		
		JButton btnRequestSwap = new JButton("Request Swap");
		btnRequestSwap.setBackground(new Color(255, 255, 224));
		btnRequestSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String toSwap = toSwapId.getText();
				if(studentId.equals(toSwap)) {
					JOptionPane.showMessageDialog(null,"ID can't be same","FAILED", 1);
				}
				else {
					Connection con = null;  
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
						Statement stmt = con.createStatement();
						Statement s = con.createStatement();
						/*String sql = "select allotted_station_id from student where studentId='"+studentId+"'";
						ResultSet rs = stmt.executeQuery(sql);
						int stationId1 = 0;  if(rs.next()) stationId1 = rs.getInt(1);
						sql = "select allotted_station_id from student where studentId='"+toSwap+"'";
						rs = stmt.executeQuery(sql);
						int stationId2 = 0; 
						if(rs.next()) stationId2 = rs.getInt(1);
						else {
							JOptionPane.showMessageDialog(null, "ID doesn't exist","", 1);
							return;
						}*/
						String sql = "select * from student where studentId='"+toSwap+"'";
						ResultSet rs = stmt.executeQuery(sql);
						if(rs.next()) {
							String no = "";
							sql = "select * from swap_requests where student1='"+toSwap+"' OR student2='"+toSwap+"'  ";
							ResultSet r = s.executeQuery(sql);
							if(r.next() == false) {
								sql = "insert into swap_requests values ('"+studentId+"','"+toSwap+"','"+no+"') ";
								PreparedStatement ps = con.prepareStatement(sql);
								ps.executeUpdate();
								JOptionPane.showMessageDialog(null,"Requested swap","", 1);
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Other ID already requested swap","", 1);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "ID doesn't exist","", 1);
						}
					}catch(Exception ee) { ee.printStackTrace(); JOptionPane.showMessageDialog(null, "error","", 1); }
				}
				
			}
		});
		btnRequestSwap.setBounds(138, 239, 173, 25);
		contentPane.add(btnRequestSwap);
		
		JLabel lblAlloted = new JLabel("Allotted Station");
		lblAlloted.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblAlloted.setBounds(149, 396, 182, 16);
		contentPane.add(lblAlloted);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(469, 163, 404, 220);
		contentPane.add(textArea);
		
		JLabel lblMessageFromAdmin = new JLabel("Message from Admin");
		lblMessageFromAdmin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMessageFromAdmin.setBounds(566, 134, 157, 16);
		contentPane.add(lblMessageFromAdmin);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 459, 548, 78);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		
		Connection con = null;  
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
			Statement stmt = con.createStatement();
			String sql ;
			sql = "select * from student_notify where student_id = '"+studentId+"' ";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				textArea.append("Please fill the preferences as requested by "+rs.getInt(2)+"\n");
			}
			sql = "select allotted_station_id,stationName,domain,accommodation,city,stipend from station,student where allotted_station_id=stationId AND studentId='"+studentId+"' ";
			rs = stmt.executeQuery(sql);
		
				table.setModel(DbUtils.resultSetToTableModel(rs));
				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(50);
				columnModel.getColumn(1).setPreferredWidth(180);
				columnModel.getColumn(2).setPreferredWidth(180);
				columnModel.getColumn(3).setPreferredWidth(150);
				columnModel.getColumn(4).setPreferredWidth(180);
				columnModel.getColumn(5).setPreferredWidth(180);
		
			
				JButton btnBack = new JButton("BACK");
				btnBack.setBackground(new Color(255, 255, 224));
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						(new StudentHome(studentId)).setVisible(true);
					}
				});
				btnBack.setBounds(42, 131, 97, 25);
				contentPane.add(btnBack);
				
				toSwapId = new JTextField();
				toSwapId.setBounds(10, 240, 116, 22);
				contentPane.add(toSwapId);
				toSwapId.setColumns(10);
				
				JLabel lblWith = new JLabel("with");
				lblWith.setBounds(42, 211, 56, 16);
				contentPane.add(lblWith);
				
				JLabel lblNewLabel = new JLabel("New label");
				lblNewLabel.setIcon(new ImageIcon("D:\\dbms\\psms.PNG"));
				lblNewLabel.setBounds(10, 24, 301, 67);
				contentPane.add(lblNewLabel);
				
				ResultSet r = stmt.executeQuery("select * from student,station where studentId='"+studentId+"' AND allotted_station_id=stationId ");
				if(r.next() == false) {
					btnRequestSwap.setVisible(false);
					toSwapId.setVisible(false);
					lblWith.setVisible(false);
				}
				
				sql = "select * from swap_requests where student1='"+studentId+"' OR student2='"+studentId+"'  ";
				ResultSet rs10 = stmt.executeQuery(sql);
				if(rs10.next()) {
					String id1 = rs10.getString(1);  String id2 = rs10.getString(2); String str = rs10.getString(3);
					if(id1.equals(studentId)) {
						//id2 is the requested id to swap
						toSwapId.setText(id2);
						btnRequestSwap.setEnabled(false);
					}
					else {
						toSwapId.setText(id1);
						btnRequestSwap.setEnabled(false);
					}
					if(str.equals(""))
						btnRequestSwap.setText("Already requested");
					else
						btnRequestSwap.setText(str);
				}
		
		}catch(Exception e) {
			e.printStackTrace(); }
			//System.out.println("PS_STATIONS_STUDENT SQL ERROR"); }
		
		
		
	}
}
