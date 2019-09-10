//ServerGui
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.awt.event.WindowAdapter;

public class Server extends Frame implements ActionListener{ //메인함수
	
	String nickname = "";
	Panel p = new Panel();
	TextArea ta = new TextArea();
	TextField tf = new TextField();
	DataInputStream in;
	DataOutputStream out;
	Server(String nickname)
	{
		super(nickname);
		this.nickname= nickname;
		p.setLayout(new BorderLayout());
		p.add(ta,"Center");
		p.add(tf,"South");
		tf.addActionListener(this); //액션 이벤트를 받기 위한 인터페이스
		tf.addFocusListener(new FocusAdapter()
		{
		public void focusGained(FocusEvent fe)
		{
			tf.requestFocus();
		}
		});
		ta.setEditable(false);  //메소드 불러옴. 필드에 입력 할 수 없음.
		
		this.add(p);
		this.setBounds(200,200,300,300);
		this.setResizable(false);
		this.setVisible(true);
		tf.requestFocus();
		this.addWindowListener(new MyWindowHandler());
		serverStart();
	}
	public void actionPerformed(ActionEvent e)
	{
	 String msg= tf.getText();
	 if("".equals(msg))return;
	 if(out!=null)
	{
	try {
		out.writeUTF(nickname+">"+msg);
	}catch(IOException e1)
	{
		e1.printStackTrace();
 	  }
	}
	ta.append("\n"+nickname + " > " + msg);
	tf.setText("");
	}
	 public void serverStart()
	 {
		 try {
			 //서버소켓 객체 선언
		ServerSocket S_socket = new ServerSocket(5001);
		ta.setText("서버가 준비되었습니다.\n");
		//채팅 객체 생성
		Socket socket = S_socket.accept();
		ta.append("상대방과 연결되었습니다.\n");
		in= new DataInputStream(socket.getInputStream());
		out= new DataOutputStream(socket.getOutputStream());
		while(in!=null)
		{
			String msg = in.readUTF();
			ta.append("/n"+msg);			
		}		
	}catch (IOException e)
	{
		ta.setText("서버소켓 설정 실패\n");			
	 }
	
	}
	public static void main(String[] args)
	{
		Server server = new Server("Server");
	}

}

class MyWindowHandler extends WindowAdapter {

}
