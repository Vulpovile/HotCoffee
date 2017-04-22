package com.androdome.hotcoffee.server;

import java.io.IOException;
import java.net.BindException;
import java.util.Random;
import java.util.UUID;

import com.androdome.hotcoffee.net.BindTo;
import com.androdome.hotcoffee.net.PlayerHandler;

public class HotCoffeeServer {
	public static String salt = "" + (new Random()).nextLong();
	public static boolean verify = true;
	public static int users = 0;
	public static int max = 2;
	public static int port = 25565;
	public static String name = "Test";
	public static boolean public_ = true;
	public int ticks = 0;
	public static String url;
	public boolean running = true;
	public GraphicalUserInterface gui = new GraphicalUserInterface(this);
	BindTo bindTo;
	public static PlayerHandler[] playerHandler = new PlayerHandler[max];

	public static void main(String args[]) {
		UUID uuid = UUID.randomUUID();
		salt = HeartSaltSend.generate(uuid.toString());
		HotCoffeeServer m = new HotCoffeeServer();
		m.gui.setLocationRelativeTo(null);
		m.gui.setVisible(true);
		try{
		m.instantiate();
		}
		catch(Exception ex)
		{
			m.gui.write("###FATAL ERROR IN MAIN LOOP! Server shut down!###");
			m.running = false;
		}
	}
	
	public void tickAllPlayers()
	{
		int connected = 0;
		for(int i = 0; i < playerHandler.length; i++)
		{
			if(playerHandler[i] != null)
				{try {playerHandler[i].tick();} catch (IOException e) {}
				connected++;
				}
		}
		HotCoffeeServer.users = connected;
	}
	
	public void instantiate() {

		try 
		{
			this.bindTo = new BindTo(HotCoffeeServer.port, this);
			url = HeartSaltSend.Beat(HotCoffeeServer.salt, HotCoffeeServer.port, HotCoffeeServer.public_, HotCoffeeServer.users, HotCoffeeServer.max, HotCoffeeServer.name);
			gui.write("Success! Your heartbeat URL is:");
			gui.write(url);
		}
		catch(BindException ex)
		{
			this.gui.write("###Failed to bind to port, " + port + " is already in use!###");
			this.running = false;
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			this.running = false;
		}
		new Thread(this.bindTo).start();
		new ServerThread(this).run();
	}
	
	public boolean command(String command, PlayerHandler player)
	{
		String[] spcmd = command.split(" ");
		gui.write("");
		String user = "";
		if(player == null)
			user = "CONSOLE";
		else
			user = player.username;	
		gui.write(user + " used the command: " + spcmd[0]);
		

		if(spcmd[0].equalsIgnoreCase("info"))
		{
			if(player == null)
			gui.write("HotCoffee is a Minecraft Classic server based on Java");
		}
		else
		{
			if(player == null)
				gui.write(spcmd[0] + " is not a valid command");
			return false;
		}
		return true;
	}

	public void tick() {
		MemUsage.getmem(this);
		ticks++;
		if (ticks >= 2800) 
		{
			ticks = 0;
			url = HeartSaltSend.Beat(HotCoffeeServer.salt, HotCoffeeServer.port, HotCoffeeServer.public_, HotCoffeeServer.users, HotCoffeeServer.max, HotCoffeeServer.name);
			if(url.contains("Failed"))
			gui.write(url);
		}
		tickAllPlayers();
	}
}