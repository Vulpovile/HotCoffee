package com.androdome.hotcoffee.server;

public class ServerThread implements Runnable {
public Main srvmain;
public boolean running = true;
	
	ServerThread(Main smain)
	{
		srvmain = smain;
	}
	
	public void run()
	{
		while(running)
		{
			srvmain.Tick();
			try {
				Thread.sleep(16L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}
