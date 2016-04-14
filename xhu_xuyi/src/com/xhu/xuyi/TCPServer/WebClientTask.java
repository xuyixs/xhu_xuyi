package com.xhu.xuyi.TCPServer;

import java.util.TimerTask;

public class WebClientTask extends TimerTask {

	@Override
	public void run() {
		BlockingQueue blockingQueue = new BlockingQueue();
		
		blockingQueue.sendToServerSocket();
	}

}
