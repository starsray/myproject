package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import dao.Impl.MovieDaoImpl;
import dao.Impl.OrderDaoImpl;
import entity.Table_Customer;
import entity.Table_Order;
import entity.Table_Schedule;
import util.DBUtil;
import util.OrderPrint;
import util.Time;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;

public class UI_Order extends JFrame{

	private JPanel contentPane;
	private ResultSet rs;
	private String seats="";
	private Table_Customer cs;
	private Table_Schedule sch;
	public static Table_Order order;
	public OrderDaoImpl odImpl;
	public static List<Integer> list;
	private JLabel lab_exception;
	OrderPrint op;
	Book book;
	int count;
	PrinterJob printerJob = PrinterJob.getPrinterJob();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					new UI_Order();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public UI_Order() {
		odImpl = new OrderDaoImpl();
		order = new Table_Order();
		cs = UI_Login.getCustomer();
		sch = UI_Schule.getMovieInfo();
		//此处添加sch对象 从哪里获得sch
		setTitle("订单信息");
		setResizable(false);
		setBounds(300, 150, 241, 448);
		contentPane = new JPanel();
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\timg1.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 217, 399);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\u8BA2\u5355\u7F16\u53F7\uFF1A");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setBounds(10, 10, 80, 25);
		panel.add(label);
		
		JLabel lblNewLabel = new JLabel(getOrder());
		order.setOrder_number(getOrder());
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 40, 191, 25);
		panel.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\u7528\u6237\u540D\uFF1A");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setBounds(10, 75, 48, 25);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u5F71\u5385\u540D\u79F0\uFF1A");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_2.setBounds(10, 180, 65, 25);
		panel.add(label_2);
		
		JLabel lblNewLabel_1 = new JLabel(String.valueOf(sch.getHid())+" 号厅");
		
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(80, 180, 87, 25);
		panel.add(lblNewLabel_1);
		
		JLabel label_3 = new JLabel("\u8D2D\u7968\u65E5\u671F\uFF1A");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_3.setBounds(10, 110, 60, 25);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("\u7535\u5F71\u540D\u79F0\uFF1A");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_4.setBounds(10, 145, 60, 25);
		panel.add(label_4);
		
		JLabel lblNewLabel_2 = new JLabel(new MovieDaoImpl().getbyId(sch.getMid()).getM_name());
		//通过sch对象获得对象 获得电影名称
		order.setMid(sch.getMid());
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(70, 145, 131, 25);
		panel.add(lblNewLabel_2);
		
		
		JLabel lblNewLabel_3 = new JLabel();
		//电影的sid
		order.setSid(sch.getSid());
		Time.setDT("yyyy-MM-dd", lblNewLabel_3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		order.setBuydate(sdf.format(new Date()));
		lblNewLabel_3.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(75, 110, 111, 25);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel(cs.getCname());
		order.setCid(cs.getCid());
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(60, 75, 60, 25);
		panel.add(lblNewLabel_4);
		
		JLabel label_5 = new JLabel("\u89C2\u5F71\u5E2D\u4F4D\uFF1A");
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_5.setBounds(10, 215, 65, 25);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("\u5B9E\u9645\u4EF7\u683C\uFF1A");
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_6.setBounds(10, 280, 65, 25);
		panel.add(label_6);
		
		JLabel lblNewLabel_6 = new JLabel(getSeat());
		order.setSeat_id(list.get(0));
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(10, 245, 200, 25);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_5 = new JLabel(String.valueOf(UI_Seat.s_price)+"元");
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(80, 280, 74, 25);
		panel.add(lblNewLabel_5);
		
		JLabel label_8 = new JLabel("\u4F1A\u5458\u60A0\u4EAB\u4EF7\uFF1A");
		label_8.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_8.setBounds(10, 310, 74, 25);
		panel.add(label_8);
		
		JLabel lblNewLabel_7 = new JLabel(String.valueOf(UI_Seat.s_price_discount)+"元");
		lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_7.setBounds(94, 310, 92, 25);
		panel.add(lblNewLabel_7);
		
		JButton button = new JButton("\u6253\u5370");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==button) {
					System.out.println("我是打印按钮");
					pr();
				}
			}
		});
		button.setBounds(61, 345, 93, 23);
		panel.add(button);
		
		lab_exception = new JLabel("");
		lab_exception.setFont(new Font("微软雅黑", Font.ITALIC, 12));
		lab_exception.setHorizontalAlignment(SwingConstants.CENTER);
		lab_exception.setForeground(new Color(255, 0, 0));
		lab_exception.setBounds(10, 374, 191, 18);
		panel.add(lab_exception);
		setVisible(true);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i)!=108) {
			order.setSeat_id(list.get(i));
			odImpl.addOrder(order);
			}
		}
		
	
	}
	public String getOrder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date())+cs.getCphone().substring(7);
	}
	public String getSeat() {
		list = new ArrayList<>();
		String sql = "select * from table_select where cid=(select MAX(cid) from table_select)";
		rs = DBUtil.executequery(sql);
		try {
			while(rs.next()) {
				rs.getInt("c_seat_id1");
				rs.getInt("c_seat_id2");
				rs.getInt("c_seat_id3");
				rs.getInt("c_seat_id4");
				if(rs.getInt("c_seat_id1")!=108 || rs.getInt("c_seat_id2")!=108 || rs.getInt("c_seat_id3")!=108 || rs.getInt("c_seat_id4")!=108) {
					list.add(rs.getInt("c_seat_id1"));
					list.add(rs.getInt("c_seat_id2"));
					list.add(rs.getInt("c_seat_id3"));
					list.add(rs.getInt("c_seat_id4"));
				}
				if(list.size()==1) {
					seats = getSeat(list.get(0));
				}
				if(list.size()==2) {
					seats = getSeat(list.get(0))+getSeat(list.get(1))+"  ";
				}
				if(list.size()==3) {
					seats = getSeat(list.get(0))+"  "+getSeat(list.get(1))+"  "+getSeat(list.get(2));
				}
				if(list.size()==4) {
					seats = getSeat(list.get(0))+"  "+getSeat(list.get(1))+"  "+getSeat(list.get(2))+"  "+getSeat(list.get(3));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seats;
	}
	public String getSeat(int id) {
		String str = "";
		for(int seat_row = 0;seat_row < 9;seat_row++) {
			for(int seat_col = 0;seat_col<12;seat_col++) {
				if(id % 12 == seat_col && (int)(id / 12) == seat_row) {
					str = (seat_row+1)+"排"+(seat_col+1)+"座";
				}
			}
		}
		return str;
	}
	public List<Integer> getSeatId() {
		List<Integer> list = new ArrayList<>();
		String sql = "select seat_id from table_select where cid=(select MAX(cid) from table_select)";
		rs = DBUtil.executequery(sql);
		try {
			while(rs.next()) {
				rs.getInt("c_seat_id1");
				rs.getInt("c_seat_id2");
				rs.getInt("c_seat_id3");
				rs.getInt("c_seat_id4");
				if(rs.getInt("c_seat_id1")!=108 || rs.getInt("c_seat_id2")!=108 || rs.getInt("c_seat_id3")!=108 || rs.getInt("c_seat_id4")!=108) {
					list.add(rs.getInt("c_seat_id1"));
					list.add(rs.getInt("c_seat_id2"));
					list.add(rs.getInt("c_seat_id3"));
					list.add(rs.getInt("c_seat_id4"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static Table_Order getCustomerOrder() {
		return order;
	}
	public void pr() {
		int height = 175 + 4 * 15 + 20;
		
		//打印格式
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT);

		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		
		p.setSize(230, height);
		p.setImageableArea(5, -20, 230, height + 20);
		pf.setPaper(p);
		//通俗的讲是书
//				Book book = new Book();
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
//				OrderPrint op = new OrderPrint();
//				book.append(op, pf);
		book = new Book();
		op = new OrderPrint(count);
		System.out.println("打印了"+count);
		if(count == list.size()-2) {
			int num=JOptionPane.showConfirmDialog(null, "打印已结束", "提示信息", JOptionPane.WARNING_MESSAGE);
			printerJob.cancel();
//			if(num==0) {
//				
//			}
//			else {
//				dispose();
//			}
			//System.exit(0);
		}else {
			count++;
		}
		book.append(op, pf);
		// 获取打印服务对象
		
		printerJob.setPageable(book);

		try {
			printerJob.print();
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	
	
}