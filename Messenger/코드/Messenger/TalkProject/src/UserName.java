// JavaChatClientMain.java
// Java Client 시작import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Graphics;

public class UserName extends JFrame {
   private static final  int BUF_LEN = 128; //  Windows 처럼 BUF_LEN 을 정의
   private Socket socket; // 연결소켓
   private ObjectInputStream ois;
   private static ObjectOutputStream oos;
   private JPanel contentPane;
   private JButton btnConnect;
   private JTextField txtUserName;
   public static String userName; //user1, user2, ... 아이디 이름
   private String myUserName;
   private JLabel label;
   public JPanel[] panel_1=new JPanel[10];
   public JLabel[] chat_name=new JLabel[10]; //*
   public ProfileAndChattingList list = null;
   
   public Vector<JPanel> panel_list=new Vector<JPanel>(); //*
   public static Vector<ChatView> chatview_list=new Vector<ChatView>();
   
   public static ImageIcon[] img_list2=new ImageIcon[10];
   public ImageIcon[] img_list_pub = new ImageIcon[10];
   public static Vector<ImageIcon> img_list=new Vector<ImageIcon>();
   public static Vector<ImageIcon> img_list_s=new Vector<ImageIcon>();
   public static String sub_userName;
   public static ImageIcon sub_userProfile;
   public static ImageIcon sub_userProfile_s;
   public static Vector<String> userList=new Vector<String>();
   //groupChat에 들어가는 3개요소를 각각 저장할수있어야함.. 
   //그리고 유저들도 groupChat을 여러개 가질 수 있어야함(본인이 들어간 톡방들)
   //groupname,chatRecord쌍으로 갖게하는 리스트 만들기!!!!해시?아 그럼 유저리스트도..
   //public static Vector<String> chatRecord = new Vector<String>();
   
   
   
   public int g_size;
   public Vector<String> g_chatRecord;
   public String[] g_chatlist2;
   public Vector<String> g_time;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               UserName frame = new UserName();
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
   public UserName() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 411, 565);
      contentPane = new JPanel();
      contentPane.setBackground(new Color(255,253,152));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JLabel lblNewLabel = new JLabel("User Name");
      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
      lblNewLabel.setBounds(153, 148, 92, 33);
      contentPane.add(lblNewLabel);
      
      txtUserName = new JTextField();
      txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
      txtUserName.setBounds(67, 217, 264, 61);
      contentPane.add(txtUserName);
      txtUserName.setColumns(10);
      
      btnConnect = new JButton("완료");
      btnConnect.setBackground(new Color(255,255,255));
      btnConnect.setBounds(120, 348, 160, 38);
      contentPane.add(btnConnect);
      
      label = new JLabel("");
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setBounds(12, 288, 372, 48);
      contentPane.add(label);
      Myaction action=new Myaction();
      btnConnect.addActionListener(action);
      txtUserName.addActionListener(action);
   }
   class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnConnect || e.getSource() == txtUserName) {
               userName=txtUserName.getText().trim();//빈칸에 입력한 아이디
               myUserName=userName;
               Server(userName);
            }
         }
   }
   public void Server(String userName) { //서버에 접속하며 아이디 로그인.
      try {
         //127.0.0.1
         //223.194.135.191
         socket = new Socket("127.0.0.1", Integer.parseInt("30000"));
         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());
         
         ChatMsg obcm=new ChatMsg(userName,"100",null);
         SendObject(obcm);
         
         ListenNetwork net = new ListenNetwork();
         net.start();
         
      } catch (NumberFormatException | IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   class ListenNetwork extends Thread {
      public void run() {
         while (true) {
            try {
               Object obcm = null;
               String msg = null;
               ChatMsg cm=null;
               try {
                  obcm = ois.readObject();
               } catch (ClassNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  break;
               }
               if (obcm == null) {
                  System.out.println("받은 obcm에 아무것도 없음.");
                  break;
               }
                  
               if (obcm instanceof ChatMsg) {
                  cm = (ChatMsg) obcm;
                  msg=cm.getData();
                  //System.out.println(cm.getData());
                  //msg = String.format("[%s] %s", cm.getId(), cm.getData());
               } else
                  continue;
               switch (cm.getCode()) {
               case "200": // login
                  if(msg.equals("success")) {
                     userList=cm.userlist;
                     for(int i=0;i<10;i++) {
                        img_list2[i]=cm.img_list2[i];
                        img_list_pub[i]=cm.img_list2[i];
                        System.out.print(img_list2[i]);
                     }
                     System.out.println();
                     //btnNewButton[i_a].setIcon(UserName.img_list_s.get(i));
                        //UserName.img_list2[index]
                     img_list_s=cm.img_list_s;
                     sub_userName=userName;
                     sub_userProfile=cm.img;
                     
                     
                   //이미지 크기 변경
                     Image img = cm.img.getImage();
                     Image changeImg = img.getScaledInstance(60, 60, Image.SCALE_REPLICATE);
                     ImageIcon new_img = new ImageIcon(changeImg);
                     sub_userProfile_s=new_img;
                     
                     
                     list = new ProfileAndChattingList();
                     ProfileAndChattingList.userProfile.setIcon(sub_userProfile_s);
                     //ProfileAndChattingList.setImgList(img_list2);
                     
                     JPanel ChatRoomlist=new JPanel();
                     ChatRoomlist.setBackground(Color.WHITE);
                     ChatRoomlist.setBounds(376, 43, 340, 425);
                     ProfileAndChattingList.contentPane.add(ChatRoomlist);
                     ChatRoomlist.setLayout(new GridLayout(9, 1, 0, 0));
                     
                     g_size = cm.size;
                     g_chatRecord = cm.chatRecord;
                     g_chatlist2 = cm.chatlist2;
                     g_time = cm.timeVector;
                     for(int i=0;i<cm.size;i++) {
                        System.out.println("채팅리스트 출력");
                        JPanel panel = new JPanel();
                        panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
                        panel.setBackground(Color.WHITE);
                        ChatRoomlist.add(panel);
                        panel.setLayout(null);
                        
                        chat_name[i] = new JLabel("New label");
                        chat_name[i].setText(cm.chatlist2[i]);
                        chat_name[i].setBounds(12, 5, 292, 20);
                        panel.add(chat_name[i]);
                        
                        JLabel chat_text= new JLabel("New label");
                        chat_text.setBounds(12, 27, 316, 20);
                        if(cm.chatRecord.get(i).length()>=2){
                           if(cm.chatRecord.get(i).substring(0,2).equals("C:")) 
                              chat_text.setText("사진");
                           else
                              chat_text.setText(cm.chatRecord.get(i));
                        }
                        else
                           chat_text.setText(cm.chatRecord.get(i));
                        
                        panel.add(chat_text);
                        panel.addMouseListener(new MyMouseListener());
                        panel.setName(cm.chatlist2[i]);
                        panel_list.add(panel);
                        
                        JLabel lblNewLabel_3 = new JLabel("New label");
                        lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
                        lblNewLabel_3.setBounds(251, 30, 77, 15);
                        lblNewLabel_3.setText(cm.timeVector.get(i));
                        panel.add(lblNewLabel_3);
                        
                        ChatRoomlist.updateUI();
                     }
                     
                     
                     setVisible(false);
                  }
                  else if(msg.equals("not_exist")) {
                     System.out.println(msg);
                     label.setText(userName+"은 존재하지 않는 아이디 입니다.");
                  }
                  else if(msg.equals("overlap"))
                     label.setText(userName+"이미 로그인 되어 있습니다.");
                  break;
               case "301":
                  ZoomProfile zoomProfile=new ZoomProfile(cm.img, false);
                  break;
               case "400": // 사용자 정보 받기
                  System.out.println(msg);
                  sub_userName=msg;
                  sub_userProfile=cm.img;
                  sub_userProfile_s=cm.img_s;
                  for(int i=0;i<10;i++) {
                     img_list2[i]=cm.img_list2[i];
                     img_list_pub[i]=cm.img_list2[i];
                  }
                      img_list_s=cm.img_list_s;
                  ProfileAndChattingList.userProfile.setIcon(cm.img_s);
                  //ProfileAndChattingList.setImgList(img_list2);
                  break;
               case "401":
                  list.profile_update(cm.img, Integer.parseInt(cm.getData()));
                  break;
               case "500": // 채팅방 리스트, 채팅
                  JPanel ChatRoomlist=new JPanel();
                  ChatRoomlist.setBackground(Color.WHITE);
                  ChatRoomlist.setBounds(376, 43, 340, 425);
                  ProfileAndChattingList.contentPane.add(ChatRoomlist);
                  ChatRoomlist.setLayout(new GridLayout(9, 1, 0, 0));
                  //
                  g_size = cm.size;
                  g_chatRecord = cm.chatRecord;
                  g_chatlist2 = cm.chatlist2;
                  g_time = cm.timeVector;
                  
                  list.updateRepaint(panel_list, chat_name, g_size, g_chatRecord, g_chatlist2, g_time);
                  for(int i=0;i<cm.size;i++) {
                     JPanel panel = new JPanel();
                     panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
                     panel.setBackground(Color.WHITE);
                     ChatRoomlist.add(panel);
                     panel.setLayout(null);
                     
                     chat_name[i] = new JLabel("New label");
                     chat_name[i].setText(cm.chatlist2[i]);
                     chat_name[i].setBounds(12, 5, 292, 20);
                     panel.add(chat_name[i]);
                     
                     JLabel chat_text= new JLabel("New label");
                     chat_text.setBounds(12, 27, 316, 20);
                     if(cm.chatRecord.get(i).length()>=2){
                        if(cm.chatRecord.get(i).substring(0,2).equals("C:"))
                           chat_text.setText("사진");
                        else
                           chat_text.setText(cm.chatRecord.get(i));
                     }
                     else
                        chat_text.setText(cm.chatRecord.get(i));
                     
                     panel.add(chat_text);
                     panel.addMouseListener(new MyMouseListener());
                     panel.setName(cm.chatlist2[i]);
                     panel_list.add(panel);
                     
                     JLabel lblNewLabel_3 = new JLabel("New label");
                     lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
                     lblNewLabel_3.setBounds(251, 30, 77, 15);
                     lblNewLabel_3.setText(cm.timeVector.get(i));
                     
                     panel.add(lblNewLabel_3);
                     
                     ChatRoomlist.updateUI();
                  }
                  //
                     
                  System.out.println("톡방 이름: "+cm.getData());
                  if(myUserName.equals(cm.userName)) { //본인 스스로에게 받
                     //AppendText(cm.getData());
                     if(cm.getData().length()>=2){
                        if(cm.getData().substring(0,2).equals("C:"))
                           AppendImageRight("[" + cm.userName + "]",cm.img_s, cm.msg_img, cm.groupName, cm.time);
                        else
                           AppendRight("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                     }
                     else
                        AppendRight("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                  }
                  
                  else {
                     System.out.println("왼쪽 정렬 불렸음");
                     if(cm.getData().length()>=2){
                        if(cm.getData().substring(0,2).equals("C:"))
                           AppendImageLeft("[" + cm.userName + "]",cm.img_s, cm.msg_img, cm.groupName, cm.time);
                        else
                           AppendLeft("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                     }
                     else
                        AppendLeft("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                     /*AppendText("[" + cm.userName + "]", cm.groupName);// + " " + cm.getData());
                     AppendIcon(cm.img_s, cm.groupName);
                     AppendText("  "+ cm.getData(),cm.groupName);*/
                  }
                  //나중에 본인이 보내는거는 오른쪽배치하도록 if/else에 따로 append 만들기!!!
                  //chatRecord.add(cm.getData());
                  break;
               case "600": // 사용자 정보 받기
                  //userList=cm.userlist;
                  System.out.println("600받음");
                  ChatView chatView=new ChatView(cm.getData());
                  chatView.setName(cm.getData());
                  chatview_list.add(chatView);
                  ProfileAndChattingList.generalChat.setVisible(false);
                  //generalChat=new GeneralChat();
                  //System.out.println(cm.userlist);
                  break;
               case "601": // 사용자 정보 받기
                  System.out.println("받음");
                  
                  try{
                              //파일 객체 생성
                              File file = cm.file;
                              ChatMsg cc=new ChatMsg("SERVER","601",null);
                              cc.file=file;
                              try {
                              oos.writeObject(cc);
                           } catch (IOException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                           }
                              
                              
                              //입력 스트림 생성
                              FileReader filereader = new FileReader(file);
                            //입력 버퍼 생성
                              BufferedReader bufReader = new BufferedReader(filereader);
                              String line = "";
                              while((line = bufReader.readLine()) != null){
                                  System.out.println(line);
                              }
                             //.readLine()은 끝에 개행문자를 읽지 않는다.            
                              bufReader.close();
                          }catch (FileNotFoundException e) {
                              // TODO: handle exception
                          }catch(IOException e){
                              System.out.println(e);
                          }
                  //jlist주도록 하는거
                  //UserName.SendObject()
                  //generalChat.setVisible(false);
                  break;
               case "700": // 저장되어있는 채팅기록 불러오기
                  System.out.println("톡방 이름: "+cm.getData());
                  System.out.println("username: "+cm.userName+" img_s: "+cm.img_s+" data"+cm.getData()+" cm.groupName"+cm.groupName+" time:"+cm.time);
                  if(myUserName.equals(cm.userName)) { //본인 스스로에게 받
                     //AppendText(cm.getData());
                     if(cm.getData().length()>=2){
                        if(cm.getData().substring(0,2).equals("C:"))
                           AppendImageRight("[" + cm.userName + "]",cm.img_s, cm.msg_img, cm.groupName, cm.time);
                        else
                           AppendRight("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                     }
                     else
                        AppendRight("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                     /*AppendTextRight("[" + cm.userName + "]", cm.groupName);// + " " + cm.getData());
                     AppendIconRight(cm.img_s, cm.groupName);
                     AppendTextRight("  "+ cm.getData(), cm.groupName);*/
                  }
                  
                  else {
                     System.out.println("왼쪽 정렬 불렸음");
                     if(cm.getData().length()>=2){
                        if(cm.getData().substring(0,2).equals("C:"))
                           AppendImageLeft("[" + cm.userName + "]",cm.img_s, cm.msg_img, cm.groupName, cm.time);
                        else
                           AppendLeft("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                     }
                     else
                        AppendLeft("[" + cm.userName + "]",cm.img_s,"  "+ cm.getData(), cm.groupName, cm.time);
                     /*AppendText("[" + cm.userName + "]", cm.groupName);// + " " + cm.getData());
                     AppendIcon(cm.img_s, cm.groupName);
                     AppendText("  "+ cm.getData(),cm.groupName);*/
                  }
                  break;
               }
               
            } catch (IOException e) {
               //AppendText("ois.readObject() error");
               try {
                  ois.close();
                  oos.close();
                  socket.close();
                  break;
               } catch (Exception ee) {
                  break;
               } // catch문 끝
            } // 바깥 catch문끝

         }
      }
   }
   
   public static void AppendRight(String username, ImageIcon icon, String msg, String roomname, String time) {
      int index=-1;
      for(int i=0;i<chatview_list.size();i++) {
         if(chatview_list.get(i).getName().equals(roomname)) {
            chatview_list.get(i).appendRight(username,msg,icon,time);
         }
      }
   }
   public static void AppendLeft(String username, ImageIcon icon, String msg, String roomname, String time) {
      for(int i=0;i<chatview_list.size();i++) {
         if(chatview_list.get(i).getName().equals(roomname)) {
            chatview_list.get(i).appendLeft(username,msg,icon,time);
         }
      }
   }
   public static void AppendImageRight(String username, ImageIcon icon, ImageIcon msg, String roomname, String time) {
      int index=-1;
      System.out.println(chatview_list);
      for(int i=0;i<chatview_list.size();i++) {
         if(chatview_list.get(i).getName().equals(roomname)) {
            chatview_list.get(i).appendImageRight(username,msg,icon,time);
         }
      }
   }
   public static void AppendImageLeft(String username, ImageIcon icon, ImageIcon msg, String roomname, String time) {
      int index=-1;
      System.out.println(chatview_list);
      for(int i=0;i<chatview_list.size();i++) {
         if(chatview_list.get(i).getName().equals(roomname)) {
            chatview_list.get(i).appendImageLeft(username,msg,icon,time);
         }
      }
   }
   
   public static void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
      try {
         oos.writeObject(ob);
      } catch (IOException e) {
         // textArea.append("메세지 송신 에러!!\n");
         //AppendText("SendObject Error");
      }
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
              for(int i=0;i<chatview_list.size();i++) {
                 if(name.equals(chatview_list.get(i).getName())) {
                    j=1;
                    break;
                 }
              }
              if(j!=1) {
                  ChatView chatView=new ChatView(name);
                  chatView.setName(name);
                  chatview_list.add(chatView);
                  ChatMsg obcm = new ChatMsg(userName, "700", name);
                  SendObject(obcm);
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
   
}