import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class liste extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JTextField textField_1;
	private DefaultTableModel model;
	private JTextField search;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					liste frame = new liste();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public liste() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 511);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 11, 547, 452);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(85, 83, 248, 261);
		panel.add(scrollPane);
		

		 model = new DefaultTableModel(new Object[]{"No", "Sanatçı", "Etkinlik"}, 0);
	     table = new JTable(model);
	     try {
			ArrayList<sanatçı> sanatçılar= getsanatçı();
			for(sanatçı s:sanatçılar) {
				Object []row= {s.getId(),
						s.getAd(),
						s.getEtkinlik()};
				model.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(table);
		
		search = new JTextField();
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchkey=search.getText();
				TableRowSorter <DefaultTableModel> trs=new TableRowSorter <DefaultTableModel>(model);
				table.setRowSorter(trs);
				trs.setRowFilter(RowFilter.regexFilter(searchkey));
			}
		});
		search.setBounds(388, 34, 138, 20);
		panel.add(search);
		search.setColumns(10);
		
		
	}
	
	
	public ArrayList<sanatçı> getsanatçı() throws SQLException{
		Connection con=null;
		DBHelper helper=new DBHelper();
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<sanatçı> sanatcilar=null;
		
		try {
			con=helper.getConnection();
			st=con.prepareStatement("select * from sanatçı");
			rs=st.executeQuery();
			
			sanatcilar=new ArrayList<sanatçı>();
			while(rs.next()) {
				sanatcilar.add(new sanatçı(rs.getInt("sanatçı_id"),
						rs.getString("sanatçı_adı"),
						rs.getString("etkinlik_adı")));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			st.close();
			con.close();
		}
		return sanatcilar;
	}
	
	
	
}
