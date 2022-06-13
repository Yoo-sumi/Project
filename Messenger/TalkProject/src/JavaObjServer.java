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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JavaObjServer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의

	
	//기본프로필사진
	private ImageIcon basic_profile = new ImageIcon("src/basic.jpeg");
	//기본프로필사진 사진조절된 버전
	private ImageIcon basic_profile_s = new ImageIcon("src/basic.png");
	
	//로그인 성공한 유저들 객체 리스트
	private Vector<UserService> real_user_vc = new Vector<UserService>();
	//아이디 리스트
	private Vector<String> id_list=new Vector<String>();
	//이미지 리스트
	private Vector<ImageIcon> img_list = new Vector<ImageIcon>();
	//이미지 리스트(작은버전)
	private Vector<ImageIcon> img_list_s = new Vector<ImageIcon>();
	
	//현재 로그인 된 아이디 리스트
	private Vector<String> login_list = new Vector<String>();
			
	//그룹에 속한 유저들 Vector
	public Vector<String> userlist_in= new Vector<String>();
		
	//그룹에 속한 유저들 Vector들을 가지고있는 Vector
	public Vector<Vector> groupchatlist = new Vector<Vector>();//이안에 userlist_in 여러개...
	
	//그룹 이름 리스트 Vector
	public Vector<String> groupnamelist= new Vector<String>();//그룹톡방이름리스트
	
	//이모티콘 (기쁨), (슬픔) 이런거들 모아둔거
	public Vector<String> emotions = new Vector<String>();
	//이모티콘 이미지들 모아둔것
	public Vector<ImageIcon> emotions_img = new Vector<ImageIcon>();
	
	///
	public Vector[] gcl = new Vector[10];
	public Vector<String> chatlist = new Vector<String>();
	
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

		
		//아이디 생성
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
		
		//각 유저마다 프로필 사진 설정.
		for(int i=0;i<10;i++) {
			img_list.add(new ImageIcon("src/basic.jpeg"));
			img_list_s.add(new ImageIcon("src/basic.png"));
			
			gcl[i]=new Vector();
		
		}
			
		
		
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
		private Vector user_vc; //그냥 접속 시도한 모든 유저들
		
		public String UserName = "";
		public String UserStatus;
		
		//프로필사진 원본
		private ImageIcon profile_img = basic_profile;//처음부터 이거하면안됨.. 본인꺼 찾아오ㅏㅇ함.
		//프로필사진 크기 조정 버전
		private ImageIcon profile_img_s = basic_profile_s;
		

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

		public void Login() {
			boolean loginCheck = true;
			boolean idCheck = false;
			//아이디 리스트에 있는지 체크
			for(int i=0;i<id_list.size();i++) {
				//이미 로그인 되어있는지 체크, 아이디 리스트에 있는지 체크.
				if(id_list.elementAt(i).equals(UserName))
					idCheck=true;
			}
			//이미 로그인 한건지 체크
			for(int i=0;i<login_list.size();i++) {
				if(login_list.elementAt(i).equals(UserName))
					loginCheck=false;
			}
			
			if(idCheck && loginCheck) {
				//UserService user = (UserService) user_vc.elementAt(i)
				real_user_vc.add(this);
				login_list.add(UserName);
				
				String u = UserName;
				String sub = u.substring(u.length()-1);
				int a = Integer.parseInt(sub)-1;
				if (a==-1)
					a=9;
				
				//본인프사 찾아오기
				profile_img = img_list.get(a);
				profile_img_s = img_list_s.get(a);
				
				
				AppendText("현재 참가자 수 " + real_user_vc.size());
				AppendText("새로운 참가자 " + UserName + " 입장.");
				
				String msg = "success";
				//client에게 로그인 성공 메세지 보내기
				ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
				
				//본인 프사 주기......
				//img_list에는 내프사 포함 전체 유저 프사들 담겨있음
				obcm.img=profile_img;//본인 프사
				obcm.img_s=profile_img_s;//본인프사 크기 줄인거
				
				//유저리스트 보내주기
				//본인제외 리스트
				Vector<String> sub_id_list=new Vector<String>();
				for(int i=0;i<10;i++) {
					if(UserName.equals(id_list.get(i))) {
						continue;
					}
					sub_id_list.add(id_list.get(i));
				}
				obcm.userlist=sub_id_list;
				obcm.img_list=img_list;
				obcm.img_list_s=img_list_s;
				
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

		public void Logout() {
			for(int i=0;i<login_list.size();i++) {
				if(login_list.get(i).equals(UserName)) {
					login_list.remove(i);
					break;
				}
			}
			
			String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
			UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
			real_user_vc.removeElement(this); //찐 유저중에서도 지움..
			WriteAll(msg); // 나를 제외한 다른 User들에게 전송
			AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + real_user_vc.size());
		}

		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteAll(String str) {
			for (int i = 0; i < real_user_vc.size(); i++) {
				UserService user = (UserService) real_user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}
		// 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < real_user_vc.size(); i++) {
				UserService user = (UserService) real_user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}

		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthers(String str) {
			for (int i = 0; i < real_user_vc.size(); i++) {
				UserService user = (UserService) real_user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOne(str);
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
		public void WriteOne(String msg) {
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
		public void WritePrivate(String msg) {
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
		
		public void WriteGroup(Object cm) {
			ChatMsg c = (ChatMsg)cm;
			int idx=0;
			for(int k=0;k<groupnamelist.size();k++) {
				if(groupnamelist.get(k).equals(c.groupName)) {
					idx = k;
					System.out.println("idx : " + idx);
				}
			}
			Vector<String> userli = (Vector<String>)groupchatlist.get(idx); // 유저리스트
			//System.out.println("톡방에 있는 사람 리스트 : " + aa);
			//채팅방이름준거랑 대조해서 인덱스뽑아내고 그 인덱스로 체크리스트의
			//인덱스로 뽑아서 get으로 하며 ㄴ벡터로 나오고 그거의 사이즈러
			for (int i = 0; i < userli.size(); i++) {
				String u = userli.get(i);//그 톡방에있는 사람들.
				for(int j=0;j<real_user_vc.size();j++) {
					UserService user = (UserService) real_user_vc.elementAt(j);
					if(u.equals(user.UserName)) {
						System.out.println("톡보내줄예정인사람: "+u);
						String sub = u.substring(u.length()-1);
						int a = Integer.parseInt(sub);
						if (a==0)
							a=10;
						//gcl[a-1]에는 a-1번째 유저가 속해있는 그룹 vector객체가 있음
						c.chatlist=gcl[a-1];
						for(int n=0;n<gcl[a-1].size();n++) {
							c.chatlist2[n]=(String) gcl[a-1].get(n);
						}
						c.size = gcl[a-1].size();
						
						
						//chatlist보내면서 마지막 대화내용도 보내야함.
						
						//cRec에는 각 chatlist에 있는 그룹명마다의 마지막 메세지들이 보관되어있음.
						
						Vector<String> cRec = new Vector<String>();
						
						for(int k=0;k<gcl[a-1].size();k++) {
							String g_name = (String) gcl[a-1].get(k);
							String last_msg="";
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
								 file.read(buff);
								 last_msg = new String(buff).replace("\0","");
								 //last_msg = file.readLine();
								 
								 cRec.add(last_msg);
								 System.out.println(u+"/"+last_msg);
						        }catch (FileNotFoundException e) {
						            // TODO: handle exception
						        }catch(IOException e){
						            System.out.println(e);
						        }
						}
						c.chatRecord = cRec;	
						System.out.println("톡방에 있는 사람 리스트 : " + c.chatlist);
						System.out.println("보내기전 그룹톡방이름 체크 : " +c.groupName);
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
		public void WriteOneObject(Object ob) {
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
					System.out.println(cm.getCode());
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
							prof.img_list2[i]= img_list.get(i);
						}
						
						try {
							oos.writeObject(prof);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(cm.getCode().matches("500")) {
						//처음 메세지 받았을때 다른 유저들도 카톡 받고 각자 채팅 리스트에 그 그룹 톡방이 보이도록 만들기.

						String group_name = cm.getId(); //그룹 이름 받기
						String group_msg = cm.getData(); //메세지 받기
						String user_name = cm.userName; //채팅 보낸 사람 이름
						
						//현재 시간
						Calendar cal = Calendar.getInstance();
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						int min = cal.get(Calendar.MINUTE);
						String time = Integer.toString(hour) + ":" +Integer.toString(min);
						
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
						
						ChatMsg cmsg;
						System.out.println("500번, 현재 그룹톡방 이름 프린트 : "+ group_name);
						cmsg = new ChatMsg(group_name, "500", group_msg); 
						cmsg.groupName = group_name;
						cmsg.userName= user_name;
						//만약 group_msg가 (기쁨), (슬픔) 등 이모티콘 일 경우, 
						 //이미지 파일로 주기 ~
						if(group_msg.equals("(기쁨)")) {
							//cmsg.setCode("501");
							ImageIcon icon = new ImageIcon("src/sad.gif") ;
							cmsg.img = icon;
						}
						else
							cmsg.img = null;
						ImageIcon ims = null;
						//얘는 왜있지..?
						for(int i=0;i<10;i++) {
							if(id_list.get(i).equals(UserName)) {
								ims = img_list_s.get(i);
							}
						}
						cmsg.img_s=ims;
						WriteGroup(cmsg);						
					}
					else if(cm.getCode().matches("600")) {
						
						//체크리스트 받음
						if(cm.getData().matches("chatopen")) { //완료 버튼을 누르는게 user2. user2->server로 chapopen프로토콜 요청. user1은 아무것도 안하고있음
							System.out.println("그룹내 인원수 ="+cm.userlist.size());	
							
							//그룹이름 통일시키기
							String group_name = cm.groupName;
							String[] arr = group_name.split(" ");
							Arrays.sort(arr);
							String group_name_new = "";
							for(int i=0;i<arr.length;i++) {
								group_name_new += arr[i]+" ";
							}
							group_name_new = group_name_new.trim();
							System.out.println("그룹새로만들었습니다 이름은 " + group_name_new);
							
							int check_group = 0;
							for(int i=0;i<groupnamelist.size();i++) {
								if(groupnamelist.get(i).equals(group_name_new))
									check_group=1;
							}
							System.out.println("그룹이 이미 존재하는가?(1중복, 0새거) : "+check_group);
							if(check_group==0) {
								groupnamelist.add(group_name_new);
								Vector<String> userli = null;
								userli = cm.userlist;
								//for(int i=0;i<cm.userlist.size();i++) {
								//	userli.add(cm.userlist.get(i));
								//}
								System.out.println("그룹 첨 만들때 얘가 가진 유저리스트 : " + userli);
								groupchatlist.add(userli);
								//Vector<String> chatli = new Vector<String>();
								//chatli.add(group_name_new);
								for(int i=0;i<cm.userlist.size();i++) {//3,5,6
									String s =cm.userlist.get(i);
									String sub = s.substring(s.length()-1);
									int a = Integer.parseInt(sub);
									if (a==0)
										a=10;
									gcl[a-1].add(group_name_new);
								}
							}
							//이제 여기서 채팅창 생성, 유저들 초대, 유저들한테 보내기
							//나는 600으로 client한테 groupname 주기
							ChatMsg cmsg = new ChatMsg("SERVER", "600", group_name_new);
							cmsg.userlist=cm.userlist;

							System.out.println("채팅방이름 설정완료");
							try {
								oos.writeObject(cmsg);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
				            ImageIcon s_icon = null;
				            while((line = bufReader.readLine()) != null){
				            	count++;
				            	if(count%3 == 1) {
				            		//라인끝에 공백 없애주기.
				            		line = line.replace("\n", "");
				            		s_name=line;//보내는사람
				            		System.out.println(s_name);
				            		if(s_name == "\0")
				            			break;
				            	}
				            		
				            	else if(count%3 == 2) {
				            		line = line.replace("\n", "");
				            		s_msg=line;//메세지
				            		System.out.println(s_msg);
				            	}
				            		
				            	else if(count%3 == 0) {
				            		line = line.replace("\n", "");
				            		s_time = line ;// 시간
				            		System.out.println(s_time);
				            		ImageIcon ims = null;
									for(int i=0;i<10;i++) {
										if(id_list.get(i).equals(s_name)) {
											ims = img_list_s.get(i);
										}
									}
									ChatMsg cmsg = new ChatMsg(s_time,"700",s_msg);									
									cmsg.userName = s_name;
									cmsg.img_s=ims;
									System.out.println("보내기 직전");
									try {
										oos.writeObject(cmsg);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
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
					//c->s (파일 read요청) : 그룹 명, 요청자 user 이름
					else if(cm.getCode().matches("1000")) {
						
					}
					else if (cm.getCode().matches("1400")) { // logout message 처리
						Logout();
						break;
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
