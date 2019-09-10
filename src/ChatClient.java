//클라이언트 GUI
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.ws.handler.Handler;

import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;


public class ChatClient extends Frame implements ActionListener{
	String nickname="";
	String serverIP="127.0.0.1";
	int serverPort=5001;
	DataInputStream in; //소켓으로부터 최종 입력 스트림
	DataOutputStream out; //소켓으로부터 최종 출력 스트림
	
	Panel p = new Panel(new BorderLayout());
	TextArea ta = new TextArea();
	TextField tf = new TextField();
	ChatClient(String nickname)
	{
		this.nickname = nickname;
		p.add(ta,"Center");
		p.add(tf,"South");
		
		ta.setEditable(false);
		tf.addActionListener(this);
		this.add(p);
		this.addWindowListener(new MyWindowHandler());
		this.setBounds(600,200,300,300);
		this.setVisible(true);		
		tf.requestFocus();
		
	}
	public static void main(String[] args)
	{
	try {
		ChatClient client = new ChatClient("Client");
		client.startClient();
	}catch (UnknownHostException e)
	{
		e.printStackTrace();		
	} catch(IOException e)
	{
		e.printStackTrace();		
	 }
	}
	//생성자
public void startClient() throws UnknownHostException, IOException
{
	Socket socket = new Socket(serverIP, serverPort);
	ta.setText("[연결 완료 : " + serverIP + "/" + serverPort + "]"); //서버에 연결 되었습니다.
	// 소켓으로부터 최종 입출력 스트림 얻기
	in = new DataInputStream(socket.getInputStream());
	out = new DataOutputStream(socket.getOutputStream());
	while(in != null)
	{ 
		String msg= in.readUTF();
		System.out.println("[받기 완료] " + msg);
		ta.append("\n" + msg);		
	}
}
@Override
public void actionPerformed(ActionEvent e)
{
	// TODO Auto- generated method stub
	String msg = tf.getText();
	if("".equals(msg))return;
	if(out!=null)
	{
		try {
			out.writeUTF(nickname + ">" + msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		}
	ta.append("\n" + nickname + ">" + msg);
	tf.setText("");
	}
			
}






