package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import entity.Table_Hall;
import entity.Table_Level;
import entity.Table_Movie;
import entity.Table_Order;
import entity.Table_Schedule;
import entity.Table_Seat;
import service.CustomerService;
import service.HallService;
import service.LevelService;
import service.MovieService;
import service.OrderService;
import service.ScheduleService;
import service.SeatService;
import service.Impl.CustomerServiceImpl;
import service.Impl.HallServiceImpl;
import service.Impl.LevelServiceImpl;
import service.Impl.MovieServiceImpl;
import service.Impl.OrderServiceImpl;
import service.Impl.ScheduleServiceImpl;
import service.Impl.SeatServiceImpl;
import util.DBUtil;

public class UI_Manager_ShowMovie extends JFrame {

	private JPanel contentPane;
	private JTextField text_movieName;
	private JTextField text_role;
	private JTextField text_Author;
	private JTextField text_beginTime;
	private JLabel lb_img;
	private JTextPane text_movieDesc;
	private JButton btn_return;
	OrderService orderService = new OrderServiceImpl();
	MovieService movieSertvice = new MovieServiceImpl();
	HallService hallService = new HallServiceImpl();
	SeatService SeatService = new SeatServiceImpl();
	ScheduleService scheduleService = new ScheduleServiceImpl();
	protected static Table_Order selectOrder;
	public int i;
	public int j;
	public int size;
	int isactiveID;
	String upSql;
	String sql;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_Manager_ShowMovie frame = new UI_Manager_ShowMovie();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UI_Manager_ShowMovie(Table_Order order) {
		this();
		selectOrder = order;
		Table_Movie movie = movieSertvice.getbyId(order.getMid());
		Table_Schedule schedule = scheduleService.getByMid(order.getMid());
		Table_Seat seat = SeatService.getSeatBySid(order.getSeat_id());
		Table_Hall hall = hallService.getHallById(seat.getHid());
		text_movieName.setText(movie.getM_name());
		text_role.setText(movie.getM_mainactor());
		text_Author.setText(movie.getM_director());
		text_beginTime.setText(schedule.getS_begindate());
		text_movieDesc.setText(movie.getM_desc());

		try {
			InputStream is = new FileInputStream(movie.getM_image());
			lb_img.setIcon(new ImageIcon(getIcon(is)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create the frame.
	 */
	public UI_Manager_ShowMovie() {
		setTitle("\u7535\u5F71\u8BE6\u60C5");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 554);
		setLocationRelativeTo(null);// ����
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lb_img = new JLabel("\u7535\u5F71\u6D77\u62A5");
		lb_img.setBounds(10, 31, 273, 320);
		contentPane.add(lb_img);

		text_movieName = new JTextField();
		text_movieName.setEditable(false);
		text_movieName.setBounds(413, 31, 117, 27);
		contentPane.add(text_movieName);
		text_movieName.setColumns(10);

		text_role = new JTextField();
		text_role.setEditable(false);
		text_role.setBounds(413, 72, 117, 27);
		contentPane.add(text_role);
		text_role.setColumns(10);

		JLabel label = new JLabel("\u5BFC\u6F14:");
		label.setBounds(327, 114, 61, 27);
		contentPane.add(label);

		text_Author = new JTextField();
		text_Author.setEditable(false);
		text_Author.setBounds(413, 114, 117, 27);
		contentPane.add(text_Author);
		text_Author.setColumns(10);

		JLabel label_1 = new JLabel("\u4E0A\u6620\u65F6\u95F4:");
		label_1.setBounds(327, 156, 83, 27);
		contentPane.add(label_1);

		text_beginTime = new JTextField();
		text_beginTime.setEditable(false);
		text_beginTime.setBounds(413, 156, 117, 27);
		contentPane.add(text_beginTime);
		text_beginTime.setColumns(10);

		JLabel label_3 = new JLabel("\u5F71\u7247\u6982\u8FF0:");
		label_3.setBounds(327, 198, 99, 23);
		contentPane.add(label_3);

		text_movieDesc = new JTextPane();
		text_movieDesc.setEditable(false);
		text_movieDesc.setBounds(327, 236, 226, 115);
		contentPane.add(text_movieDesc);
		// ��Ʊ��ť
		btn_return = new JButton("\u786E\u8BA4\u9000\u7968");
		btn_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_return.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				LevelService levelService = new LevelServiceImpl();
				Table_Level level = levelService.getLevelById(UI_Manager.customer.getClid());
				boolean isReturn = orderService.deleteOrder(UI_Manager_MyMovie.selectOrder.getOid());
				Table_Schedule schedule = scheduleService.getByMid(selectOrder.getMid());
				if (isReturn == true) {
					JOptionPane.showMessageDialog(null, "��Ʊ�ɹ�!");
					String sql = "select begin1,begin2,begin3 from table_seat where hid=" + schedule.getHid()
							+ " and seat_row=1 and seat_column=1";
					

					//SeatService.updateSeatState(UI_Manager_MyMovie.selectOrder.getSeat_id());

					ResultSet rs = DBUtil.executequery(sql);
					ArrayList<String> bTime = new ArrayList<String>();
					try {
						while (rs.next()) {
							String b1 = rs.getDate("begin1").toString() + " " + rs.getTime("begin1");
							String b2 = rs.getDate("begin2").toString() + " " + rs.getTime("begin2");
							String b3 = rs.getDate("begin3").toString() + " " + rs.getTime("begin3");
							bTime.add(b1);
							bTime.add(b2);
							bTime.add(b3);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						DBUtil.closeAll();
					}
					for (int i = 0; i < bTime.size(); i++) {
						if (schedule.getS_begindate().equals(bTime.get(0))) {
							// System.out.println(schedule.getS_begindate().equals(bTime.get(0)));
							isactiveID = 1;
						} else if (schedule.getS_begindate().equals(bTime.get(1))) {
							// System.out.println(schedule.getS_begindate().equals(bTime.get(1)));
							isactiveID = 2;
						} else if (schedule.getS_begindate().equals(bTime.get(2))) {
							// System.out.println(schedule.getS_begindate().equals(bTime.get(2)));
							isactiveID = 3;
						}
					}
					System.out.println(isactiveID);
					SeatService.updateSeatState(isactiveID, schedule.getHid(), UI_Manager_MyMovie.selectOrder.getSeat_id());
					double returnMOney = (UI_Manager.customer.getCmoney()
							+ schedule.getS_price() * level.getLdiscount() * 0.8);
					UI_Manager.customer.setCmoney(returnMOney);
					CustomerService customerService = new CustomerServiceImpl();
					customerService.updateCustomer(UI_Manager.customer);

					dispose();
				}

			}
		});

		btn_return.setBounds(226, 426, 117, 36);
		contentPane.add(btn_return);

		JLabel label_2 = new JLabel("\u7247\u540D:");
		label_2.setBounds(327, 31, 71, 27);
		contentPane.add(label_2);

		JLabel label_5 = new JLabel("\u4E3B\u6F14:");
		label_5.setBounds(327, 72, 83, 27);
		contentPane.add(label_5);
	}

	// ͼƬ����
	public BufferedImage getIcon(InputStream is) {
		BufferedImage bufferedImage = null;
		try {
			Image image = ImageIO.read(is);
			bufferedImage = new BufferedImage(273, 320, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(image, 0, 0, 273, 320, null);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return bufferedImage;

	}
}
