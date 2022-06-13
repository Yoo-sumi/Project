import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class GeneralChat extends JFrame{
   private JPanel contentPane;
   private JCheckBox chckbxNewCheckBox[]=new JCheckBox[10];
   private Vector<String> selected_list=new Vector<String>();
   private JTextPane textPane;
   
   public GeneralChat() {
         //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setBounds(100, 100, 445, 573);
         contentPane = new JPanel();
         contentPane.setBackground(new Color(255,253,152));
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
         setContentPane(contentPane);
         contentPane.setLayout(null);
         selected_list.add(UserName.userName);
         
         JLabel lblNewLabel = new JLabel("대화상대 선택");
         lblNewLabel.setBounds(179, 39, 84, 33);
         contentPane.add(lblNewLabel);
         
         JLabel lblNewLabel_1 = new JLabel("채팅방 이름 설정");
         lblNewLabel_1.setBounds(168, 374, 95, 33);
         contentPane.add(lblNewLabel_1);
         
         Myaction action=new Myaction();
         JButton button = new JButton("완료");
         button.setBackground(new Color(255,255,255));
         button.setBounds(278, 471, 73, 38);
         button.addActionListener(action);
         contentPane.add(button);
         
         JPanel panel = new JPanel();
         panel.setBackground(Color.WHITE);
         panel.setBounds(82, 95, 269, 260);
         contentPane.add(panel);
         panel.setLayout(new GridLayout(10, 1, 0, 0));
         
         textPane = new JTextPane();
         textPane.setBounds(82, 405, 269, 46);
         contentPane.add(textPane);
         
         textPane.setText(UserName.userName);
         
         Myaction2 action2=new Myaction2();
         for(int i=0;i<UserName.userList.size();i++) {
            chckbxNewCheckBox[i]= new JCheckBox(UserName.userList.elementAt(i));
            chckbxNewCheckBox[i].setBackground(new Color(255,255,255));
            chckbxNewCheckBox[i].addActionListener(action2);
            panel.add(chckbxNewCheckBox[i]);
         }
         setVisible(true);
   }
   class Myaction2 implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
    	  String roomName=UserName.userName+" ";
    	  for(int j=0;j<UserName.userList.size();j++) {
              if(chckbxNewCheckBox[j].isSelected()) {
            	  roomName+=chckbxNewCheckBox[j].getText()+" ";
              }
           }
    	  textPane.setText(roomName.trim());
      }
   }
   class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
         for(int j=0;j<UserName.userList.size();j++) {
            if(chckbxNewCheckBox[j].isSelected()) {
               selected_list.add(chckbxNewCheckBox[j].getText());
            }
         }
         
         ChatMsg obcm = new ChatMsg(UserName.userName, "600", "chatopen");
         obcm.groupName=textPane.getText();
         obcm.userlist=selected_list;
         UserName.SendObject(obcm);
         
         
         //ChatView chatView=new ChatView();
         //setVisible(false);
      }
   }
}