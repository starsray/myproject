package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;

import dao.MovieTypeDao;
import dao.Impl.MovieTypeDaoImpl;
import entity.Table_Movie;
import entity.Table_MovieType;
import service.MovieService;
import service.MovietypeService;
import service.Impl.MovieServiceImpl;
import service.Impl.MovietypeServiceImpl;
import util.castutil;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dialog.ModalityType;



public class UI_findMovie extends JDialog {

	private JPanel contentPane;
	private JTextField txt_moviename;
	private JButton bt_search;
	private JButton bt_reset;
	private JComboBox cb_movietype;
	private JScrollPane scrollPane;
	public static Table_Movie tmo=new Table_Movie();
	private  DefaultTableModel tbm;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_findMovie frame = new UI_findMovie();
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
	public UI_findMovie() {
		setModalityType(ModalityType.APPLICATION_MODAL);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 849, 471);
		contentPane = new JPanel();
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\timg1.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8BF7\u8F93\u5165\u7535\u5F71\u540D\uFF1A");
		label.setBounds(27, 29, 101, 27);
		contentPane.add(label);
		
		txt_moviename = new JTextField();
		txt_moviename.setBounds(138, 32, 127, 21);
		contentPane.add(txt_moviename);
		txt_moviename.setColumns(10);
		
		JLabel label_1 = new JLabel("\u8BF7\u9009\u62E9\u7535\u5F71\u7C7B\u578B\uFF1A");
		label_1.setBounds(287, 35, 106, 15);
		contentPane.add(label_1);
		
		cb_movietype = new JComboBox();
		cb_movietype.setModel(new DefaultComboBoxModel(new String[] {"-\u8BF7\u9009\u62E9\u7535\u5F71\u7C7B\u578B-", "\u52B1\u5FD7", "\u7231\u60C5"}));
		DefaultComboBoxModel dcb=new DefaultComboBoxModel<>();
		MovieService ms=new MovieServiceImpl();
		List<Table_Movie> lists=  ms.findAAll();
		MovieTypeDao mtd=new MovieTypeDaoImpl();
		dcb.addElement("-请选择类型-");
		MovietypeService mts=new MovietypeServiceImpl();
		List<Table_MovieType> tm1=mts.findAll();
		for (Table_MovieType table_MovieType : tm1) {
			dcb.addElement(table_MovieType.getTname());
		}
		cb_movietype.setModel(dcb);
		cb_movietype.setBounds(403, 32, 127, 21);
		contentPane.add(cb_movietype);
		
		 scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 96, 813, 235);
		contentPane.add(scrollPane);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()==2) {
					int i=table_1.getSelectedRow();
					String i1= castutil.castString( table_1.getValueAt(i, 0));
					MovieService mss=new MovieServiceImpl();
					Table_Movie tmo=mss.getbyName(i1);
					
					//TODO
					
					
					 UI_findMovie.this.tmo=tmo;
					UI_UpdateMovie uum=new UI_UpdateMovie(tmo);
					uum.setVisible(true);
					uum.addWindowListener(new WindowAdapter() {
						public void WindowClosed(WindowEvent e) {
							
						}
					});
					
				}
				
				
				
			}
		});
		
		
		
		//tbm=new DefaultTableModel();
		bt_search = new JButton("\u641C\u7D22");
		bt_search.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				//TODO
				MovieService ms=new MovieServiceImpl();
				MovietypeService mts=new MovietypeServiceImpl();
				
				tbm=new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"电影名", "演员", "类型", "评分", "电影简介","票价"
						}
					);
				table_1.setModel(tbm);

		
		if(!txt_moviename.getText().equals("")) {
			List<Table_Movie> lms=ms.findMovieByName(txt_moviename.getText());
			if(lms.isEmpty()) {
			JOptionPane.showMessageDialog(null, "查无与<"+txt_moviename.getText()+">相关的电影!请重新输入");
			txt_moviename.setText(null);
			}
			else {
			
			for (Table_Movie lm : lms) {
				tbm.addRow(new Object[]{
						lm.getM_name(),
						lm.getM_mainactor(),
						mts.getById(lm.getTid()).getTname(),
						lm.getM_score(),
						lm.getM_desc(),
						lm.getM_price(),
			}); }
		}}
		if(cb_movietype.getSelectedIndex()!=0&&cb_movietype.getSelectedIndex()!=1) {
			int tid=cb_movietype.getSelectedIndex();
			List<Table_Movie> lms1=ms.findMovieByType(tid);
			
			for (Table_Movie lm1 : lms1) {
				tbm.addRow(new Object[]{
						lm1.getM_name(),
						lm1.getM_mainactor(),
						mts.getById(lm1.getTid()).getTname(),
						lm1.getM_score(),
						lm1.getM_desc(),
						lm1.getM_price(),
			}); 					
			
		}
		}
		
		
		if(cb_movietype.getSelectedIndex()==1){
			List<Table_Movie> lms=ms.findMovieByHot(1);
			
			for (Table_Movie lm : lms) {
				tbm.addRow(new Object[]{
						lm.getM_name(),
						lm.getM_mainactor(),
						mts.getById(lm.getTid()).getTname(),
						lm.getM_score(),
						lm.getM_desc(),
						lm.getM_price(),
						
			}); 		
			
			
		}
			
			
			
		}
			
			
			
			
		
				
				
				
				
				
				
				
				
				
			}});
		tbm=new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"电影名", "演员", "类型", "评分", "电影简介","票价"
				}
			);
		table_1.setModel(tbm);
		scrollPane.setViewportView(table_1);
		
		
		bt_search.setBounds(575, 31, 93, 23);
		contentPane.add(bt_search);
		
		bt_reset = new JButton("\u91CD\u7F6E");
		bt_reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				tbm=new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"电影名", "演员", "类型", "评分", "电影简介","票价"
						}
					);
				table_1.setModel(tbm);
				txt_moviename.setText(null);
				cb_movietype.setSelectedIndex(0);
				
				
				
			}
		});
		bt_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		bt_reset.setBounds(701, 31, 93, 23);
		contentPane.add(bt_reset);
		
	
	
	
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
