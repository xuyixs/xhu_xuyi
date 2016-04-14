package com.xhu.xuyi.TCPServer;

import java.util.Timer;
import java.util.TimerTask;

public class TCPStartTimer extends Timer {
	
	public void schedule(TimerTask task, long delay) {
		super.schedule(task, delay);
	}
}
