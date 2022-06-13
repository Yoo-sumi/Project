import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

//import UserName.Myaction;
public class Profile extends JFrame{
   private JPanel contentPane;
   private JButton btn_profile;
   private FileDialog fd;
   private ChatMsg obcm;

   public Profile() {
         //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setBounds(100, 100, 480, 538);
         contentPane = new JPanel();
         contentPane.setBackground(new Color(255,253,152));
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
         setContentPane(contentPane);
         contentPane.setLayout(null);
         
         JLabel lblNewLabel = new JLabel("User Name");
         lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
         lblNewLabel.setBounds(199, 43, 82, 33);
         lblNewLabel.setText(UserName.userName);
         contentPane.add(lblNewLabel);
         
         Myaction action=new Myaction();
         JButton btnConnect = new JButton("\uC644\uB8CC");
         btnConnect.setBackground(Color.WHITE);
         btnConnect.setBounds(262, 343, 82, 23);
         btnConnect.addActionListener(action);
         contentPane.add(btnConnect);
         
         btn_profile = new JButton("+");
         btn_profile.setBackground(new Color(255,255,255));
         btn_profile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Frame frame= new Frame("이미지첨부");
               fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
               fd.setVisible(true);
               ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
               System.out.println(fd.getDirectory() + fd.getFile());
               btn_profile.setIcon(img);
               
               obcm = new ChatMsg(UserName.userName, "300", "IMG");
               ImageIcon image = new ImageIcon(fd.getDirectory() + fd.getFile());
               obcm.setImg(image);
               //UserName.SendObject(obcm);
               //img.setImage(fd.getDirectory() + fd.getFile());
            }
         });
         btn_profile.setBackground(Color.WHITE);
         btn_profile.setBounds(122, 91, 221, 215);
         contentPane.add(btn_profile);
         
         JButton btn_basic = new JButton("기본프사");
         btn_basic.setBackground(new Color(255,255,255));
         btn_basic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //ImageIcon img = new ImageIcon("src/basic.jpeg");
               obcm = new ChatMsg(UserName.userName, "300", "IMG");
               
               ImageIcon image = new ImageIcon("src/basic.jpeg");
               btn_profile.setIcon(image);
               obcm.setImg(image);
               //UserName.SendObject(obcm);
            }
         });
         btn_basic.setBounds(122, 343, 97, 23);
         contentPane.add(btn_basic);
         
         
         
         setVisible(true);
   }
   class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
         //ProfileAndChattingList list = new ProfileAndChattingList();
         UserName.SendObject(obcm);
         setVisible(false);
         
      }
   }
}