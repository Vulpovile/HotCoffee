package com.androdome.hotcoffee.server;

public class ServerThread{
public HotCoffeeServer srvmain;
public boolean running = true;
	
	ServerThread(HotCoffeeServer smain)
	{
		srvmain = smain;
	}
	
	public void run()
	{
		while(running)
		{
			srvmain.tick();
			try {
				Thread.sleep(16L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}
