import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
public class ChatView extends JFrame{
	private JPanel contentPane;
	
	private JButton btn_send;
	public  JPanel txtarea;
	public static String roomName;
	public String myRoomName;
	private JTextField textinput;
	private JScrollPane scrollSingle;
	private JPanel panel_1;
	private int count=0;
	private FileDialog fd;
	private ChatMsg obcm;
	private HashMap<String , ImageIcon> map = new HashMap<String , ImageIcon>();
	
	private int panel_height=428;
	private JButton btn_emt;
	public ChatView(String roomName) {
			map.put("(화남)", new ImageIcon("src/angry.PNG"));
			map.put("(생일)", new ImageIcon("src/birthday.PNG"));
			map.put("(잘가)", new ImageIcon("src/bye.PNG"));
			map.put("(축하)", new ImageIcon("src/congratulation.PNG"));
			map.put("(먹기)", new ImageIcon("src/eating.PNG"));
			map.put("(즐거움)", new ImageIcon("src/enjoy.PNG"));
			map.put("(화이팅)", new ImageIcon("src/fighting.PNG"));
			map.put("(굿)", new ImageIcon("src/good.PNG"));
			map.put("(잘자)", new ImageIcon("src/goodnight.PNG"));
			map.put("(행복)", new ImageIcon("src/happy.PNG"));
			map.put("(안녕)", new ImageIcon("src/hi.PNG"));
			map.put("(죽음)", new ImageIcon("src/kijul.PNG"));
			map.put("(슬픔)", new ImageIcon("src/sad.PNG"));
			map.put("(감사)", new ImageIcon("src/thanku.PNG"));
			map.put("(왜)", new ImageIcon("src/why.PNG"));
			map.put("(좋아)", new ImageIcon("src/yes.PNG"));
		
			setTitle(UserName.userName);
			this.roomName=roomName;
			this.myRoomName=roomName;
			System.out.println("success");
			Myaction action=new Myaction();
		    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 484, 627);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(255,253,152));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("배경색 설정");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(105, 10, 247, 33);
			lblNewLabel.setText(roomName);
			contentPane.add(lblNewLabel);
			
			

			ImageIcon icon = new ImageIcon("src/화살표.png");
			//이미지 크기 변경
            Image img = icon.getImage();
            Image changeImg = img.getScaledInstance(35, 35, Image.SCALE_REPLICATE);
            ImageIcon arrow = new ImageIcon(changeImg);
			
			
			ImageIcon exit = new ImageIcon("src/나가기.png");
			//이미지 크기 변경
            img = exit.getImage();
			changeImg = img.getScaledInstance(35, 35, Image.SCALE_REPLICATE);
            ImageIcon exit_new = new ImageIcon(changeImg);
            
            
            JButton btn_exit = new JButton(exit_new);	
            btn_exit.setBorderPainted(false);
            btn_exit.setContentAreaFilled(false);
            btn_exit.setFocusPainted(false);
			
			btn_exit.setName("나가기");
			btn_exit.setBounds(23, 9, 35, 35);
			btn_exit.setBackground(new Color(255,0,0,0));
			btn_exit.addActionListener(action);
			contentPane.add(btn_exit);
			
			JButton btn_setting = new JButton("배경색 설정");
			btn_setting.setBackground(new Color(255,255,255));
			btn_setting.setBounds(326, 15, 115, 28);
			btn_setting.addActionListener(action);
			contentPane.add(btn_setting);
			
			
			
			txtarea = new JPanel();
			
			txtarea.setBackground(Color.WHITE);
			txtarea.setPreferredSize(new Dimension(400, 428));
			//txtarea.setBounds(23, 53, 418, 395);
			//contentPane.add(txtarea);
			
			
			
			
			/*scrollSingle = new JScrollPane(txtarea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollSingle.setBounds(23, 53, 418, 428);*/
			scrollSingle = new JScrollPane();
			scrollSingle.setViewportView(txtarea);
			scrollSingle.setBounds(23, 53, 418, 428);
			scrollSingle.setAutoscrolls(true);
			
			
			txtarea.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			
			
			//scrollSingle.setPreferredSize(new Dimension(400, 200));
			
			contentPane.add(scrollSingle);
			
			ImageIcon icon_emotion = new ImageIcon("src/emotion.png");
			//이미지 크기 변경
            img = icon_emotion.getImage();
            changeImg = img.getScaledInstance(35, 35, Image.SCALE_REPLICATE);
            ImageIcon emoticon = new ImageIcon(changeImg);
			
			JPanel background_panel = new JPanel();
			background_panel.setBorder(new TitledBorder(new LineBorder(Color.gray)));
			background_panel.setBackground(Color.WHITE);
			background_panel.setBounds(23, 502, 418, 65);
			contentPane.add(background_panel);
			background_panel.setLayout(null);
			
			textinput = new JTextField();
			textinput.setBackground(SystemColor.control);
			textinput.setBounds(103, 19, 259, 28);
			background_panel.add(textinput);
			
			
			ImageIcon icon_plus = new ImageIcon("src/plus.png");
			//이미지 크기 변경
            img = icon_plus.getImage();
            changeImg = img.getScaledInstance(42, 42, Image.SCALE_REPLICATE);
            ImageIcon plus = new ImageIcon(changeImg);
            
			JButton btn_pic = new JButton(plus);
			btn_pic.setBounds(12, 14, 35, 35);
			background_panel.add(btn_pic);
			
			//ImageIcon image_plus = new ImageIcon("src/plus.png");			
			//button_1.setIcon(image_plus);
			//button_1.setLayout(new BorderLayout());
			btn_pic.setBackground(new Color(255,0,0,0));
			
			btn_pic.setBorderPainted(false);
			btn_pic.setContentAreaFilled(false);
			btn_pic.setFocusPainted(false);
			
			
			btn_emt = new JButton(emoticon);
			btn_emt.setBounds(55, 14, 35, 35);
			background_panel.add(btn_emt);
			btn_emt.setBackground(new Color(255,0,0,0));
			
			btn_emt.setBorderPainted(false);
			btn_emt.setContentAreaFilled(false);
			btn_emt.setFocusPainted(false);
			
			
			btn_send = new JButton(arrow);
			btn_send.setBounds(373, 14, 35, 35);
			background_panel.add(btn_send);
			btn_send.setBackground(new Color(255,0,0,0));
			
            btn_send.setBorderPainted(false);
            btn_send.setContentAreaFilled(false);
            btn_send.setFocusPainted(false);
            TextSendAction send_action = new TextSendAction();
            btn_send.addActionListener(send_action);
			
			//ImageIcon image_emotion = new ImageIcon("src/emotion.png");
			//button.setIcon(image_emotion);
			btn_emt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Emoticon emoticon=new Emoticon(myRoomName);
				}
			});
			
			btn_pic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Frame frame= new Frame("이미지첨부");
		               fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
		               fd.setVisible(true);
		               obcm = new ChatMsg(myRoomName, "500", fd.getDirectory() + fd.getFile());
		               ImageIcon image = new ImageIcon(fd.getDirectory() + fd.getFile());
		               obcm.userName = UserName.userName;
		               obcm.setImg(UserName.sub_userProfile);
		               obcm.msg_img=image;
		               UserName.SendObject(obcm);
				}
			});
			
			textinput.addActionListener(send_action);
			
			setVisible(true);
	}
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 //Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btn_send || e.getSource() == textinput) {
				//System.out.println(groupChat.groupName);
				String msg = textinput.getText();
				if(!msg.equals("")) {
					ChatMsg obcm = new ChatMsg(myRoomName, "500", msg);
					obcm.userName = UserName.userName;
					obcm.setImg(UserName.sub_userProfile);
					UserName.SendObject(obcm);
					textinput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
					textinput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				}
				
			}
		}
	}
	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			if(btn.getText().equals("배경색 설정")) {
				JColorChooser chooser=new JColorChooser();
				 Color selectedColor=chooser.showDialog(null,"Color",Color.YELLOW);
	                if(selectedColor!=null)
	                	txtarea.setBackground(selectedColor);
			}
			else if(btn.getName().equals("나가기")) {
				int index=-1;
				for(int i=0;i<UserName.chatview_list.size();i++) {
					System.out.println(UserName.chatview_list.get(i).getName());
					if(UserName.chatview_list.get(i).getName().equals(myRoomName)) {
						index=i;
					}
				}
				if(index!=-1) 
					UserName.chatview_list.remove(index);
				setVisible(false);
			}	
		}
	}
	public void appendLeft(String username, String msg,ImageIcon icon, String time) {
		if(count>=4) {
			panel_height+=105;
			txtarea.setPreferredSize(new Dimension(400, panel_height));
		}
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,0, 0, 0));
		panel.setPreferredSize(new Dimension(390, 100));
		//panel.setSize(200, 100);
		panel.setLayout(null);
		
		
		JLabel c_name = new JLabel(username);
		c_name.setHorizontalAlignment(SwingConstants.CENTER);
		c_name.setBounds(12, 5, 66, 15);
		panel.add(c_name);
		
		JButton c_image;
		//JButton c_image = new JButton("new button");
		//c_image.setBounds(12, 23, 69, 67);
		Image ori_icon = icon.getImage();
		int width, height;
		double ratio;
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) width / height;
				height = 70;
				width = (int) (height * ratio);
			} else { // 세로 사진
				ratio = (double) height / width;
				width = 70;
				height = (int) (width * ratio);
			}
			Image new_img = ori_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			//c_image = null;
			c_image = new JButton(new_icon);
			c_image.setBounds(12, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
			//c_image.setIcon(new_icon);
		}
		else {
			//c_image.setIcon(icon);
			c_image = new JButton(icon);
			c_image.setBounds(12, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
		}
			
		
		
		//JButton c_image = new JButton("New button");
		//c_image.setBounds(12, 23, 69, 67);
		//c_image.setIcon(icon);
		c_image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ZoomProfile zoomProfile=new ZoomProfile(icon,false);
			}
		});
		panel.add(c_image);

		System.out.println(msg);
		if(map.containsKey(msg.trim())==true) {
			System.out.println("걸림");
			JPanel emoticon=new JPanel() {
	    		Image background=map.get(msg.trim()).getImage();
	    		public void paint(Graphics g) {//그리는 함수
	    			g.drawImage(background, 0, 0, null);//background를 그려줌		
	    		}
	    	};
	    	emoticon.setBounds(89, 5, 90, 90);
			panel.add(emoticon);
			
			JLabel lblNewLabel_1_e = new JLabel("New label");
			lblNewLabel_1_e.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1_e.setBounds(182, 75, 80, 15);
			lblNewLabel_1_e.setText(time);
			lblNewLabel_1_e.setFont(new Font("굴림", Font.PLAIN, 8));
			panel.add(lblNewLabel_1_e);
		}
		else {
			JLabel c_text = new JLabel(msg);
			c_text.setBounds(87, 66, 198, 24);
			panel.add(c_text);
			
			JLabel lblNewLabel_1 = new JLabel("New label");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setBounds(298, 75, 80, 15);
			lblNewLabel_1.setText(time);
			lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 8));
			panel.add(lblNewLabel_1);
		}
		
		txtarea.add(panel);
		
		txtarea.updateUI();
		count++;
				
		System.out.println("스크롤 전: "+scrollSingle.getVerticalScrollBar().getMaximum()+ "값: "+scrollSingle.getVerticalScrollBar().getValue());
		scrollSingle.setViewportView(txtarea);
		scrollSingle.getVerticalScrollBar().setValue(scrollSingle.getVerticalScrollBar().getMaximum());
		
	}
	public void appendRight(String username, String msg,ImageIcon icon, String time) {
		if(count>=4) {
			panel_height+=105;
			txtarea.setPreferredSize(new Dimension(400, panel_height));
		}
	
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,0, 0, 0));
		panel.setPreferredSize(new Dimension(390, 100));
		//panel.setSize(200, 100);
		panel.setLayout(null);
		JLabel c_name = new JLabel(username);
		c_name.setHorizontalAlignment(SwingConstants.CENTER);
		c_name.setBounds(310, 5, 66, 15);
		panel.add(c_name);
		
		JButton c_image;
		//c_image.setBounds(309, 23, 69, 67);
		Image ori_icon = icon.getImage();
		int width, height;
		double ratio;
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) width / height;
				height = 70;
				width = (int) (height * ratio);
			} else { // 세로 사진
				ratio = (double) height / width;
				width = 70;
				height = (int) (width * ratio);			
			}
			Image new_img = ori_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			
			c_image = new JButton(new_icon);
			c_image.setBounds(309, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
		}
		else {
			c_image = new JButton(icon);
			c_image.setBounds(309, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
		}

		//System.out.println(icon);
		
		//JButton c_image = new JButton("New button");
		//c_image.setBounds(12, 23, 69, 67);
		//c_image.setIcon(icon);
		c_image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ZoomProfile zoomProfile=new ZoomProfile(icon,false);
			}
		});
		panel.add(c_image);
		
		/*JLabel c_text = new JLabel(msg);
		c_text.setHorizontalAlignment(SwingConstants.RIGHT);
		c_text.setBounds(94, 66, 198,24);
		panel.add(c_text);
		
		//시간추가
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(12, 75, 80, 15);
		lblNewLabel_1.setText(time);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 8));
		panel.add(lblNewLabel_1);
		
		txtarea.add(panel);*/
		System.out.println(msg);
		if(map.containsKey(msg.trim())==true) {
			System.out.println("걸림");
			JPanel emoticon=new JPanel() {
	    		Image background=map.get(msg.trim()).getImage();		
	    		
	    		public void paint(Graphics g) {//그리는 함수
	    			g.drawImage(background, 0, 0, null);//background를 그려줌		
	    		}
	    	};
	    	emoticon.setBounds(208, 5, 90, 90);
			panel.add(emoticon);
			
			JLabel lblNewLabel_1_e = new JLabel("New label");
			lblNewLabel_1_e.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_1_e.setBounds(121, 75, 80, 15);
			lblNewLabel_1_e.setText(time);
			lblNewLabel_1_e.setFont(new Font("굴림", Font.PLAIN, 8));
			panel.add(lblNewLabel_1_e);
		}
		else {
			JLabel c_text = new JLabel(msg);
			c_text.setHorizontalAlignment(SwingConstants.RIGHT);
			c_text.setBounds(94, 66, 198,24);
			panel.add(c_text);
			
			//시간추가
			JLabel lblNewLabel_1 = new JLabel("New label");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_1.setBounds(12, 75, 80, 15);
			lblNewLabel_1.setText(time);
			lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 8));
			panel.add(lblNewLabel_1);
		}
		
		txtarea.add(panel);
		txtarea.updateUI();
		count++;
		
		System.out.println("스크롤 전: "+scrollSingle.getVerticalScrollBar().getMaximum()+ "값: "+scrollSingle.getVerticalScrollBar().getValue());
		scrollSingle.setViewportView(txtarea);
		scrollSingle.getVerticalScrollBar().setValue(scrollSingle.getVerticalScrollBar().getMaximum());
		

		System.out.println("스크롤 후: "+scrollSingle.getVerticalScrollBar().getMaximum()+ "값: "+scrollSingle.getVerticalScrollBar().getValue());
	}
	public void appendImageLeft(String username,  ImageIcon msg,ImageIcon icon, String time) {
		if(count>=4) {
			panel_height+=105;
			txtarea.setPreferredSize(new Dimension(400, panel_height));
		}
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(255,0, 0, 0));
		panel1.setPreferredSize(new Dimension(390, 100));
		//panel.setSize(200, 100);
		panel1.setLayout(null);
		JLabel c_name1 = new JLabel(username);
		c_name1.setHorizontalAlignment(SwingConstants.CENTER);
		c_name1.setBounds(12, 5, 66, 15);
		panel1.add(c_name1);
		
		JButton c_image;// = new JButton("new button");
		
		Image ori_icon = icon.getImage();
		int width, height;
		double ratio;
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) width / height;
				height = 70;
				width = (int) (height * ratio);
			} else { // 세로 사진
				ratio = (double) height / width;
				width = 70;
				height = (int) (width * ratio);				
			}
			Image new_img = ori_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			c_image = new JButton(new_icon);
			c_image.setBounds(12, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
			//c_image.setIcon(new_icon);
		}
		else {
			c_image = new JButton(icon);
			c_image.setBounds(12, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
		}
			
		
		
		//JButton c_image = new JButton("New button");
		//c_image.setBounds(12, 23, 69, 67);
		//c_image.setIcon(icon);
		c_image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ZoomProfile zoomProfile=new ZoomProfile(icon,false);
			}
		});
		panel1.add(c_image);
		
		txtarea.add(panel1);
		
		JButton btnNewButton;// = new JButton("New button");
		
		
		Image ori_img = msg.getImage();
		//int width, height;
		//double ratio;
		width = msg.getIconWidth();
		height = msg.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			btnNewButton = new JButton(new_icon);
			btnNewButton.setBounds(87, 5, 134, 85);
			btnNewButton.setBackground(new Color(255,255,255));
		}
		else {
			btnNewButton = new JButton(msg);
			btnNewButton.setBounds(87, 5, 134, 85);
			btnNewButton.setBackground(new Color(255,255,255));
		}
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ZoomProfile zoomProfile=new ZoomProfile(msg,true);
			}
		});
		panel1.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setBounds(228, 75, 90, 15);
		lblNewLabel_2.setText(time);
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 8));
		panel1.add(lblNewLabel_2);
		
		txtarea.updateUI();
		count++;
				
		System.out.println("스크롤 전: "+scrollSingle.getVerticalScrollBar().getMaximum()+ "값: "+scrollSingle.getVerticalScrollBar().getValue());
		scrollSingle.setViewportView(txtarea);
		scrollSingle.getVerticalScrollBar().setValue(scrollSingle.getVerticalScrollBar().getMaximum());
		
	}
	public void appendImageRight(String username, ImageIcon msg, ImageIcon icon, String time) {
		if(count>=4) {
			panel_height+=105;
			txtarea.setPreferredSize(new Dimension(400, panel_height));
		}
	
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(255,0, 0, 0));
		panel1.setPreferredSize(new Dimension(390, 100));
		//panel.setSize(200, 100);
		panel1.setLayout(null);
		JLabel c_name1 = new JLabel(username);
		c_name1.setHorizontalAlignment(SwingConstants.CENTER);
		c_name1.setBounds(310, 5, 66, 15);
		panel1.add(c_name1);
		
		JButton c_image; // = new JButton("new button");
		
		Image ori_icon = icon.getImage();
		int width, height;
		double ratio;
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) width / height;
				height = 70;
				width = (int) (height * ratio);			
			} else { // 세로 사진
				ratio = (double) height / width;
				width = 70;
				height = (int) (width * ratio);
			}
			Image new_img = ori_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			
			c_image = new JButton(new_icon);
			c_image.setBounds(309, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
			//c_image.setIcon(new_icon);
		}
		else {
			c_image = new JButton(icon);
			c_image.setBounds(309, 23, 69, 67);
			c_image.setBackground(new Color(255,255,255));
		}
		
		
		//JButton c_image = new JButton("New button");
		//c_image.setBounds(12, 23, 69, 67);
		//c_image.setIcon(icon);
		c_image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ZoomProfile zoomProfile=new ZoomProfile(icon,false);
			}
		});
		panel1.add(c_image);
		
		txtarea.add(panel1);
		
		JButton btnNewButton;// = new JButton("New button");
		Image ori_img = msg.getImage();
		//int width, height;
		//double ratio;
		width = msg.getIconWidth();
		height = msg.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			btnNewButton = new JButton(new_icon);
			btnNewButton.setBounds(164, 5, 134, 85);
			btnNewButton.setBackground(new Color(255,255,255));
		}
		else {
			btnNewButton = new JButton(msg);
			btnNewButton.setBounds(164, 5, 134, 85);
			btnNewButton.setBackground(new Color(255,255,255));
		}
		//	btnNewButton.setIcon(msg);
		//btnNewButton.setBounds(164, 5, 134, 85);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ZoomProfile zoomProfile=new ZoomProfile(msg,true);
			}
		});
		
		panel1.add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(70, 75, 88, 15);
		lblNewLabel_3.setText(time);
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 8));
		panel1.add(lblNewLabel_3);
		
		txtarea.updateUI();
		count++;
		
		System.out.println("스크롤 전: "+scrollSingle.getVerticalScrollBar().getMaximum()+ "값: "+scrollSingle.getVerticalScrollBar().getValue());
		scrollSingle.setViewportView(txtarea);
		scrollSingle.getVerticalScrollBar().setValue(scrollSingle.getVerticalScrollBar().getMaximum());
		

		System.out.println("스크롤 후: "+scrollSingle.getVerticalScrollBar().getMaximum()+ "값: "+scrollSingle.getVerticalScrollBar().getValue());
	}
}
