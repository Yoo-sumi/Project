import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

//import UserName.MyMouseListener;

//import Profile.Myaction;

public class ProfileAndChattingList extends JFrame {
	public Vector<JPanel> panel_list;
	public JLabel[] chat_name;
	public int g_size;
	public Vector<String> g_chatRecord;	  
	public String[] g_chatlist2;
	public Vector<String> g_time;

	
   public static JPanel contentPane;
   public static JButton userProfile;
   public static JLabel userName;
   public static JPanel ChatRoomlist;
   public JButton btnNewButton[]=new JButton[9];
   public JLabel friend_name[]=new JLabel[9];
   public JPanel panel[]=new JPanel[9];
   private int index;
   public static GeneralChat generalChat;
   
   public JPanel panel_friendlist;
   
   public ProfileAndChattingList() {
      Myaction action=new Myaction();
      Myaction2 action2=new Myaction2();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 901, 539);
      contentPane = new JPanel();
      contentPane.setBackground(new Color(255,253,152));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JLabel lblNewLabel = new JLabel("\uB0B4 \uD504\uB85C\uD544");
      lblNewLabel.setBounds(33, 12, 82, 33);
      contentPane.add(lblNewLabel);
      
      
      
      JPanel panel_myprofile = new JPanel();
      panel_myprofile.setBackground(Color.WHITE);
      panel_myprofile.setBounds(33, 43, 305, 79);
      contentPane.add(panel_myprofile);
      panel_myprofile.setLayout(null);
      
      userProfile = new JButton(UserName.sub_userProfile_s);
      userProfile.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            ZoomProfile zoomProfile=new ZoomProfile(UserName.sub_userProfile,false);
         }
      });
      userProfile.setBounds(12, 10, 60, 60);
      userProfile.setBackground(new Color(255,255,255));
      panel_myprofile.add(userProfile);
      
      userName = new JLabel("New label");
      userName.setBounds(87, 10, 109, 21);
      panel_myprofile.add(userName);
      
      panel_friendlist = new JPanel();
      panel_friendlist.setBackground(new Color(255,255,255));
      panel_friendlist.setBackground(Color.WHITE);
      panel_friendlist.setBounds(33, 179, 305, 289);
      
      panel_friendlist.setLayout(new GridLayout(9, 1, 0, 0));
      
      JScrollPane scrollSingle = new JScrollPane(panel_friendlist, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      scrollSingle.setSize(305, 289);
      scrollSingle.setLocation(33, 179);
      scrollSingle.setPreferredSize(new Dimension(400, 200));
      //본인을 제외한 나머지 9개를 주어야함
         String u = UserName.sub_userName;
         String sub = u.substring(u.length()-1);
         int a = Integer.parseInt(sub)-1;
         if (a==-1)
            a=9;
         index=a; //이거 이상할수도
         System.out.println("본인 인덱스 : "+ a);
         for(int i=0;i<10;i++) {//9->10으로 수정
            
            int i_a=i;
            //0 1 2 3 
            //user3이 들어온다? a=2
            //user4부터는 i_a = 
            if(i==a) continue;
            if(i>a) { // user4일때 a a
               i_a = i-1;
            }
            //이렇게하면 한칸 뛰어서 이어서 이어지므로....
            //
            
            
            panel[i_a] = new JPanel();
            panel[i_a].setBackground(new Color(255,255,255));
            panel_friendlist.add(panel[i_a]);
            panel[i_a].setLayout(null);
            
            ImageIcon s_icon = UserName.img_list_s.get(i);
            //이미지 크기 변경
            Image img = s_icon.getImage();
            Image changeImg = img.getScaledInstance(40, 40, Image.SCALE_REPLICATE);
            ImageIcon new_img = new ImageIcon(changeImg);
            
            btnNewButton[i_a] = new JButton(new_img);
            btnNewButton[i_a].setName("user"+(i+1));
            btnNewButton[i_a].setBounds(3, 0, 30, 30);
            //setIcon 하기 전에 크기 조절 해주기... 원본받았으니까.
            //btnNewButton[i_a].setIcon(UserName.img_list_s.get(i));
            //UserName.img_list2[index]
            btnNewButton[i_a].addActionListener(action2);
            
            panel[i_a].add(btnNewButton[i_a]);
            
            friend_name[i_a] = new JLabel("New label");
            friend_name[i_a].setBounds(61, 3, 100, 20);
            friend_name[i_a].setText(UserName.userList.get(i_a));
            panel[i_a].add(friend_name[i_a]);
       }
      
      JLabel lblNewLabel_1 = new JLabel("친구 목록");
      lblNewLabel_1.setBounds(33, 145, 82, 33);
      contentPane.add(lblNewLabel_1);
      
      //JPanel panel_chatlist = new JPanel();
      
      ChatRoomlist=new JPanel();
      ChatRoomlist.setBackground(Color.WHITE);
      ChatRoomlist.setBounds(376, 43, 340, 425);
      contentPane.add(ChatRoomlist);
      ChatRoomlist.setLayout(new GridLayout(9, 1, 0, 0));
      
      //
      /*JPanel panel_1 = new JPanel();
      panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
      panel_1.setBackground(Color.WHITE);
      ChatRoomlist.add(panel_1);
      panel_1.setLayout(null);
      
      JLabel chat_name = new JLabel("New label");
      chat_name.setBounds(12, 5, 292, 20);
      panel_1.add(chat_name);
      
      JLabel chat_text = new JLabel("New label");
      chat_text.setBounds(12, 27, 316, 20);
      panel_1.add(chat_text);*/
      //
      
      /*JList list = new JList();
      list.setBounds(177, 10, 0, 0);
      ChatRoomlist.add(list);*/
      
      JLabel lblNewLabel_2 = new JLabel("채팅방 목록");
      lblNewLabel_2.setBounds(376, 12, 143, 33);
      contentPane.add(lblNewLabel_2);
      
      JButton btn_generalChat = new JButton("채팅방 만들기");
      btn_generalChat.setBackground(new Color(255,255,255));
      btn_generalChat.setBounds(728, 43, 139, 44);
      btn_generalChat.addActionListener(action);
      contentPane.add(btn_generalChat);
      
      
      
      JButton btn_exit = new JButton("나가기");
      btn_exit.setBackground(new Color(255,255,255));
      btn_exit.setBounds(728, 99, 139, 44);
      btn_exit.addActionListener(action);
      contentPane.add(btn_exit);
      
      
      userName.setText(UserName.sub_userName);
      //userProfile.setIcon(UserName.sub_userProfile_s);
      
      JButton btn_change_profile = new JButton("프로필 변경");
      btn_change_profile.setBackground(new Color(255,255,255));
      btn_change_profile.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Profile profile=new Profile();
         }
      });
      btn_change_profile.setBounds(84, 47, 100, 23);
      panel_myprofile.add(btn_change_profile);
      contentPane.add(scrollSingle);
      
      ImageIcon ic = new ImageIcon("src/동기화.png");
      Image img = ic.getImage();
      Image changeImg = img.getScaledInstance(33, 33, Image.SCALE_REPLICATE);
      ImageIcon new_img = new ImageIcon(changeImg);
      
      
      JButton btnNewButton_1 = new JButton(new_img);
      btnNewButton_1.setBackground(new Color(255,0,0,0));
		
      btnNewButton_1.setBorderPainted(false);
      btnNewButton_1.setContentAreaFilled(false);
      btnNewButton_1.setFocusPainted(false);
      
      btnNewButton_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChatMsg obcm = new ChatMsg(UserName.userName, "401", "update");
            obcm.userlist=UserName.userList;
            UserName.SendObject(obcm);
         }
      });
      btnNewButton_1.setBounds(299, 140, 33, 33);
      contentPane.add(btnNewButton_1);
      
      ///
      
      
      setVisible(true);
      
   }
   class Myaction2 implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
         JButton button=(JButton)e.getSource();
         String name = button.getName();
         System.out.println(name);
         ChatMsg obcm = new ChatMsg(name, "301", null);
         UserName.SendObject(obcm);
         //System.out.println(UserName.img_list2[index]);
         //ZoomProfile zoomProfile=new ZoomProfile(UserName.img_list2[index]);
      }
   }
      
   class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
         JButton btn = (JButton)e.getSource();
         if(btn.getText().equals("채팅방 만들기")) {
            generalChat = new GeneralChat();
            //ChatMsg obcm = new ChatMsg(UserName.userName, "600", "request");
            //UserName.SendObject(obcm);
         }
         else if(btn.getText().equals("나가기")) {
            setVisible(false);
            ChatMsg obcm = new ChatMsg(UserName.userName, "800", null);
            UserName.SendObject(obcm);
            System.exit(0);
         }
      }
   }
   public void profile_update(ImageIcon profile, int index) {
      //이미지 크기 변경
        Image img = profile.getImage();
        Image changeImg = img.getScaledInstance(40, 40, Image.SCALE_REPLICATE);
        ImageIcon new_img = new ImageIcon(changeImg);
      btnNewButton[index].setIcon(new_img);
      panel_friendlist.repaint();
   }
   
   
   public void updateRepaint(Vector<JPanel> panel_list, JLabel[] chat_name, int g_size,
		   Vector<String> g_chatRecord, String[] g_chatlist2, Vector<String> g_time) {
	  this.panel_list=panel_list;
		this.chat_name=chat_name;
		this.g_size=g_size;
		this.g_chatRecord=g_chatRecord;	  
		this.g_chatlist2=g_chatlist2;
		this.g_time=g_time;
   }
   class MyMouseListener implements MouseListener{

       @Override
       public void mouseClicked(MouseEvent e) {
           if (e.getClickCount()==2){
              System.out.println("더블클릭!!!");
              String name=null;
              int j=0;
              JPanel Jpanel=(JPanel)e.getSource();
             for(int i=0;i<panel_list.size();i++) {
                if(panel_list.get(i).getName().equals(Jpanel.getName())) {
                   name=Jpanel.getName();
                }
                   //Jpenl.get
                 //chatView=new ChatView();
              }
             for(int i=0;i<UserName.chatview_list.size();i++) {
                if(name.equals(UserName.chatview_list.get(i).getName())) {
                   j=1;
                   break;
                }
             }
             if(j!=1) {
                 ChatView chatView=new ChatView(name);
                 chatView.setName(name);
                 UserName.chatview_list.add(chatView);
                 ChatMsg obcm = new ChatMsg(UserName.userName, "700", name);
                 UserName.SendObject(obcm);
             }
             //System.out.println(chatview_list);
           }
       }

       @Override
       public void mousePressed(MouseEvent e) {
       }

       @Override
       public void mouseReleased(MouseEvent e) {
       }

       @Override
       public void mouseEntered(MouseEvent e) {
       }

       @Override
       public void mouseExited(MouseEvent e) {
       }
       
   }
   
   public void paint(Graphics g) {
       super.paint(g);
        
       JPanel ChatRoomlist=new JPanel();
       ChatRoomlist.setBackground(Color.WHITE);
       ChatRoomlist.setBounds(376, 43, 340, 425);
       System.out.println("ㅎㅎ해줌");
      // if (list != null) {
    	   System.out.println("repaint해줌");
    	   ProfileAndChattingList.contentPane.add(ChatRoomlist);
           ChatRoomlist.setLayout(new GridLayout(9, 1, 0, 0));
           //
           for(int i=0;i<g_size;i++) {
              JPanel panel = new JPanel();
              panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
              panel.setBackground(Color.WHITE);
              ChatRoomlist.add(panel);
              panel.setLayout(null);
              
              chat_name[i] = new JLabel("New label");
              chat_name[i].setText(g_chatlist2[i]);
              chat_name[i].setBounds(12, 5, 292, 20);
              panel.add(chat_name[i]);
              
              JLabel chat_text= new JLabel("New label");
              chat_text.setBounds(12, 27, 316, 20);
              if(g_chatRecord.get(i).length()>=2){
                 if(g_chatRecord.get(i).substring(0,2).equals("C:"))
                    chat_text.setText("사진");
                 else
                    chat_text.setText(g_chatRecord.get(i));
              }
              else
                 chat_text.setText(g_chatRecord.get(i));
              
              panel.add(chat_text);
              panel.addMouseListener(new MyMouseListener());
              panel.setName(g_chatlist2[i]);
              panel_list.add(panel);
              
              JLabel lblNewLabel_3 = new JLabel("New label");
              lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
              lblNewLabel_3.setBounds(251, 30, 77, 15);
              lblNewLabel_3.setText(g_time.get(i));
              
              panel.add(lblNewLabel_3);
              
              ChatRoomlist.updateUI();
           }
     //  }
       
   }
}