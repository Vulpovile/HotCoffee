package com.androdome.hotcoffee.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.BindException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import com.androdome.hotcoffee.net.BindTo;
import com.androdome.hotcoffee.net.PlayerHandler;

public class HotCoffeeServer {
	static DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss] ");
	private Properties properties = new Properties();
	public static String salt = "" + (new Random()).nextLong();
	public static boolean verify = true;
	public static int users = 0;
	public static int max;
	public static int port;
	public static String name;
	public static String motd;
	public static String consoleName;
	public static boolean public_;
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
		m.startup();
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
				{try {playerHandler[i].tick();} catch (IOException e) {this.gui.write("Error");}
				connected++;
				}
		}
		HotCoffeeServer.users = connected;
	}
	
	public void startup() {
			gui.write("Starting...");
			
			//Properties
			try {
				this.properties.load(new FileReader("server.properties"));
			} catch (FileNotFoundException e2) {
				this.gui.write("Properties file not found, creating new file");
			} catch (IOException e2) {
			}
			
	         HotCoffeeServer.name = this.properties.getProperty("server-name", "Minecraft Server");
	         HotCoffeeServer.motd = this.properties.getProperty("motd", "Welcome to my Minecraft Server!");
	         HotCoffeeServer.consoleName = this.properties.getProperty("console-name", "CONSOLE");
	         HotCoffeeServer.port = Integer.parseInt(this.properties.getProperty("port", "25565"));
	         HotCoffeeServer.max = Integer.parseInt(this.properties.getProperty("max-players", "16"));
	         HotCoffeeServer.public_ = Boolean.parseBoolean(this.properties.getProperty("public", "true"));
	         HotCoffeeServer.verify = Boolean.parseBoolean(this.properties.getProperty("verify-names", "true"));
	         
	         this.properties.setProperty("server-name", HotCoffeeServer.name);
	         this.properties.setProperty("motd", HotCoffeeServer.motd);
	         this.properties.setProperty("console-name", HotCoffeeServer.consoleName);
	         this.properties.setProperty("max-players", "" + HotCoffeeServer.max);
	         this.properties.setProperty("port", "" + HotCoffeeServer.port);
	         this.properties.setProperty("public", "" + HotCoffeeServer.public_);
	         this.properties.setProperty("verify-names", "" + HotCoffeeServer.verify);
			
			
			
			try {
				this.properties.store(new FileWriter("server.properties"), "HotCoffee properties");
			} catch (IOException e1) {
				gui.write("###Failed to save server properties!###");
			}
			//Properties end
						
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
		gui.nextLine();
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
	
	public boolean serverModifier(String modifier)
	{
		gui.nextLine();
		String[] modcommand = modifier.split(" ");
		if(modcommand[0].trim().equalsIgnoreCase("viewproperties"))
		{
			gui.write("Property server-name = " + name);
			gui.write("Property motd = " + motd);
			gui.write("Property console-name = " + consoleName);
			gui.write("Property max-players = " + max);
			gui.write("Property port = " + port);
			gui.write("Property public = " + public_);
			gui.write("Property verify-names = " + verify);
			return true;
		}
		else
		{
			gui.write(modcommand[0] + " is not a valid server modifier");
		}
		return false;
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
