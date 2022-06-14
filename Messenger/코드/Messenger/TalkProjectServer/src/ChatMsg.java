
// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.File;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
    private String data;

    public String time;
    public Vector<String> timeVector = new Vector<>();
    public ImageIcon img;
    public ImageIcon msg_img;
    public ImageIcon img_s;//
    public ImageIcon emotion; //이거 추가!!!!!!!!!!
    public File file;

    public Vector<String> userlist=new Vector<String>();
    public Vector<ImageIcon> img_list = new Vector<ImageIcon>();
    public ImageIcon[] img_list2 = new ImageIcon[10];
    public Vector<ImageIcon> img_list_s = new Vector<ImageIcon>();
    public Vector<String> chatRecord=new Vector<String>();

    public Vector<Vector> check_userlist = new Vector<Vector>();
    public Vector<String> userlist_in= new Vector<String>();

    public Vector<String> chatlist;
    public String[] chatlist2 = new String[10];
    public int size;
    public String groupName;


    //public GroupChat groupChat;

    public String userName;

    public ChatMsg(String id, String code, String msg) {
        //check_userlist.add(userlist_in);
        this.id = id;
        this.code = code;
        this.data = msg;
    }
    public void setChatRecord(Vector<String> chatRecord) {
        this.chatRecord=chatRecord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
}