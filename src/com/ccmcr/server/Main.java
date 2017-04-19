package com.ccmcr.server;

import java.util.Random;

public class Main {
	public static String salt = "" + (new Random()).nextLong();
	public static int users = 0;
	public static int max = 0;
	public static int port = 25565;
	public static String name = "Test";
	public static boolean public_ = false;
	public int ticks = 0;
	public HeartbeatLoop hl;
	public static String url;
	
	public static void main(String args[])
	{
		HeartSaltSend hl = new HeartSaltSend();
		salt = hl.generate(salt);
		Main m = new Main();
		m.Instantiate();
		url = HeartSaltSend.Beat(Main.salt, Main.port, Main.public_, Main.users, Main.max, Main.name);
		System.out.println("Success! Your heartbeat URL is:");
		System.out.println(url);
	}
	
	public void Instantiate()
	{
		ServerThread st = new ServerThread(this);
		hl = new HeartbeatLoop(st);
		Thread tThread = new Thread(st);
		tThread.start();
	}
	public void Tick()
	{
		ticks++;
		//System.out.println(ticks);
		if(ticks >= 2800)
		{
			ticks = 0;
			url = HeartSaltSend.Beat(Main.salt, Main.port, Main.public_, Main.users, Main.max, Main.name);
			System.out.println(url);
		}
	}
}
