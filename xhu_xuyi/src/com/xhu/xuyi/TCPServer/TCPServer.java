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
 * 把服务端写成一个类，在Web容器完成启动之后启动ServerSocket
 * @author xuyi
 *
 */
public class TCPServer {
	
	ServerSocket serverSocket;
	
	String tempStr = "已经建立了连接....";
	
	public static Map<String, Socket> socketMap = new HashMap<String, Socket>();
	
	public void TCPServerStart(){
		try{
			serverSocket = new ServerSocket(10005,0,Inet4Address.getLocalHost());
			System.out.println("服务端已启动，地址为:"+serverSocket.getLocalSocketAddress());
			
			//设置Server的时间为永久的
			serverSocket.setSoTimeout(0);
			
			 while (true) {
			   final Socket socket = serverSocket.accept();
			   
			   int port = socket.getPort();
			   String stringPort = String.valueOf(port);
			   socketMap.put(socket.getInetAddress().getHostAddress()+"&"+stringPort, socket);
			   System.out.println("地址为："+socket.getInetAddress().getHostAddress()+" 的客户端连接了服务端...");
			   
			   //服务器内部通过Socket发送到Server端的数据 ， 再发送到指定的远程客户端
			   if(socket.getInetAddress().equals(Inet4Address.getLocalHost()) && 
					   socket.getPort() == 9080){
				   
				   System.out.println("这是服务器内部发过来的Socket数据...");
				   new Thread(){
					public void run(){
						   try {
							   InputStream in = socket.getInputStream();
//							   ObjectInputStream ois = new ObjectInputStream(in);
							//通过对象流来传数据 
//							List<Object> objList;
							String tcpStr = null;
//							String userIp = null;
//							int userPort;
							String ipAndPort = null;
							
							BufferedReader socketBr = new BufferedReader(new InputStreamReader(in));
							
							char[] buf = new char[100];
							int len = 0;
							
							//要保持不断的读取 ，而ObjectIputStream的readObject()不能做到
							while((len = socketBr.read(buf)) != -1){
//								try {
//									objList = (List<Object>)ois.readObject();
//									tcpStr = (String) objList.get(0);
//									userIp = (String) objList.get(1);
//									userPort = (int) objList.get(2);
//									//拼接用户的IP和Port
//									ipAndPort = userIp+"&"+String.valueOf(userPort);
//									
//									System.out.println("以下为Server接收到的内部Servlet过来的数据...");
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
								System.out.println("ServerSocket接收到的是："+tcpStr+"、"+ipAndPort);
								//查找保存在Map里面的Socket的IP和Port是否同时满足和写过来的对象流内的IP和Port相等
								Socket toSocket = socketMap
										.get(ipAndPort);
								
								OutputStream outputStream = toSocket.getOutputStream();
								outputStream.write(tcpStr.getBytes());
								System.out.println("服务端读取的从服务器内部8081端口发送的数据,并且已经发送到了指定的客户端...");
								
							}
							
						   } catch (IOException e) {
							e.printStackTrace();
						}
						   
					   };
				   }.start();
				   
			   }else{
				   
				   // 循环中每接收到一个客户端请求, 开启一条新线程
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
