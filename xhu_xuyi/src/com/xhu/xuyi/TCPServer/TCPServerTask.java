package com.xhu.xuyi.TCPServer;

import java.util.TimerTask;

public class TCPServerTask extends TimerTask {

	public void run() {
		TCPServer tcpServer = new TCPServer();
		tcpServer.TCPServerStart();
	}

}
