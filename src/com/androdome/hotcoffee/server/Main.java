package com.androdome.hotcoffee.server;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import com.androdome.hotcoffee.net.BindTo;

public class Main {
	public static String salt = "" + (new Random()).nextLong();
	public static boolean verify = true;
	public static int users = 0;
	public static int max = 0;
	public static int port = 25565;
	public static String name = "Test";
	public static boolean public_ = true;
	public int ticks = 0;
	public static String url;
	public boolean running = true;
	public GraphicalUserInterface gui = new GraphicalUserInterface();
	BindTo bindTo;
	
	public static void main(String args[])
	{
		UUID uuid = UUID.randomUUID();
		salt = HeartSaltSend.generate(uuid.toString());
		Main m = new Main();
		m.gui.setLocationRelativeTo(null);
		m.gui.setVisible(true);
		url = HeartSaltSend.Beat(Main.salt, Main.port, Main.public_, Main.users, Main.max, Main.name);
		m.gui.write("Success! Your heartbeat URL is:");
		m.gui.write(url);
		m.instantiate();
	}
	
	public void instantiate()
	{
		
		try {
			this.bindTo = new BindTo(Main.port, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this.bindTo).start();
		new ServerThread(this).run();
	}
	
	public void tick()
	{
		MemUsage.getmem(this);
		ticks++;
		if(ticks >= 2800)
		{
			ticks = 0;
			url = HeartSaltSend.Beat(Main.salt, Main.port, Main.public_, Main.users, Main.max, Main.name);
			gui.write(url);
		}
	}
}
