//JavaObjServer.java ObjectStream 기반 채팅 Server

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import JavaObjServer.UserService;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JavaObjServer extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    JTextArea textArea;
    private JTextField txtPortNumber;

    private ServerSocket socket; // 서버소켓
    private Socket client_socket; // accept() 에서 생성된 client 소켓
    private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
    private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의

    //기본 프로필 사진
    private ImageIcon basic_profile = new ImageIcon("src/basic.jpeg");
    //기본 프로필 사진 섬네일
    private ImageIcon basic_profile_s = new ImageIcon("src/basic.png");
    //로그인 성공한 UserService 객체 리스트
    private Vector<UserService> real_user_vc = new Vector<UserService>();
    //현재 로그인 된 아이디 리스트
    private Vector<String> login_list = new Vector<String>();
    //아이디 리스트 : user1~user10
    private Vector<String> id_list=new Vector<String>();
    //프사 리스트 : user1~user10
    private Vector<ImageIcon> img_list = new Vector<ImageIcon>();
    //프사 리스트(섬네일) : user~user10
    private Vector<ImageIcon> img_list_s = new Vector<ImageIcon>();
    //그룹에 속한 유저들 Vector들을 가지고있는 Vector : Vector[ vector(user1, user2), vector(user2, user4, user7), ... ]
    public Vector<Vector> group_userlist = new Vector<Vector>();//이안에 userlist 여러개...
    //그룹 이름 리스트 Vector : user1 user2, user3 user8 user9, ...
    public Vector<String> groupnamelist= new Vector<String>();//그룹톡방이름리스트
    ///user가 가지고 있는 그룹톡방 vector 객체 리스트 : user1 - vector(user1 user2, user1 user5 user10, ...) / user2 - ...
    public Vector[] user_grouplist = new Vector[10];
    //Client 이미지 경로, 이미지 사진
    public HashMap<String, ImageIcon> chat_images = new HashMap<>();


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JavaObjServer frame = new JavaObjServer();
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
    public JavaObjServer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 338, 440);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 10, 300, 298);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);

        JLabel lblNewLabel = new JLabel("Port Number");
        lblNewLabel.setBounds(13, 318, 87, 26);
        contentPane.add(lblNewLabel);

        txtPortNumber = new JTextField();
        txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
        txtPortNumber.setText("30000");
        txtPortNumber.setBounds(112, 318, 199, 26);
        contentPane.add(txtPortNumber);
        txtPortNumber.setColumns(10);


        //미리 아이디 생성
        id_list.add("user1");
        id_list.add("user2");
        id_list.add("user3");
        id_list.add("user4");
        id_list.add("user5");
        id_list.add("user6");
        id_list.add("user7");
        id_list.add("user8");
        id_list.add("user9");
        id_list.add("user10");

        //각 유저마다 기본 프로필 사진 설정.
        for(int i=0;i<10;i++) {
            img_list.add(new ImageIcon("src/basic.jpeg"));
            img_list_s.add(new ImageIcon("src/basic.png"));
            user_grouplist[i]=new Vector();
        }

        /*
        //이모티콘 종류 생성
        emotions.put("(화남)", new ImageIcon("src/angry.png"));
        emotions.put("(생일)", new ImageIcon("src/birthday.png"));
        emotions.put("(잘가)", new ImageIcon("src/bye.png"));
        emotions.put("(축하)", new ImageIcon("src/congratulation.png"));
        emotions.put("(먹음)", new ImageIcon("src/eating.png"));
        emotions.put("(즐거움)", new ImageIcon("src/enjoy.png"));
        emotions.put("(화이팅)", new ImageIcon("src/fighting.png"));
        emotions.put("(굿)", new ImageIcon("src/good.png"));
        emotions.put("(굿밤)", new ImageIcon("src/goodnight.png"));
        emotions.put("(기쁨)", new ImageIcon("src/happy.png"));
        emotions.put("(안녕)", new ImageIcon("src/hi.png"));
        emotions.put("(기절)", new ImageIcon("src/kijul.png"));
        emotions.put("(슬픔)", new ImageIcon("src/sad.png"));
        emotions.put("(감사)", new ImageIcon("src/thanks.png"));
        emotions.put("(왜)", new ImageIcon("src/why.png"));
        emotions.put("(넵)", new ImageIcon("src/yes.png"));
        */


        JButton btnServerStart = new JButton("Server Start");
        btnServerStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
                } catch (NumberFormatException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                AppendText("Chat Server Running..");
                btnServerStart.setText("Chat Server Running..");
                btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
                txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
                AcceptServer accept_server = new AcceptServer();
                accept_server.start();
            }
        });
        btnServerStart.setBounds(12, 356, 300, 35);
        contentPane.add(btnServerStart);
    }

    // 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
    class AcceptServer extends Thread {
        @SuppressWarnings("unchecked")
        public void run() {
            while (true) { // 사용자 접속을 계속해서 받기 위해 while문
                try {
                    AppendText("Waiting new clients ...");
                    client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
                    AppendText("새로운 참가자 from " + client_socket);
                    // User 당 하나씩 Thread 생성
                    UserService new_user = new UserService(client_socket);
                    UserVec.add(new_user); // 새로운 참가자 배열에 추가
                    new_user.start(); // 만든 객체의 스레드 실행
                    //AppendText("현재 참가자 수 " + UserVec.size());
                } catch (IOException e) {
                    AppendText("accept() error");
                    // System.exit(0);
                }
            }
        }
    }

    public void AppendText(String str) {
        // textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
        textArea.append(str + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    public void AppendObject(ChatMsg msg) {
        // textArea.append("사용자로부터 들어온 object : " + str+"\n");
        textArea.append("code = " + msg.getCode() + "\n");
        textArea.append("id = " + msg.getId() + "\n");
        textArea.append("data = " + msg.getData() + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    // User 당 생성되는 Thread
    // Read One 에서 대기 -> Write All
    class UserService extends Thread  {
        private InputStream is;
        private OutputStream os;
        private DataInputStream dis;
        private DataOutputStream dos;

        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        private Socket client_socket;
        private Vector user_vc; //그냥 접속 시도한 모든 유저들 (로그인 성공/실패 여부 상관없이)

        public String UserName = ""; // 유저 이름 : user1, user2, user3, ...
        public String UserStatus = "";

        //프로필사진 원본
        private ImageIcon profile_img = basic_profile; // 처음엔 기본 프로필로 설정
        //프로필사진 섬네일
        private ImageIcon profile_img_s = basic_profile_s; // 처음엔 기본 프로필로 설정


        public UserService(Socket client_socket) {
            // TODO Auto-generated constructor stub
            // 매개변수로 넘어온 자료 저장
            this.client_socket = client_socket;
            this.user_vc = UserVec;


            try {
                oos = new ObjectOutputStream(client_socket.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(client_socket.getInputStream());
            } catch (Exception e) {
                AppendText("userService error");
            }
        }

        public synchronized void Login() {
            boolean loginCheck = true;
            boolean idCheck = false;
            //아이디 리스트에 있는지 체크
            for(int i=0;i<id_list.size();i++) {
                //아이디 리스트에 있는지 체크 : 존재하는 아이디인지 확인하기 위함.
                if(id_list.elementAt(i).equals(UserName))
                    idCheck=true;
            }
            //이미 로그인 한건지 체크 : 중복 로그인 방지
            for(int i=0;i<login_list.size();i++) {
                if(login_list.elementAt(i).equals(UserName))
                    loginCheck=false;
            }

            if(idCheck && loginCheck) {
                real_user_vc.add(this); // 로그인 성공한 UserService 객체만 모아두는 벡터에 추가
                login_list.add(UserName); // 현재 로그인 리스트에 추가

                String u = UserName;
                String sub = u.substring(u.length()-1);
                int a = Integer.parseInt(sub)-1;
                if (a==-1)
                    a=9;

                //서버에서 본인프사 찾아오기
                profile_img = img_list.get(a);
                profile_img_s = img_list_s.get(a);

                //client에게 로그인 성공 메세지 보내기
                ChatMsg obcm = new ChatMsg("SERVER", "200", "success");
                obcm.img=profile_img;//본인프사 원본
                obcm.img_s=profile_img_s;//본인프사 섬네일

                //본인 카톡방 리스트 찾아오기
                //chat_record(마지막 채팅 기록), timeVector(마지막 시간 기록), chatlist2(내가 들어간 채팅방 이름 목록) 필요.
                Vector<String> cRec = new Vector<String>();
                Vector<String> tVec = new Vector<String>();

                for (int k = 0; k < user_grouplist[a].size(); k++) {
                    String g_name = (String) user_grouplist[a].get(k); // 채팅방 이름
                    obcm.chatlist2[k] = g_name; // 내가 포함된 채팅방 이름 넣기

                    System.out.println("chatlist : " + obcm.chatlist2[k]);

                    String last_msg = "";
                    String last_time = "";
                    try {
                        String file_name = g_name + ".txt"; // 그룹톡방이름.txt 파일에서 마지막 채팅/시간 읽어오기
                        RandomAccessFile file = new RandomAccessFile("src/" + file_name, "r");
                        long fileSize = file.length();
                        long pos = fileSize - 1;
                        int count = 0;
                        while (true) {
                            file.seek(pos);
                            if (file.readByte() == '\n') {
                                if (count == 2) break;
                                count++;
                            }
                            pos--;
                        }
                        file.seek(pos + 1);
                        last_msg = file.readLine().replace("\0", ""); // 마지막 메세지
                        last_msg = new String(last_msg.getBytes("iso-8859-1"), "utf-8");//

                        last_time = file.readLine().replace("\0", ""); // 마지막 시간
                        cRec.add(last_msg); // 마지막 메세지만 포함된 벡터
                        tVec.add(last_time); // 마지막 시간만 포함된 벡터
                        System.out.println(u+"/"+last_msg);
                    } catch (FileNotFoundException e) {
                        // TODO: handle exception
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }

                //size줄때 charRecord가 없느 ㄴ 경우 하나 뺌
                obcm.size = user_grouplist[a].size(); // 본인이 들어간 톡방 개수
                if(cRec.size() == 0)
                    obcm.size = obcm.size -1;
                obcm.chatRecord = cRec;
                obcm.timeVector = tVec;

                System.out.println("obcm.chatRecord"+ obcm.chatRecord);
                System.out.println("obcm.timeVecor"+ obcm.timeVector);
                AppendText("현재 참가자 수 " + real_user_vc.size());
                AppendText("새로운 참가자 " + UserName + " 입장.");

                //유저리스트 보내주기, 본인제외
                Vector<String> sub_id_list=new Vector<String>();
                for(int i=0;i<10;i++) {
                    if(UserName.equals(id_list.get(i))) {
                        continue;
                    }
                    sub_id_list.add(id_list.get(i));
                }
                obcm.userlist=sub_id_list;
                obcm.img_list=img_list; // 본인,친구 프사 리스트
                obcm.img_list_s=img_list_s; // 본인,친구 프사섬네일 리스트

                for(int i=0;i<10;i++) {
                    obcm.img_list2[i]= null;
                }
                for(int i=0;i<10;i++) {
                    obcm.img_list2[i]= img_list.get(i);
                }


                try {
                    oos.writeObject(obcm);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }

            if(!idCheck) {
                //client에게 로그인 실패 메세지 보내기
                AppendText(UserName + " 은 존재하지 않는 아이디 입니다.");
                String msg = "not_exist";
                ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
                try {
                    oos.writeObject(obcm);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(!loginCheck) {
                //client에게 로그인 실패 메세지 보내기
                AppendText(UserName + " 은 이미 로그인 되어있습니다.");
                String msg = "overlap";
                ChatMsg obcm = new ChatMsg("SERVER", "200", msg);

                try {
                    oos.writeObject(obcm);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //WriteOne("200");//error
        }

        public synchronized void Logout() {
            for(int i=0;i<login_list.size();i++) {
                if(login_list.get(i).equals(UserName)) {
                    login_list.remove(i);
                    break;
                }
            }

            String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
            UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
            real_user_vc.removeElement(this); //찐 유저중에서도 지움..
            //WriteAll(msg); // 나를 제외한 다른 User들에게 전송
            AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + real_user_vc.size());
        }


        // 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
        public synchronized void WriteAll(String str) {
            for (int i = 0; i < real_user_vc.size(); i++) {
                UserService user = (UserService) real_user_vc.elementAt(i);
                if (user.UserStatus == "O")
                    user.WriteOne(str);
            }
        }
        // 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
        public synchronized void WriteAllObject(Object ob) {
            for (int i = 0; i < real_user_vc.size(); i++) {
                UserService user = (UserService) real_user_vc.elementAt(i);
                if (user.UserStatus == "O")
                    user.WriteOneObject(ob);
            }
        }



        // Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
        public byte[] MakePacket(String msg) {
            byte[] packet = new byte[BUF_LEN];
            byte[] bb = null;
            int i;
            for (i = 0; i < BUF_LEN; i++)
                packet[i] = 0;
            try {
                bb = msg.getBytes("euc-kr");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (i = 0; i < bb.length; i++)
                packet[i] = bb[i];
            return packet;
        }

        // UserService Thread가 담당하는 Client 에게 1:1 전송
        public synchronized void WriteOne(String msg) {
            try {
                // dos.writeUTF(msg);
//				byte[] bb;
//				bb = MakePacket(msg);
//				dos.write(bb, 0, bb.length);
                ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
                oos.writeObject(obcm);
            } catch (IOException e) {
                AppendText("dos.writeObject() error");
                try {
//					dos.close();
//					dis.close();
                    ois.close();
                    oos.close();
                    client_socket.close();
                    client_socket = null;
                    ois = null;
                    oos = null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Logout(); // 에러가난 현재 객체를 벡터에서 지운다
            }
        }

        // 귓속말 전송
        public synchronized void WritePrivate(String msg) {
            try {
                ChatMsg obcm = new ChatMsg("귓속말", "200", msg);
                oos.writeObject(obcm);
            } catch (IOException e) {
                AppendText("dos.writeObject() error");
                try {
                    oos.close();
                    client_socket.close();
                    client_socket = null;
                    ois = null;
                    oos = null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Logout(); // 에러가난 현재 객체를 벡터에서 지운다
            }
        }

        //저장할때 클라이언트쪽 사진 경로를 저장해서
        //나중에서 다시 불러올때, 그때 한꺼번에 보내는데.. 경로명이 C;//어쩌고니까.
        public synchronized void WriteGroup(Object cm) {
            ChatMsg c = (ChatMsg)cm;
            int idx=0;
            for(int k=0;k<groupnamelist.size();k++) {
                if(groupnamelist.get(k).equals(c.groupName)) {
                    idx = k;
                    //System.out.println("idx : " + idx);
                }
            }

            Vector<String> userli = (Vector<String>)group_userlist.get(idx); // 유저리스트
            //System.out.println("톡방에 있는 사람 리스트 : " + aa);
            //채팅방이름준거랑 대조해서 인덱스뽑아내고 그 인덱스로 체크리스트의
            //인덱스로 뽑아서 get으로 하며 ㄴ벡터로 나오고 그거의 사이즈러
            for (int i = 0; i < userli.size(); i++) {
                String u = userli.get(i);//그 톡방에있는 사람들.
                for(int j=0;j<real_user_vc.size();j++) {
                    UserService user = (UserService) real_user_vc.elementAt(j);
                    if(u.equals(user.UserName)) {
                        //System.out.println("톡보내줄예정인사람: "+u);
                        String sub = u.substring(u.length()-1);
                        int a = Integer.parseInt(sub);
                        if (a==0)
                            a=10;
                        //user_grouplist[a-1]에는 a-1번째 유저가 속해있는 그룹 vector객체가 있음
                        c.chatlist=user_grouplist[a-1];
                        for(int n=0;n<user_grouplist[a-1].size();n++) {
                            c.chatlist2[n]=(String) user_grouplist[a-1].get(n);
                        }


                        //chatlist보내면서 마지막 대화내용도 보내야함.

                        //cRec에는 각 chatlist에 있는 그룹명마다의 마지막 메세지들이 보관되어있음.

                        Vector<String> cRec = new Vector<String>();
                        Vector<String> tVec = new Vector<String>();

                        for(int k=0;k<user_grouplist[a-1].size();k++) {
                            String g_name = (String) user_grouplist[a-1].get(k);
                            String last_msg="";
                            String last_time="";
                            try{
                                String file_name = g_name + ".txt";
                                RandomAccessFile file = new RandomAccessFile("src/"+file_name,"r");
                                long fileSize = file.length();
                                long pos = fileSize -1;
                                int count = 0;
                                while(true) {
                                    file.seek(pos);
                                    if(file.readByte()=='\n') {
                                        if(count == 2) break;
                                        count++;
                                    }
                                    pos--;
                                }
                                file.seek(pos+1);
                                byte[] buff = new byte[128];
                                last_msg = file.readLine().replace("\0", "");
                                last_msg = new String(last_msg.getBytes("iso-8859-1"), "utf-8");//
                                last_time = file.readLine().replace("\0", "");
                                cRec.add(last_msg);
                                tVec.add(last_time);
                                System.out.println(u+"/"+last_msg);
                            }catch (FileNotFoundException e) {
                                // TODO: handle exception
                            }catch(IOException e){
                                System.out.println(e);
                            }
                        }
                        c.chatRecord = cRec;
                        c.timeVector = tVec;
                        c.size = user_grouplist[a-1].size();
                        if(cRec.size() == 0)
                            c.size = c.size -1;
                        System.out.println("obcm.chatRecord"+ c.chatRecord);
                        System.out.println("obcm.timeVecor"+ c.timeVector);

                        //System.out.println("톡방에 있는 사람 리스트 : " + c.chatlist);
                        //System.out.println("보내기전 그룹톡방이름 체크 : " +c.groupName);
                        //System.out.println("보내기전 유저 네임 체크 : "+ c.userName);
                        //System.out.println("보내기전 유저 네임 체크 : "+ c.groupName);
                        user.WriteOneObject(c);
                    }

                }
            }


            //그룹이름명.txt에 추가하기


			/*
			try {
				ChatMsg obcm = cm;

				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
//					dos.close();
//					dis.close();
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}*/
        }
        public synchronized void WriteOneObject(Object ob) {
            try {
                oos.writeObject(ob);
            }
            catch (IOException e) {
                AppendText("oos.writeObject(ob) error");
                try {
                    ois.close();
                    oos.close();
                    client_socket.close();
                    client_socket = null;
                    ois = null;
                    oos = null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Logout();
            }
        }

        public void run() {
            while (true) { // 사용자 접속을 계속해서 받기 위해 while문
                try {
                    Object obcm = null;
                    String msg = null;
                    ChatMsg cm = null;
                    if (socket == null)
                        break;
                    try {
                        obcm = ois.readObject();
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return;
                    }
                    if (obcm == null)
                        break;
                    if (obcm instanceof ChatMsg) {
                        cm = (ChatMsg) obcm;
                        AppendObject(cm);
                    } else
                        continue;
                    //System.out.println(cm.getCode());
                    if (cm.getCode().matches("100")) {
                        UserName = cm.getId();
                        UserStatus = "O"; // Online 상태
                        Login();
                    }
                    else if (cm.getCode().matches("200")) {
                        msg = String.format("[%s] %s", cm.getId(), cm.getData());
                        AppendText(msg); // server 화면에 출력
                        String[] args = msg.split(" "); // 단어들을 분리한다.
                        if (args.length == 1) { // Enter key 만 들어온 경우 Wakeup 처리만 한다.
                            UserStatus = "O";
                        }

                        else if (args[1].matches("/exit")) {
                            Logout();
                            break;
                        } else if (args[1].matches("/list")) {
                            WriteOne("User list\n");
                            WriteOne("Name\tStatus\n");
                            WriteOne("-----------------------------\n");
                            for (int i = 0; i < user_vc.size(); i++) {
                                UserService user = (UserService) user_vc.elementAt(i);
                                WriteOne(user.UserName + "\t" + user.UserStatus + "\n");
                            }
                            WriteOne("-----------------------------\n");
                        } else if (args[1].matches("/sleep")) {
                            UserStatus = "S";
                        } else if (args[1].matches("/wakeup")) {
                            UserStatus = "O";
                        } else if (args[1].matches("/to")) { // 귓속말
                            for (int i = 0; i < user_vc.size(); i++) {
                                UserService user = (UserService) user_vc.elementAt(i);
                                if (user.UserName.matches(args[2]) && user.UserStatus.matches("O")) {
                                    String msg2 = "";
                                    for (int j = 3; j < args.length; j++) {// 실제 message 부분
                                        msg2 += args[j];
                                        if (j < args.length - 1)
                                            msg2 += " ";
                                    }
                                    // /to 빼고.. [귓속말] [user1] Hello user2..
                                    user.WritePrivate(args[0] + " " + msg2 + "\n");
                                    //user.WriteOne("[귓속말] " + args[0] + " " + msg2 + "\n");
                                    break;
                                }
                            }
                        } else { // 일반 채팅 메시지
                            UserStatus = "O";
                            //WriteAll(msg + "\n"); // Write All
                            WriteAllObject(cm);
                        }
                    }
                    //프로필 사진 변경
                    //새로고침 버튼을 만들어서... 만약 새로고침 버튼을 누르면 프로토콜 301이 오는거임
                    //그러면 서버에 저장된 모든 프로필 작은사진 & 프로필 원본 보내줘서 301로 다시 보내주면
                    //클라이언트에서는 전부다 사진 교체하고 repaint하셈.~
                    else if(cm.getCode().matches("300")) {
                        //나중에 프로필 클릭했을때 원본도 나와야하니까 원본도 따로 저장해놓는 코드 만들기..
                        ImageIcon ic = cm.img;
                        profile_img = ic;

                        //이미지 크기 변경
                        Image img = ic.getImage();
                        Image changeImg = img.getScaledInstance(70, 70, Image.SCALE_REPLICATE);
                        ImageIcon new_img = new ImageIcon(changeImg);
                        profile_img_s = new_img;

                        //img list랑 img list _ s 수정해서 같이 보내줘야함!!!!
                        String u = UserName;
                        String sub = u.substring(u.length()-1);
                        int a = Integer.parseInt(sub)-1;
                        if (a==-1)
                            a=9;
                        //img_list.get(a) = ic_image;//.setIcon(ic_image);
                        //img_list_s.get(a).setImage(changeImg);
                        img_list.set(a, ic);
                        img_list_s.set(a, new_img);

                        AppendText("프로필 사진 설정 완료.");

                        //user1, user3 현재 접속중
                        //user1이 프로필 바꿈
                        //그럼 user3입장에서도 user1의 사진이 바뀌어야함
                        //그리고 user1이 프로필 바꾸고 본
                        ChatMsg prof = new ChatMsg("SERVER", "400", UserName);
                        prof.img=profile_img;
                        prof.img_s =profile_img_s; //작은사진으로 프로필 사진 보이게...
                        prof.img_list = img_list;
                        prof.img_list_s = img_list_s;

                        for(int i=0;i<10;i++) {
                            prof.img_list2[i]= null;
                        }
                        for(int i=0;i<10;i++) {
                            prof.img_list2[i] = img_list.get(i);
                            //System.out.print(prof.img_list2[i]);
                        }
                        //System.out.println(prof.img_list2);
                        synchronized(this) {
                            try {
                                oos.writeObject(prof);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                    }
                    else if(cm.getCode().matches("301")) {
                        String name = cm.getId();
                        String sub = name.substring(name.length()-1);
                        int a = Integer.parseInt(sub)-1;
                        if (a==-1)
                            a=9;

                        ImageIcon icon = img_list.get(a);
                        System.out.println("301받았음");



                            ChatMsg cmsg = new ChatMsg("SERVER", "301", null);
                            System.out.println("301불렸음");
                            cmsg.img = icon;
                            synchronized(this) {
                                try {
                                    oos.writeObject(cmsg);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }


                    }
                    else if(cm.getCode().matches("401")) {
                        Vector<String> sub_userlist = cm.userlist;

                        for(int i=0;i<9;i++) {
                            String name = sub_userlist.get(i);
                            String sub = name.substring(name.length()-1);
                            int a = Integer.parseInt(sub)-1;
                            if (a==-1)
                                a=9;
                            ImageIcon icon = img_list.get(a);

                            ChatMsg cmsg = new ChatMsg("SERVER", "401", Integer.toString(i));
                            cmsg.img = icon;
                            System.out.println("401불렸음");
                            //System.out.println(cmsg.img_list);
                            synchronized(this) {
                                try {
                                    oos.writeObject(cmsg);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }


                    }
                    else if(cm.getCode().matches("500")) {
                        //처음 메세지 받았을때 다른 유저들도 카톡 받고 각자 채팅 리스트에 그 그룹 톡방이 보이도록 만들기.

                        String group_name = cm.getId(); //그룹 이름 받기
                        String group_msg = cm.getData(); //메세지(또는 이미지경로) 받기
                        String user_name = cm.userName; //채팅 보낸 사람 이름

                        //현재 시간
                        Calendar cal = Calendar.getInstance();
                        int hour = cal.get(Calendar.HOUR_OF_DAY);
                        int min = cal.get(Calendar.MINUTE);
                        String time = Integer.toString(hour) + ":" +Integer.toString(min);

                        ChatMsg cmsg = new ChatMsg(group_name, "500", group_msg);

                        //2글자 이내일때도 오류안나도록 만들기.~~~~~~~~~~~
                        if (group_msg.length()>=2) {
                            if(group_msg.substring(0,2).equals("C:")) { // 이미지 x 일반메세지 o
                                ImageIcon image = cm.msg_img;
                                chat_images.put(group_msg, image);
                                cmsg.msg_img = image; //이제 클라이언트에서 img가 있으면 img 출력하고 없으면 메세지 출력하도록.
                                //cmsg.setCode("501");
                            }
                        }

                        cmsg.groupName = group_name;
                        cmsg.userName = user_name;
                        cmsg.img = cm.img;
                        cmsg.time = time;
                        //System.out.println("group가기전 user name :"+ cmsg.userName);
                        //System.out.println("group가기전 group name :"+ cmsg.groupName);
                        //그룹 톡방 파일에 저장
                        try{
                            //파일 객체 생성
                            String file_name = group_name + ".txt";
                            File file = new File("src/"+file_name);
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                            String str = user_name + "\n" + group_msg + "\n" + time + "\n";
                            writer.write(str);
                            writer.close();
                        }catch (FileNotFoundException e) {
                            // TODO: handle exception
                        }catch(IOException e){
                            System.out.println(e);
                        }

                        //만약 group_msg가 (기쁨), (슬픔) 등 이모티콘 일 경우, 이모티콘 이미지 주기
                      //  for(int i=0;i<emotions.size();i++) {
                        //    if (emotions.containsKey(group_msg)) {
                          //      cmsg.emotion = emotions.get(group_msg);
                           // }
                        //}

                        // 프사(작은버전) 주기... -> 원본으로 수정
                        for(int i=0;i<10;i++) {
                            if(id_list.get(i).equals(UserName)) {
                                cmsg.img_s = img_list.get(i);
                            }
                        }
                        WriteGroup(cmsg);
                    }

                    else if(cm.getCode().matches("600")) {

                        //체크리스트 받음
                        if(cm.getData().matches("chatopen")) { //완료 버튼을 누르는게 user2. user2->server로 chapopen프로토콜 요청. user1은 아무것도 안하고있음
                            System.out.println("그룹내 인원수 = " + cm.userlist.size());

                            //그룹이름 통일시키기
                            String group_name = cm.groupName;
                            String[] arr = group_name.split(" ");
                            Arrays.sort(arr);
                            String group_name_new = "";
                            for (int i = 0; i < arr.length; i++) {
                                group_name_new += arr[i] + " ";
                            }
                            group_name_new = group_name_new.trim();
                            System.out.println("그룹새로만들었습니다 이름은 " + group_name_new);

                            int check_group = 0;
                            for (int i = 0; i < groupnamelist.size(); i++) {
                                if (groupnamelist.get(i).equals(group_name_new))
                                    check_group = 1;
                            }
                            //System.out.println("그룹이 이미 존재하는가?(1중복, 0새거) : " + check_group);
                            if (check_group == 0) {
                                groupnamelist.add(group_name_new);
                                Vector<String> userli = new Vector<>();
                                userli = cm.userlist;

                                System.out.println("그룹 첨 만들때 얘가 가진 유저리스트 : " + userli);
                                group_userlist.add(userli);
                                //Vector<String> chatli = new Vector<String>();
                                //chatli.add(group_name_new);
                                for (int i = 0; i < cm.userlist.size(); i++) {//3,5,6
                                    String s = cm.userlist.get(i);
                                    String sub = s.substring(s.length() - 1);
                                    int a = Integer.parseInt(sub);
                                    if (a == 0)
                                        a = 10;
                                    user_grouplist[a - 1].add(group_name_new);
                                    System.out.println("user_grouplist["+ (a-1) + "]:"+user_grouplist[a-1]);
                                }
                            }
                            //이제 여기서 채팅창 생성, 유저들 초대, 유저들한테 보내기
                            //나는 600으로 client한테 groupname 주기
                            ChatMsg cmsg = new ChatMsg("SERVER", "600", group_name_new);
                            cmsg.userlist = cm.userlist;

                            //System.out.println("채팅방이름 설정완료");

                            synchronized (this) {
                                try {
                                    oos.writeObject(cmsg);
                                 } catch (IOException e) {
                                   // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                          }
                        }
                    }
                    //700이 오면 나는 계속 700으로 보냄...
                    else if(cm.getCode().matches("700")) {
                        System.out.println("700왔음!");
                        //서버에서 전체 채팅 기록 요청.
                        //그룹이름과 같이 줄 예정!!
                        String group_name = cm.getData();
                        String user_name = cm.getId();

                        String file_name = group_name + ".txt";
                        try{
                            //파일 객체 생성
                            File file = new File("src/"+file_name);
                            //입력 스트림 생성
                            FileReader filereader = new FileReader(file);
                            //입력 버퍼 생성
                            BufferedReader bufReader = new BufferedReader(filereader);
                            String line = "";
                            int count = 0;


                            String s_name="";
                            String s_msg = "";
                            String s_time="";
                            ImageIcon img=null;
                            ImageIcon s_icon = null;
                            while((line = bufReader.readLine()) != null){
                                count++;
                                if(count%3 == 1) {
                                    //라인끝에 공백 없애주기.
                                    line = line.replace("\n", "");
                                    s_name=line;//보내는사람
                                    //System.out.println(s_name);
                                    if(s_name == "\0")
                                        break;
                                }

                                else if(count%3 == 2) {
                                    line = line.replace("\n", "");
                                    s_msg=line;//메세지
                                   // System.out.println(s_msg);

                                    for(int i=0;i<chat_images.size();i++) {
                                        if (chat_images.containsKey(s_msg)) {
                                            img = chat_images.get(s_msg);
                                        }
                                    }
                                    //여기ㅏ서 해시맵 탑색하는데, 경로명 일치하는 거 있으면,
                                    //메세지 말고 이미지아이콘으로 보내기 ~~~~~
                                    //메세지에는 이미지라고 써놓고 클라이언트에서는 if msg=="이미지"이면
                                    //사진 출력하는 것으로 ....................................

                                }

                                else if(count%3 == 0) {
                                    line = line.replace("\n", "");
                                    s_time = line ;// 시간
                                  //  System.out.println(s_time);
                                    ImageIcon ims = null;
                                    for(int i=0;i<10;i++) {
                                        if(id_list.get(i).equals(s_name)) {
                                            ims = img_list.get(i);
                                        }
                                    }
                                    ChatMsg cmsg = new ChatMsg("SERVER","700",s_msg);
                                    cmsg.userName = s_name;
                                    cmsg.time = s_time;
                                    cmsg.msg_img = img;
                                    cmsg.groupName = group_name;
                                    cmsg.img_s=ims; // 작은프사
                                  //  System.out.println("보내기 직전");

                                    synchronized (this) {
                                        try {
                                            oos.writeObject(cmsg);
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            }
                            //.readLine()은 끝에 개행문자를 읽지 않는다.
                            bufReader.close();
                        }catch (FileNotFoundException e) {
                            // TODO: handle exception
                        }catch(IOException e){
                            System.out.println(e);
                        }
                    }
                    //새로운 대화상대 추가

                    /*
                    else if(cm.getCode().matches("1000")) {

                        //새로운 대화 상대 추가할때,,, 유저 리스트로 주자...
                        Vector<String> userlist = cm.userlist;

                        //그룹이름 통일시키기
                        String group_name = cm.groupName;
                        String[] arr = group_name.split(" ");
                        for(int i=0;i<arr.length;i++)
                            userlist.add(arr[i]);
                        Collections.sort(userlist);c
                        String group_name_new = "";
                        for (int i = 0; i < userlist.length(); i++) {
                            group_name_new += userlist.get(i) + " ";
                        }
                        group_name_new = group_name_new.trim();


                    }
*/

                    else if (cm.getCode().matches("800")) { // logout message 처리
                        try {
//						dos.close();
//						dis.close();
                            ois.close();
                            oos.close();
                            client_socket.close();
                            Logout(); // 에러가난 현재 객체를 벡터에서 지운다
                            break;
                        } catch (Exception ee) {
                            break;
                        } // catch문 끝

                    } else if (cm.getCode().matches("1300")) {
                        WriteAllObject(cm);
                    }
                } catch (IOException e) {
                    AppendText("ois.readObject() error");
                    try {
//						dos.close();
//						dis.close();
                        ois.close();
                        oos.close();
                        client_socket.close();
                        Logout(); // 에러가난 현재 객체를 벡터에서 지운다
                        break;
                    } catch (Exception ee) {
                        break;
                    } // catch문 끝
                } // 바깥 catch문끝
            } // while
        } // run

    }



}
