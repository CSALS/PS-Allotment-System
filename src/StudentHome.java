import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import javax.swing.ImageIcon;
import java.awt.Color;
public class StudentHome extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public StudentHome(String studentId) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 745);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHome = new JLabel("HOME");
		lblHome.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblHome.setBounds(436, 66, 94, 59);
		contentPane.add(lblHome);
		
		JButton btnProfilePage = new JButton("Profile Page");
		btnProfilePage.setBackground(new Color(255, 255, 224));
		btnProfilePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//what happens if profile page button is pressed
				dispose();
				(new StudentProfile(studentId)).setVisible(true);
			}
		});
		btnProfilePage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnProfilePage.setBounds(24, 292, 182, 35);
		contentPane.add(btnProfilePage);
		
		JButton btnSearchStations = new JButton("Search Stations");
		btnSearchStations.setBackground(new Color(255, 255, 224));
		btnSearchStations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//what happens if search ps stations button is pressed
				dispose();
				(new PS_Stations_Search(studentId,"STUDENT")).setVisible(true);
			}
		});
		btnSearchStations.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSearchStations.setBounds(24, 353, 182, 42);
		contentPane.add(btnSearchStations);
		
		JLabel lblYourReferences = new JLabel("Your preferences");
		lblYourReferences.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblYourReferences.setBounds(407, 297, 203, 59);
		contentPane.add(lblYourReferences);
		
		JButton btnNewButton = new JButton("View the results");
		btnNewButton.setBackground(new Color(255, 255, 224));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//what happens if view the results button is pressed
				dispose();
				(new PS_Stations_Student(studentId)).setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(24, 427, 182, 42);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(262, 369, 580, 127);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		
		
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ps_database","root","");
			Statement stmt = con.createStatement();
			String sql = "select priority,station_id as stationId,stationName,domain,accommodation,city,stipend from station,student_preference where station_id=stationId AND sid='"+studentId+"' ";
			ResultSet rs = stmt.executeQuery(sql);

				table.setModel(DbUtils.resultSetToTableModel(rs));
				
				JButton btnLogOut = new JButton("Log Out");
				btnLogOut.setBackground(new Color(255, 255, 224));
				btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 18));
				btnLogOut.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						(new LogIn()).setVisible(true);
					}
				});
				btnLogOut.setBounds(717, 127, 125, 35);
				contentPane.add(btnLogOut);
				
				JLabel lblNewLabel = new JLabel("New label");
				lblNewLabel.setIcon(new ImageIcon("D:\\dbms\\psms.PNG"));
				lblNewLabel.setBounds(24, 33, 305, 71);
				contentPane.add(lblNewLabel);
			
			
			
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(150);
		columnModel.getColumn(1).setPreferredWidth(150);
		columnModel.getColumn(2).setPreferredWidth(180);
		columnModel.getColumn(3).setPreferredWidth(150);
		columnModel.getColumn(4).setPreferredWidth(180);
		columnModel.getColumn(5).setPreferredWidth(180);
		columnModel.getColumn(6).setPreferredWidth(180);
	}
}
