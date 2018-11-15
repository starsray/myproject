package view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import entity.Table_MovieType;
import entity.dto.MovieDto;
import service.MovieService;
import service.Impl.MovieServiceImpl;
import service.Impl.MovietypeServiceImpl;
import util.ImgUtil;

public class UI_mainView extends JFrame {

	private JPanel contentPane;
	private JTree tree;
	private JTabbedPane tb_main;

	private Dimension srcsize;
	private JPanel panel;
	private JTextField tx_name;
	protected static String name;
	protected static String uname;
	protected static int tid;
	UI_Login uilog;
	File f;
	URI uri;
	URL url;
	boolean flag = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_mainView frame = new UI_mainView();
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
	public UI_mainView() {
		setBackground(new Color(204, 255, 204));
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/timg1.jpg"));
		setTitle("\u7535\u5F71\u7968\u7BA1\u7406\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		srcsize = Toolkit.getDefaultToolkit().getScreenSize();// ��ȡ��Ļ�ߴ�

		setBounds(0, 0, srcsize.width, srcsize.height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(5, 5, 1894, 1032);
		splitPane.setResizeWeight(0.1);// ���ô������
		contentPane.add(splitPane);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		tb_main = new JTabbedPane(JTabbedPane.TOP);
		tb_main.setBackground(new Color(0, 139, 139));
		splitPane.setRightComponent(tb_main);

		JPanel panel_1 = new JPanel();
		tb_main.addTab("��ӭ��", null, panel_1, null);

		InputStream is = null;

		try {
			is = new FileInputStream("img/8.jpg");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		ImageIcon background = new ImageIcon(ImgUtil.getIcon(is, srcsize.height, (int) (srcsize.width * 0.9)));

		panel_1.setLayout(null);

		/**
		 * ���û�ӭ��������
		 */
		JLabel label_1 = new JLabel("\u6B22");
		label_1.setForeground(new Color(255, 20, 147));
		label_1.setFont(new Font("��������", Font.BOLD | Font.ITALIC, 50));
		label_1.setBounds(122, 78, 119, 109);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("\u8FCE");
		label_2.setForeground(new Color(255, 20, 147));
		label_2.setFont(new Font("��������", Font.BOLD | Font.ITALIC, 50));
		label_2.setBounds(292, 184, 119, 109);
		panel_1.add(label_2);

		JLabel label_3 = new JLabel("\u60A8");
		label_3.setForeground(new Color(255, 20, 147));
		label_3.setFont(new Font("��������", Font.BOLD | Font.ITALIC, 50));
		label_3.setBounds(542, 131, 119, 109);
		panel_1.add(label_3);

		UI_mainView.this.uname = uilog.c_name;
		JLabel label_4 = new JLabel(uilog.c_name);
		label_4.setForeground(new Color(255, 20, 147));
		label_4.setFont(new Font("��������", Font.BOLD | Font.ITALIC, 50));
		label_4.setBounds(785, 209, 204, 109);
		panel_1.add(label_4);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, (int) (srcsize.width * 0.9), srcsize.height);
		lblNewLabel.setIcon(background);
		panel_1.add(lblNewLabel);

		JPanel imgpanel = (JPanel) this.getContentPane();
		imgpanel.setOpaque(false);
		/**
		 * ���������¼�
		 */
		tree = new JTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				TreePath path = tree.getClosestPathForLocation(e.getX(), e.getY());
				tree.setSelectionPath(path);

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				Object obj = node.getUserObject();
				// �ж��Ƿ��Ǹ��ڵ��ַ���
				if (obj instanceof Table_MovieType) {
					Table_MovieType bt = (Table_MovieType) obj;
					// ��ȡѡ��� ������
					int count = tb_main.getTabCount();
					// System.out.println(count);
					for (int i = 0; i < count; i++) {
						// �õ�ѡ�������
						UI_mainView.this.tid = bt.getTid();
						String name = tb_main.getTitleAt(i);
						if (name.equals(bt.getTname())) {
							// ������ͬ��������Ӧ ��ѡ���
							tb_main.setSelectedIndex(i);
							return;
						}
					}
					// System.out.println("tid"+tid);
					switch (bt.getTid()) {
					case 1:
						UI_hotMovie uihot = new UI_hotMovie();
						tb_main.add(uihot, bt.getTname());
						break;

					case 2:
						UI_LoveMovie uil = new UI_LoveMovie();
						tb_main.add(uil, bt.getTname());
						break;
					case 3:
						UI_ActionMovie uia = new UI_ActionMovie();
						tb_main.add(uia, bt.getTname());
						break;
					case 4:
						UI_ThrillerMovie uit = new UI_ThrillerMovie();
						tb_main.add(uit, bt.getTname());
						break;
					case 5:
						UI_SuspenseMovie uis = new UI_SuspenseMovie();
						tb_main.add(uis, bt.getTname());
						break;
					case 6:
						UI_Comedy uic = new UI_Comedy();
						tb_main.add(uic, bt.getTname());
						break;
					default:
						break;
					}
					// ÿ�ζ���ѡ�����һ��
					tb_main.setSelectedIndex(tb_main.getTabCount() - 1);
				}
			}
		});
		tree.setVisibleRowCount(8);

		tree.setBackground(new Color(240, 255, 255));
		tree.setForeground(Color.WHITE);
		tree.setFont(new Font("��Բ", Font.BOLD, 25));
		scrollPane.setColumnHeaderView(tree);
		intoBook();

		setUI(tree);

		panel = new JPanel();

		panel.setBackground(new Color(240, 255, 240));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("\u5F71\u7247\u67E5\u8BE2:");
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1.setBackground(new Color(255, 215, 0));
		lblNewLabel_1.setFont(new Font("��Բ", Font.BOLD, 15));
		lblNewLabel_1.setBounds(11, 73, 163, 30);
		panel.add(lblNewLabel_1);

		tx_name = new JTextField();
		tx_name.setFont(new Font("����", Font.PLAIN, 15));
		tx_name.setBounds(11, 113, 154, 37);
		panel.add(tx_name);
		tx_name.setColumns(10);

		JButton btnNewButton_1 = new JButton("\u67E5\u8BE2");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				UI_mainView.this.name = tx_name.getText();
				MovieService ms = new MovieServiceImpl();
				List<MovieDto> list = ms.findByShowName("m_name", name);
				int count = tb_main.getTabCount();
				if (tx_name.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�������Ӱ����");
				} else if (list.size() == 0) {
					JOptionPane.showMessageDialog(null, "��Ǹ��ʱû�и�ӰƬ");
				} else {
					String name1 = null;
					int i = 0;
					UI_Find uif = new UI_Find();
					for (i = 0; i < count; i++) {
						name1 = tb_main.getTitleAt(i);
					}
					if (tx_name.getText().equals(name1)) {
						// ����������
						return;
					} else {
						tb_main.add(uif, tx_name.getText());
					}
					tb_main.setSelectedIndex(tb_main.getTabCount() - 1);
				}
			}
		});

		btnNewButton_1.setFont(new Font("��Բ", Font.BOLD, 13));
		btnNewButton_1.setBounds(54, 160, 79, 23);
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u6211\u7684");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				UI_MyMovie myMovie = new UI_MyMovie(UI_Login.customer);
				myMovie.setVisible(true);
			}
		});
		btnNewButton_2.setFont(new Font("��Բ", Font.BOLD, 15));

		btnNewButton_2.setBounds(54, 259, 93, 23);
		panel.add(btnNewButton_2);
		/**
		 * ����
		 */
		JButton button_1 = new JButton("\u8BBE\u7F6E");
		button_1.setFont(new Font("��Բ", Font.BOLD, 15));
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				UI_Setup uis = new UI_Setup(UI_Login.customer);
				setLocationRelativeTo(null);
				uis.setVisible(true);
			}
		});
		button_1.setBounds(54, 349, 93, 23);
		panel.add(button_1);
		/**
		 * ����ͼ��
		 */
		JLabel label = new JLabel();
		Image img = null;

		try {
			img = ImgUtil.getIcon(new FileInputStream("img/musicopen.jpg"), 50, 46);
			label.setIcon(new ImageIcon(img));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent c) {

				if (flag) {
					try {
						Image img = ImgUtil.getIcon(new FileInputStream("img/musiccolse.jpg"), 50, 46);
						label.setIcon(new ImageIcon(img));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					start();
					flag = false;

				} else {

					try {
						AudioClip aau;
						aau = Applet.newAudioClip(url);
						aau.stop();
						Image img = ImgUtil.getIcon(new FileInputStream("img/musicopen.jpg"), 50, 46);
						label.setIcon(new ImageIcon(img));

						stop();
						flag = true;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

				}

			}

		});
		label.setBounds(54, 402, 50, 46);
		panel.add(label);

	}

	/**
	 * ��������
	 */
	void start() { // ע�⣬javaֻ�ܲ����������ʣ���.wav���ָ�ʽ
		try {
			f = new File("img/ҹ�յļž�.wav"); // ����·��
			uri = f.toURI();
			url = uri.toURL(); // ����·��

			AudioClip aau;
			aau = Applet.newAudioClip(url);
			// ����ѭ��
			aau.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void stop() { // ע�⣬javaֻ�ܲ����������ʣ���.wav���ָ�ʽ
		try {
			f = new File("img/ҹ�յļž�.wav"); // ����·��
			uri = f.toURI();
			url = uri.toURL(); // ����·��

			AudioClip aau;
			aau = Applet.newAudioClip(url);
			// ����ѭ��
			aau.stop();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������
	 */
	public void intoBook() {
		MovietypeServiceImpl ms = new MovietypeServiceImpl();
		List<Table_MovieType> plist = ms.findAll();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("��Ӱ����");

		for (Table_MovieType typ : plist) {
			DefaultMutableTreeNode dtm_p = new DefaultMutableTreeNode(typ);
			root.add(dtm_p);
		}

		DefaultTreeModel dtm = new DefaultTreeModel(root);
		tree.setModel(dtm);
	}

	/**
	 * Ϊ���ڵ�����ͼ��
	 * 
	 * @param tree
	 */
	public static void setUI(JTree tree) {

		// ���չ��ʱ��ͼ��
		// Icon ExpandedIcon = new
		// ImageIcon("img/tree_elbow.png");
		// ����۵�ʱ��ͼ��
		InputStream is;
		Image im = null;
		try {
			is = new FileInputStream(new File("img/timg.jpg"));
			im = ImgUtil.getIcon(is, 15, 15);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Icon CollapsedIcon = new ImageIcon("img/tree_folder_open.gif");
		// Ҷ�ڵ��ͼ�꣬Ҳ��������û���ӽ��Ľڵ�ͼ��
		Icon LeafIcon = new ImageIcon(im);
		// ��Ҷ�ڵ�ر�ʱ��ͼ�꣬Ҳ�����������ӽ��Ľڵ�ͼ��
		Icon ClosedIcon = new ImageIcon("img/tree_folder.gif");
		// ��Ҷ�ڵ��ʱ��ͼ�꣬Ҳ�����������ӽ��Ľڵ�ͼ��
		InputStream is2;
		Image im2 = null;
		try {
			is2 = new FileInputStream(new File("img/timg2.jpg"));
			im2 = ImgUtil.getIcon(is2, 18, 18);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Icon OpenedIcon = new ImageIcon(im2);

		DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) (tree.getCellRenderer());
		render.setLeafIcon(LeafIcon);
		render.setClosedIcon(ClosedIcon);
		render.setOpenIcon(OpenedIcon);

		BasicTreeUI ui = (BasicTreeUI) (tree.getUI());
		ui.setCollapsedIcon(CollapsedIcon);
	}
}