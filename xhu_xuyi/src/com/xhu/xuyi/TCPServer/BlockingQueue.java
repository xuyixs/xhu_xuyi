package com.xhu.xuyi.TCPServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueue {
	
	Socket socket = null;
	public static LinkedBlockingQueue<List<Object>> linkedBlockingQueue = new LinkedBlockingQueue<List<Object>>();
	
	public void sendToServerSocket(){
		
		try {
			Inet4Address address=(Inet4Address) Inet4Address.getLocalHost();
			//创建套接字连接到本地的10005端口，并绑定此套接字的端口为8081
			socket = new Socket(address, 10005, address, 9080);
			System.out.println("服务器内部的Socket已经创建好，端口为9080，已经准备好发送数据了...");
			
			//设置Socket为永久的
			socket.setSoTimeout(0);
			
			//死循环让8081端口一直接收数据
			while(true){
				//采用队列的阻塞来获取Put()到队列的数据
				List<Object> toList = linkedBlockingQueue.take();
				
				System.out.println("获取到了Queue的数据，准备发送到ServerSocket...");
				
				OutputStream out = socket.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
				
				//拼接toList里面的对象
				StringBuilder sb = new StringBuilder();
				sb.append(toList.get(0)).append("%").append(toList.get(1)).append("&").append(toList.get(2));
				
				bw.write(sb.toString());
				bw.flush();
				
//				ObjectOutputStream oos = 
//						new ObjectOutputStream(socket.getOutputStream());
				
//				oos.writeObject(toList);
//				oos.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendToServerSocketClose(){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
