package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entity.Table_Customer;
import entity.Table_Hall;
import entity.Table_Movie;
import entity.Table_Order;
import entity.Table_Schedule;
import entity.Table_Seat;
import service.CustomerService;
import service.HallService;
import service.MovieService;
import service.OrderService;
import service.ScheduleService;
import service.SeatService;
import service.Impl.CustomerServiceImpl;
import service.Impl.HallServiceImpl;
import service.Impl.MovieServiceImpl;
import service.Impl.OrderServiceImpl;
import service.Impl.ScheduleServiceImpl;
import service.Impl.SeatServiceImpl;
import util.ImgUtil;
import util.castutil;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;

public class UI_Manager_MyMovie extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	Table_Customer customer;
	DefaultTableModel tableModel;
	CustomerService customerService = new CustomerServiceImpl();
	OrderService orderService = new OrderServiceImpl();
	public static Table_Order selectOrder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UI_Manager_MyMovie dialog = new UI_Manager_MyMovie();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UI_Manager_MyMovie(Table_Customer c) {
		this();
		customer = c;
		List<Table_Order> orderList = orderService.getOrderByCid(c.getCid());
		initTable(orderList);
	}

	/**
	 * Create the dialog.
	 */
	public UI_Manager_MyMovie() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("\u5BA2\u6237\u5F71\u7968\u7BA1\u7406");
		setBounds(100, 100, 406, 483);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\timg1.jpg"));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		customer = UI_Manager.customer;

			JPanel panel = new JPanel();
			panel.setBounds(0, 15, 384, 397);
			contentPanel.add(panel);
			panel.setLayout(null);

			JScrollPane scrollPane = new JScrollPane();

			scrollPane.setBounds(15, 43, 354, 290);
			panel.add(scrollPane);

			/**
			 * 添加评价
			 * 比较放映时间与当前时间判断是否失效
			 * 如果没看就弹出提示窗口
			 * 如果看了就弹出评价窗口
			 */
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(e.getClickCount()==2) {
						dispose();
						int index=table.getSelectedRow();
						Object orderId=tableModel.getValueAt(index, 0);
						 selectOrder=orderService.getOrderById(castutil.castint(orderId));
						Object time=tableModel.getValueAt(index, 2);
						DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
							try {
								Date dt1=df.parse(castutil.castString(time));
								Date dt2=df.parse(df.format( new Date()));
								if(dt1.getTime()>dt2.getTime()) {								
									UI_Manager_ShowMovie showMovie=new UI_Manager_ShowMovie(selectOrder);
									showMovie.setVisible(true);
								}else {
									JOptionPane.showMessageDialog(null, "该影片已播出！");
									
								}
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

					}
				}

			});
			tableModel = new DefaultTableModel(new Object[][] {
				{null, null, null, null},
			},
			new String[] {
					"\u7968\u53F7","\u5F71\u7247\u540D\u79F0", "\u653E\u6620\u65F6\u95F4", "\u5730\u70B9", "\u5EA7\u4F4D"
			}
			);
			table.setModel(tableModel);
			table.setFont(new Font("微软雅黑", Font.PLAIN, 13));
			scrollPane.setViewportView(table);
			//退出按钮
			btn_exit = new JButton("\u9000\u51FA");
			btn_exit.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					dispose();
				}
			});
			btn_exit.setBounds(277, 363, 93, 34);
			panel.add(btn_exit);
		}



	MovieService movieSertvice = new MovieServiceImpl();
	HallService hallService = new HallServiceImpl();
	SeatService SeatService = new SeatServiceImpl();
	ScheduleService scheduleService = new ScheduleServiceImpl();
	private JButton btn_exit;

	// 表格初始化
	public void initTable(List<Table_Order> list) {
		int count = tableModel.getRowCount();
		for (int i = 0; i < count; i++) {
			tableModel.removeRow(0);
		}
		for (Table_Order order : list) {
			Table_Movie movie=movieSertvice.getbyId(order.getMid());
			Table_Schedule schedule=scheduleService.getScheduleBySid(order.getSid());
			Table_Seat seat=SeatService.getSeatBySid(order.getSeat_id());
			Table_Hall hall=hallService.getHallById(seat.getHid());
			tableModel.addRow(new Object[] {
					order.getOid(),
					movie.getM_name(),
					schedule.getS_begindate(),
					hall.getHdesc(),
					seat.getSeat_row()+"排"+seat.getSeat_column()+"座"});
		}

	}

}
