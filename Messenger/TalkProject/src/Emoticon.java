import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


//import UserName.Myaction;
public class Emoticon extends JFrame{
   private JPanel contentPane;
   private FileDialog fd;
   private ChatMsg obcm;
   private String myRoomName;

   public Emoticon(String myRoomName) {
	     this.myRoomName=myRoomName;
         setBounds(100, 100, 420,440);
         contentPane = new JPanel();
         contentPane.setBackground(Color.WHITE);
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
         setContentPane(contentPane);
         contentPane.setLayout(null);
         
         JPanel angry=new JPanel() {
        		Image background=new ImageIcon("src/angry.PNG").getImage();
        		public void paint(Graphics g) {//그리는 함수
        			g.drawImage(background, 0, 0, null);//background를 그려줌		
        		}
        	};
         angry.setBounds(0, 0, 100, 100);
         angry.setToolTipText("(화남)");
         angry.setName("(화남)");
         angry.addMouseListener(new MyMouseListener());
         contentPane.add(angry);
         
         JPanel birthday=new JPanel() {
     		Image background=new ImageIcon("src/birthday.PNG").getImage();
     		public void paint(Graphics g) {//그리는 함수
     			g.drawImage(background, 0, 0, null);//background를 그려줌		
     		}
     	};
         birthday.setBounds(100, 0, 100, 100);
         birthday.setToolTipText("(생일)");
         birthday.setName("(생일)");
         birthday.addMouseListener(new MyMouseListener());
         contentPane.add(birthday);
         
         JPanel btnNewButton_2=new JPanel() {
      		Image background=new ImageIcon("src/bye.PNG").getImage();
      		public void paint(Graphics g) {//그리는 함수
      			g.drawImage(background, 0, 0, null);//background를 그려줌		
      		}
      	};
         btnNewButton_2.setBounds(200, 0, 100, 100);
         btnNewButton_2.setToolTipText("(잘가)");
         btnNewButton_2.setName("(잘가)");
         btnNewButton_2.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_2);
         
         JPanel btnNewButton_3=new JPanel() {
       		Image background=new ImageIcon("src/congratulation.PNG").getImage();
       		public void paint(Graphics g) {//그리는 함수
       			g.drawImage(background, 0, 0, null);//background를 그려줌		
       		}
       	};
         btnNewButton_3.setBounds(300, 0, 100, 100);
         btnNewButton_3.setToolTipText("(축하)");
         btnNewButton_3.setName("(축하)");
         btnNewButton_3.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_3);
         
         JPanel btnNewButton_4=new JPanel() {
        		Image background=new ImageIcon("src/eating.PNG").getImage();
        		public void paint(Graphics g) {//그리는 함수
        			g.drawImage(background, 0, 0, null);//background를 그려줌		
        		}
        	};
         btnNewButton_4.setBounds(0, 100, 100, 100);
         btnNewButton_4.setToolTipText("(먹기)");
         btnNewButton_4.setName("(먹기)");
         btnNewButton_4.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_4);
         
         JPanel btnNewButton_3_1=new JPanel() {
     		Image background=new ImageIcon("src/enjoy.PNG").getImage();
     		public void paint(Graphics g) {//그리는 함수
     			g.drawImage(background, 0, 0, null);//background를 그려줌		
     		}
     	};
         btnNewButton_3_1.setBounds(300, 100, 100, 100);
         btnNewButton_3_1.setToolTipText("(즐거움)");
         btnNewButton_3_1.setName("(즐거움)");
         btnNewButton_3_1.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_3_1);
         
         JPanel btnNewButton_1_1=new JPanel() {
      		Image background=new ImageIcon("src/fighting.PNG").getImage();
      		public void paint(Graphics g) {//그리는 함수
      			g.drawImage(background, 0, 0, null);//background를 그려줌		
      		}
      	};
         btnNewButton_1_1.setBounds(100, 100, 100, 100);
         btnNewButton_1_1.setToolTipText("(화이팅)");
         btnNewButton_1_1.setName("(화이팅)");
         btnNewButton_1_1.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_1_1);
         
         JPanel btnNewButton_2_1=new JPanel() {
       		Image background=new ImageIcon("src/good.PNG").getImage();
       		public void paint(Graphics g) {//그리는 함수
       			g.drawImage(background, 0, 0, null);//background를 그려줌		
       		}
       	};
         btnNewButton_2_1.setBounds(200, 100, 100, 100);
         btnNewButton_2_1.setToolTipText("(굿)");
         btnNewButton_2_1.setName("(굿)");
         btnNewButton_2_1.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_2_1);
         
         JPanel btnNewButton_5=new JPanel() {
        		Image background=new ImageIcon("src/goodnight.PNG").getImage();
        		public void paint(Graphics g) {//그리는 함수
        			g.drawImage(background, 0, 0, null);//background를 그려줌		
        		}
        	};
         btnNewButton_5.setBounds(0, 200, 100, 100);
         btnNewButton_5.setToolTipText("(잘자)");
         btnNewButton_5.setName("(잘자)");
         btnNewButton_5.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_5);
         
         JPanel btnNewButton_3_1_1=new JPanel() {
     		Image background=new ImageIcon("src/happy.PNG").getImage();
     		public void paint(Graphics g) {//그리는 함수
     			g.drawImage(background, 0, 0, null);//background를 그려줌		
     		}
     	};
         btnNewButton_3_1_1.setBounds(300, 300, 100, 100);
         btnNewButton_3_1_1.setToolTipText("(행복)");
         btnNewButton_3_1_1.setName("(행복)");
         btnNewButton_3_1_1.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_3_1_1);
         
         JPanel btnNewButton_1_2=new JPanel() {
      		Image background=new ImageIcon("src/hi.PNG").getImage();
      		public void paint(Graphics g) {//그리는 함수
      			g.drawImage(background, 0, 0, null);//background를 그려줌		
      		}
      	};
         btnNewButton_1_2.setBounds(100, 200, 100, 100);
         btnNewButton_1_2.setToolTipText("(안녕)");
         btnNewButton_1_2.setName("(안녕)");
         btnNewButton_1_2.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_1_2);
         
         JPanel btnNewButton_2_2=new JPanel() {
       		Image background=new ImageIcon("src/kijul.PNG").getImage();
       		public void paint(Graphics g) {//그리는 함수
       			g.drawImage(background, 0, 0, null);//background를 그려줌		
       		}
       	};
         btnNewButton_2_2.setBounds(200, 200, 100, 100);
         btnNewButton_2_2.setToolTipText("(죽음)");
         btnNewButton_2_2.setName("(죽음)");
         btnNewButton_2_2.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_2_2);
         
         JPanel btnNewButton_3_2=new JPanel() {
        		Image background=new ImageIcon("src/sad.PNG").getImage();
        		public void paint(Graphics g) {//그리는 함수
        			g.drawImage(background, 0, 0, null);//background를 그려줌		
        		}
        	};
         btnNewButton_3_2.setBounds(300, 200, 100, 100);
         btnNewButton_3_2.setToolTipText("(슬픔)");
         btnNewButton_3_2.setName("(슬픔)");
         btnNewButton_3_2.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_3_2);
         
         JPanel btnNewButton_4_1=new JPanel() {
     		Image background=new ImageIcon("src/thanku.PNG").getImage();
     		public void paint(Graphics g) {//그리는 함수
     			g.drawImage(background, 0, 0, null);//background를 그려줌		
     		}
     	};
         btnNewButton_4_1.setBounds(0, 300, 100, 100);
         btnNewButton_4_1.setToolTipText("(감사)");
         btnNewButton_4_1.setName("(감사)");
         btnNewButton_4_1.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_4_1);
         
         JPanel btnNewButton_1_1_1=new JPanel() {
      		Image background=new ImageIcon("src/why.PNG").getImage();
      		public void paint(Graphics g) {//그리는 함수
      			g.drawImage(background, 0, 0, null);//background를 그려줌		
      		}
      	};
         btnNewButton_1_1_1.setBounds(100, 300, 100, 100);
         btnNewButton_1_1_1.setToolTipText("(왜)");
         btnNewButton_1_1_1.setName("(왜)");
         btnNewButton_1_1_1.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_1_1_1);
         
         JPanel btnNewButton_2_1_1=new JPanel() {
       		Image background=new ImageIcon("src/yes.PNG").getImage();
       		public void paint(Graphics g) {//그리는 함수
       			g.drawImage(background, 0, 0, null);//background를 그려줌		
       		}
       	};
         btnNewButton_2_1_1.setBounds(200, 300, 100, 100);
         btnNewButton_2_1_1.setToolTipText("(좋아)");
         btnNewButton_2_1_1.setName("(좋아)");
         btnNewButton_2_1_1.addMouseListener(new MyMouseListener());
         contentPane.add(btnNewButton_2_1_1);
         
         setVisible(true);
   }
   class MyMouseListener implements MouseListener{

       @Override
       public void mouseClicked(MouseEvent e) {
           if (e.getClickCount()==2){
        	   JPanel panel=(JPanel)e.getSource();
        	   ChatMsg obcm = new ChatMsg(myRoomName, "500",panel.getName());
        	   obcm.userName = UserName.userName;
        	   obcm.setImg(UserName.sub_userProfile);
        	   UserName.SendObject(obcm);
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