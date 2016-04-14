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
			//�����׽������ӵ����ص�10005�˿ڣ����󶨴��׽��ֵĶ˿�Ϊ8081
			socket = new Socket(address, 10005, address, 9080);
			System.out.println("�������ڲ���Socket�Ѿ������ã��˿�Ϊ9080���Ѿ�׼���÷���������...");
			
			//����SocketΪ���õ�
			socket.setSoTimeout(0);
			
			//��ѭ����8081�˿�һֱ��������
			while(true){
				//���ö��е���������ȡPut()�����е�����
				List<Object> toList = linkedBlockingQueue.take();
				
				System.out.println("��ȡ����Queue�����ݣ�׼�����͵�ServerSocket...");
				
				OutputStream out = socket.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
				
				//ƴ��toList����Ķ���
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
