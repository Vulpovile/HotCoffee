package com.androdome.hotcoffee.server;

import java.io.IOException;
import java.util.Random;

import com.androdome.hotcoffee.net.BindTo;

public class Main {
	public static String salt = "" + (new Random()).nextLong();
	public static int users = 0;
	public static int max = 0;
	public static int port = 25565;
	public static String name = "Test";
	public static boolean public_ = true;
	public int ticks = 0;
	public static String url;
	public boolean running = true;
	BindTo bindTo;
	
	public static void main(String args[])
	{
		HeartSaltSend hl = new HeartSaltSend();
		salt = hl.generate(salt);
		Main m = new Main();
		m.instantiate();
		url = HeartSaltSend.Beat(Main.salt, Main.port, Main.public_, Main.users, Main.max, Main.name);
		System.out.println("Success! Your heartbeat URL is:");
		System.out.println(url);
	}
	
	public void instantiate()
	{
		try {
			this.bindTo = new BindTo(Main.port, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerThread st = new ServerThread(this);
		Thread bind = new Thread(this.bindTo);
		bind.start();
		Thread tThread = new Thread(st);
		tThread.start();
	}
	
	public void tick()
	{
		ticks++;
		if(ticks >= 2800)
		{
			ticks = 0;
			url = HeartSaltSend.Beat(Main.salt, Main.port, Main.public_, Main.users, Main.max, Main.name);
			System.out.println(url);
		}
	}
}
