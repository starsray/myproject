package view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Table_Customer;
import service.CustomerService;
import service.Impl.CustomerServiceImpl;
import util.castutil;
import java.awt.Dialog.ModalityType;

public class UI_Customer_Addmoney extends JDialog {

	private JPanel contentPane;
	private JLabel lb_rest;
	private JSpinner spinner;
	private JButton btn_sure;
	private JButton btn_exit;
	Table_Customer customer;
	protected static double addMoney;
	protected static double  newmoney;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_Customer_Addmoney frame = new UI_Customer_Addmoney();
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
	public UI_Customer_Addmoney() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 275, 300);
		contentPane = new JPanel();
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\timg1.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5BA2\u6237\u4F59\u989D:");
		label.setBounds(10, 36, 63, 15);
		contentPane.add(label);
		
		lb_rest = new JLabel("");
		lb_rest.setEnabled(false);
		lb_rest.setBounds(83, 36, 78, 15);
		contentPane.add(lb_rest);
		
		JLabel label_1 = new JLabel("\u5145\u503C\u91D1\u989D:");
		label_1.setBounds(10, 92, 63, 15);
		contentPane.add(label_1);
		
		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(castutil.castDouble(spinner.getValue())>0) {
					addMoney=castutil.castDouble(spinner.getValue());
					double newMoney=UI_GeRen.customer.getCmoney()+castutil.castDouble(spinner.getValue());
					UI_Customer_Addmoney.this.newmoney=newMoney;
					lb_rest.setText(newMoney+"");
					
				}else {
					JOptionPane.showMessageDialog(null, "金额不符合");
					addMoney=0;
					return;
				}
				
			}
		});
		spinner.setBounds(83, 89, 78, 22);
		contentPane.add(spinner);
		
		btn_sure = new JButton("\u786E\u5B9A\u5145\u503C");
		btn_sure.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				UI_Pay pay=new UI_Pay();
				//dispose();
			//	pay.setLocationRelativeTo(null);
				pay.setVisible(true);
				pay.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						// 当付款窗体销毁时(付款成功)
						CustomerService customerService=new CustomerServiceImpl();
						UI_GeRen.customer.setCmoney(castutil.castDouble(lb_rest.getText()));
						if(customerService.updateCustomer(UI_GeRen.customer)==true) {
								int num=JOptionPane.showConfirmDialog(
										null,//窗体显示的地方
										"是否继续充值?",//提示消息
										"客户操作提示",//提示标题
										JOptionPane.YES_NO_OPTION,//按钮信息
										JOptionPane.QUESTION_MESSAGE//图片类型
										);
								if(num==0) {
									//充值成功 ,清空,继续充值
									//spinner.setValue("");
									
								}else {
									dispose();//销毁当前窗体
									
								}
							}
						
						}	
					
				});
			}
		});
		btn_sure.setBounds(10, 207, 93, 23);
		contentPane.add(btn_sure);
		
		btn_exit = new JButton("\u9000\u51FA");
		btn_exit.setBounds(140, 207, 93, 23);
		contentPane.add(btn_exit);
		lb_rest.setText(UI_GeRen.customer.getCmoney()+"");
	}
}
