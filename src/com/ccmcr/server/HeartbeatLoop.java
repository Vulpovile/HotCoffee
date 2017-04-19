package com.ccmcr.server;

public class HeartbeatLoop implements Runnable{
	public ServerThread sThread;
	
	public HeartbeatLoop(ServerThread st) {
		sThread = st;
	}

	public void run() {
		while (sThread.running){
		System.out.println("Beat");
		HeartSaltSend.Beat(Main.salt, Main.port, Main.public_, Main.users, Main.max, Main.name);
		try {
			Thread.sleep(45000L);
		} catch (InterruptedException e) {
		}
		}
	}

}
