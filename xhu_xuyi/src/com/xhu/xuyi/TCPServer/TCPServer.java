package com.xhu.xuyi.TCPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * �ѷ����д��һ���࣬��Web�����������֮������ServerSocket
 * @author xuyi
 *
 */
public class TCPServer {
	
	ServerSocket serverSocket;
	
	String tempStr = "�Ѿ�����������....";
	
	public static Map<String, Socket> socketMap = new HashMap<String, Socket>();
	
	public void TCPServerStart(){
		try{
			serverSocket = new ServerSocket(10005,0,Inet4Address.getLocalHost());
			System.out.println("���������������ַΪ:"+serverSocket.getLocalSocketAddress());
			
			//����Server��ʱ��Ϊ���õ�
			serverSocket.setSoTimeout(0);
			
			 while (true) {
			   final Socket socket = serverSocket.accept();
			   
			   int port = socket.getPort();
			   String stringPort = String.valueOf(port);
			   socketMap.put(socket.getInetAddress().getHostAddress()+"&"+stringPort, socket);
			   System.out.println("��ַΪ��"+socket.getInetAddress().getHostAddress()+" �Ŀͻ��������˷����...");
			   
			   //�������ڲ�ͨ��Socket���͵�Server�˵����� �� �ٷ��͵�ָ����Զ�̿ͻ���
			   if(socket.getInetAddress().equals(Inet4Address.getLocalHost()) && 
					   socket.getPort() == 9080){
				   
				   System.out.println("���Ƿ������ڲ���������Socket����...");
				   new Thread(){
					public void run(){
						   try {
							   InputStream in = socket.getInputStream();
//							   ObjectInputStream ois = new ObjectInputStream(in);
							//ͨ���������������� 
//							List<Object> objList;
							String tcpStr = null;
//							String userIp = null;
//							int userPort;
							String ipAndPort = null;
							
							BufferedReader socketBr = new BufferedReader(new InputStreamReader(in));
							
							char[] buf = new char[100];
							int len = 0;
							
							//Ҫ���ֲ��ϵĶ�ȡ ����ObjectIputStream��readObject()��������
							while((len = socketBr.read(buf)) != -1){
//								try {
//									objList = (List<Object>)ois.readObject();
//									tcpStr = (String) objList.get(0);
//									userIp = (String) objList.get(1);
//									userPort = (int) objList.get(2);
//									//ƴ���û���IP��Port
//									ipAndPort = userIp+"&"+String.valueOf(userPort);
//									
//									System.out.println("����ΪServer���յ����ڲ�Servlet����������...");
//									System.out.println(tcpStr);
//									System.out.println(ipAndPort);
									
//								} catch (ClassNotFoundException e) {
//									e.printStackTrace();
//								}
								
								StringBuilder sb = new StringBuilder();
								for (int i = 0; i < len; i++) {
									sb.append(buf[i]);
								}
								String str = sb.toString();
								String[] arr = str.split("%");
								tcpStr = arr[0];
								ipAndPort = arr[1];
								System.out.println("ServerSocket���յ����ǣ�"+tcpStr+"��"+ipAndPort);
								//���ұ�����Map�����Socket��IP��Port�Ƿ�ͬʱ�����д�����Ķ������ڵ�IP��Port���
								Socket toSocket = socketMap
										.get(ipAndPort);
								
								OutputStream outputStream = toSocket.getOutputStream();
								outputStream.write(tcpStr.getBytes());
								System.out.println("����˶�ȡ�Ĵӷ������ڲ�8081�˿ڷ��͵�����,�����Ѿ����͵���ָ���Ŀͻ���...");
								
							}
							
						   } catch (IOException e) {
							e.printStackTrace();
						}
						   
					   };
				   }.start();
				   
			   }else{
				   
				   // ѭ����ÿ���յ�һ���ͻ�������, ����һ�����߳�
				   new Thread(){
					   public void run() {
						   try {
							   OutputStream out = socket.getOutputStream();
							   out.write(tempStr.getBytes());
							   
							   InputStream in = socket.getInputStream();
							   
							   BufferedReader socketBr = new BufferedReader(new InputStreamReader(in));
							   char[] buf = new char[1024];
							   int len = 0;
							   
							   while((len = socketBr.read(buf)) != -1){
								   StringBuilder sb = new StringBuilder();
								   for (int i = 0; i < len; i++) {
									   sb.append(buf[i]);
								   }
								   System.out.println(socket.getInetAddress().getHostAddress()
										   +socket.getPort()+":"+sb.toString());
							   }
							   socket.close();
						   } catch (Exception e) {
							   e.printStackTrace();
						   }
					   };
				   }.start();
			   }
			   
			  }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void TCPServerClose(){
		if(serverSocket != null){
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
