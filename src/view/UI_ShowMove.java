package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
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
import java.util.List;

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

public class UI_ShowMove extends JFrame {

	private JPanel contentPane;
	private JTextField text_movieName;
	private JTextField text_beginTime;
	private JTextField text_Author;
	private JTextField text_role;
	private JLabel lb_img;
	private JTextPane text_movieDesc;
	private JButton btn_return;
	OrderService orderService=new OrderServiceImpl();
	MovieService movieSertvice=new MovieServiceImpl();
	HallService hallService=new HallServiceImpl();
	SeatService SeatService=new SeatServiceImpl();
	ScheduleService scheduleService=new ScheduleServiceImpl();
	private JButton btn_change;
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
					UI_ShowMove frame = new UI_ShowMove();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public UI_ShowMove(Table_Order order) {
		this();
		selectOrder=order;
		Table_Movie movie=movieSertvice.getbyId(order.getMid());
		Table_Schedule schedule=scheduleService.getByMid(order.getMid());
		Table_Seat seat=SeatService.getSeatBySid(order.getSeat_id());
		Table_Hall hall=hallService.getHallById(seat.getHid());
		text_movieName.setText(movie.getM_name());
		text_beginTime.setText(schedule.getS_begindate());
		text_Author.setText(movie.getM_director());
		text_role.setText(movie.getM_mainactor());
		text_movieDesc.setText(movie.getM_desc());
		
		try {
		InputStream is=new FileInputStream(movie.getM_image());
		lb_img.setIcon(new ImageIcon(getIcon(is)));
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		}
		
	}
	/**
	 * Create the frame.
	 */
	
	public UI_ShowMove() {
		setTitle("\u7535\u5F71");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 516);
		contentPane = new JPanel();
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\timg1.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lb_img = new JLabel("\u7535\u5F71\u6D77\u62A5");
		lb_img.setBounds(10, 10, 273, 320);
		contentPane.add(lb_img);

		text_movieName = new JTextField();
		text_movieName.setEditable(false);
		text_movieName.setBounds(400, 13, 123, 21);
		contentPane.add(text_movieName);
		text_movieName.setColumns(10);

		text_beginTime = new JTextField();
		text_beginTime.setEditable(false);
		text_beginTime.setBounds(400, 51, 123, 21);
		contentPane.add(text_beginTime);
		text_beginTime.setColumns(10);

		JLabel label = new JLabel("\u5BFC\u6F14");
		label.setBounds(330, 99, 67, 15);
		contentPane.add(label);

		text_Author = new JTextField();
		text_Author.setEditable(false);
		text_Author.setBounds(400, 96, 123, 21);
		contentPane.add(text_Author);
		text_Author.setColumns(10);

		JLabel label_1 = new JLabel("\u4E3B\u6F14");
		label_1.setBounds(330, 139, 67, 15);
		contentPane.add(label_1);

		text_role = new JTextField();
		text_role.setEditable(false);
		text_role.setBounds(400, 136, 123, 21);
		contentPane.add(text_role);
		text_role.setColumns(10);

		JLabel label_3 = new JLabel("\u5F71\u7247\u6982\u8FF0:");
		label_3.setBounds(330, 190, 60, 15);
		contentPane.add(label_3);

		text_movieDesc = new JTextPane();
		text_movieDesc.setEditable(false);
		text_movieDesc.setBounds(327, 215, 196, 115);
		contentPane.add(text_movieDesc);

//		JButton button = new JButton("\u9009\u5EA7\u8D2D\u7968");
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				if(e.getSource() == button) {
//
//					//UI_Seat ui_Seat = new UI_Seat();
//					//ui_Seat.setVisible(true);
//				}
//
//			}
//		});
//		button.setBounds(31, 445, 151, 23);
//		contentPane.add(button);

		btn_return = new JButton("\u9000\u7968");
		btn_return.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				LevelService levelService=new LevelServiceImpl();
				Table_Level level=levelService.getLevelById(UI_Login.customer.getClid());
				boolean isReturn=orderService.deleteOrder(UI_MyMovie.selectOrder.getOid());
				Table_Schedule schedule=scheduleService.getByMid(selectOrder.getMid());
				if(isReturn==true) {
					JOptionPane.showMessageDialog(null, "退票成功!");
					String sql = "select begin1,begin2,begin3 from table_seat where hid="+schedule.getHid()+" and seat_row=1 and seat_column=1";
					System.out.println(sql);
					ResultSet rs = DBUtil.executequery(sql);
					List<String> bTime = new ArrayList<>();
					try {
						while(rs.next()) {
							String b1 = rs.getDate("begin1").toString()+" "+rs.getTime("begin1");
							String b2 = rs.getDate("begin2").toString()+" "+rs.getTime("begin2");
							String b3 = rs.getDate("begin3").toString()+" "+rs.getTime("begin3");
							bTime.add(b1);
							bTime.add(b2);
							bTime.add(b3);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						DBUtil.closeAll();
					}
					for(int i = 0;i < bTime.size();i++) {
						if(schedule.getS_begindate().equals(bTime.get(0))) {
							//System.out.println(schedule.getS_begindate().equals(bTime.get(0)));
							isactiveID = 1;
						}else if(schedule.getS_begindate().equals(bTime.get(1))){
							//System.out.println(schedule.getS_begindate().equals(bTime.get(1)));
							isactiveID = 2;
						}else if(schedule.getS_begindate().equals(bTime.get(2))) {
							//System.out.println(schedule.getS_begindate().equals(bTime.get(2)));
							isactiveID = 3;
						}
					}
					System.out.println(isactiveID);
					SeatService.updateSeatState(isactiveID,schedule.getHid(),UI_MyMovie.selectOrder.getSeat_id());
//					System.out.println(SeatService.updateSeatState(isactiveID,schedule.getHid(),UI_MyMovie.selectOrder.getSeat_id())+"淇敼鐘舵��");
//					String sql2="select * from table_seat  where hid="+schedule.getHid()+" and seat_id="+UI_MyMovie.selectOrder.getSeat_id()+"";
//					ResultSet rs2 = DBUtil.executequery(sql2);
//					try {
//						while(rs2.next()) {
//							System.out.println(rs2.getInt("seat_isactive1")+"搴т綅鐘舵��");
//							System.out.println(rs2.getInt("seat_isactive2")+"搴т綅鐘舵��");
//							System.out.println(rs2.getInt("seat_isactive3")+"搴т綅鐘舵��");
//							System.out.println(rs2.getInt("seat_isactive4")+"搴т綅鐘舵��");
//						}
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					double returnMOney=(UI_Login.customer.getCmoney()+schedule.getS_price()*level.getLdiscount()*0.8);
					UI_Login.customer.setCmoney(returnMOney);
					CustomerService customerService=new CustomerServiceImpl();
					customerService.updateCustomer(UI_Login.customer);
					dispose();
				}
			}
		});
		btn_return.setBounds(264, 445, 93, 23);
		contentPane.add(btn_return);
		
		JLabel label_2 = new JLabel("\u7535\u5F71\u540D\u79F0");
		label_2.setBounds(330, 13, 60, 15);
		contentPane.add(label_2);
		
		JLabel label_5 = new JLabel("\u4E0A\u6620\u65F6\u95F4");
		label_5.setBounds(330, 54, 60, 15);
		contentPane.add(label_5);
		

	}
	//??????
		public BufferedImage getIcon(InputStream is) {
			BufferedImage bufferedImage=null;
			try {
				Image image=ImageIO.read(is);
				bufferedImage=new BufferedImage(273,320,BufferedImage.TYPE_INT_RGB);
				bufferedImage.getGraphics().drawImage(image,0,0, 273,320, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bufferedImage;
			//initTable(List<Table_Order> list);

		}
		
		
}
