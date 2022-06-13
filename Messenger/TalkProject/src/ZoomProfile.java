import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class ZoomProfile extends JFrame{
   private JPanel contentPane;
   private Image background; //#
   private static int count=0; //#

   public ZoomProfile(ImageIcon image, boolean check) {
	  System.out.println(image);
      //이미지 크기 변경
      Image img = image.getImage();
      int width = img.getWidth(null);
      int height = img.getHeight(null);
      float ratio = (float) (width/450.0);

      if(width>height) {
      //width를 450에 맞추어서 height크기 조절
         width = 450;
         height = (int) (height/ratio);
         }
      else {
         height = 450;
         width = (int) (width/ratio);
      }

      
      Image changeImg = img.getScaledInstance(width, height, Image.SCALE_REPLICATE);
      //ImageIcon new_img = new ImageIcon(changeImg);
      //profile_img_s = new_img;
      //이미지 크기를 가로 나 세로가 더 큰곳에 맞추어서 더 큰곳을 450으로 만들고
      //if 가로나 세로가 450보다 크면 그쪽을 450에 맞추고
      //만약에 더 작으면? 그냥 크기 조절 안하고 붙뎌넣기..... . 
         setBounds(100, 100, 450, 450);
         contentPane = new JPanel();
         contentPane.setBackground(Color.WHITE);
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
         setContentPane(contentPane);
         contentPane.setLayout(null);
         
         JPanel panel = new JPanel(){
            
            public void paint(Graphics g) {//그리는 함수
               background=changeImg;//image.getImage();
               g.drawImage(background, 0, 0, null);//background를 그려줌      
            }
         };
         panel.setBounds(0, 0, 421, 436);
         
         JButton btnNewButton = new JButton("저장"); //#
         btnNewButton.setBackground(new Color(255,255,255));
         btnNewButton.addActionListener(new ActionListener() { //#
            public void actionPerformed(ActionEvent e) { //#
               try {
                         // 저장할 이미지의 크기와 타입을 잡아줌.
                         BufferedImage bufferedImage = new BufferedImage(background.getWidth(getParent()), background.getHeight(getParent()), BufferedImage.TYPE_INT_BGR);
                         bufferedImage.createGraphics().drawImage(background, 0, 0,ZoomProfile.this);
                          
                         // 해당경로에 이미지를 저장함.
                         Calendar cal = Calendar.getInstance();
                         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                         String time = formatter.format(cal.getTime());

                         ImageIO.write(bufferedImage, "jpg", new File("save_images/"+time+".jpg"));
                         //ImageIO.write(bufferedImage, "jpg", new File(Integer.toString(++count)+".jpg"));
                     } catch(Exception e1) {
                         e1.printStackTrace();   }
         }});
         panel.setLayout(null);
         btnNewButton.setBounds(187, 376, 72, 23); //#
         panel.add(btnNewButton); //#
         btnNewButton.setVisible(check);
         
         contentPane.add(panel);
         setVisible(true);
   }
}