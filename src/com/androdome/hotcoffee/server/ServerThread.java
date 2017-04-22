package com.androdome.hotcoffee.server;

public class ServerThread{
public HotCoffeeServer srvmain;
	
	ServerThread(HotCoffeeServer smain)
	{
		srvmain = smain;
	}
	
	public void run()
	{
		while(srvmain.running)
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
