package com.androdome.hotcoffee.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.androdome.hotcoffee.server.HeartSaltSend;
import com.androdome.hotcoffee.server.HotCoffeeServer;

public class PlayerHandler 
{
	Socket socket;
	public String username;
	HotCoffeeServer main;
	int playerid;
	DataInputStream in;
	DataOutputStream out;
	PacketParser parsePacket;
	boolean tickEnabled = false;
	public boolean CPE = false;

	public PlayerHandler(Socket sock, HotCoffeeServer m) 
	{
		socket = sock;
		main = m;
	}

	public void Player() throws IOException 
	{
		boolean failed = false;
		main.gui.write("Socket connected from " + socket.getInetAddress());
		parsePacket = new PacketParser();
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());

		//Get identification
		String[] info = this.recievePacket();
		
		//Send identification
		parsePacket.send(PacketType.INDENTIFICATION, new Object[] { 7, "Test", "tast", 0}, out);
		
		if(Integer.parseInt(info[3]) == 66)
			CPE = true;
		
		String mppass = info[2].replace(" ", "");
		String name = info[1].replace(" ", "");
		int packetversion = Integer.parseInt(info[0]);
		
		String verify = HeartSaltSend.generate(HotCoffeeServer.salt + name);

		if (mppass.equalsIgnoreCase(verify)) 
		{
			main.gui.write("Verified");
		} 
		
		else 
		{
			if (HotCoffeeServer.verify)
				failed = true;
			main.gui.write("Not verified");
		}
		
		if (packetversion != 7 || failed || HotCoffeeServer.users >= HotCoffeeServer.max) 
		{
			String error = "";
			
			if (packetversion != 7)
				error = "Wrong packet version";
			else if (failed)
				error = "You could not be verified by classicube.net!";
			else if (HotCoffeeServer.users >= HotCoffeeServer.max)
				error = "This server is full";
			
			parsePacket.send(PacketType.DISCONNECT, new Object[] { error }, out);
			main.gui.write(name + " disconnected: " + error);
			socket.close();
		} 
		
		else 
		{
			this.username = name;
			for(int i = 0; i < HotCoffeeServer.playerHandler.length; i++)
			{
				if(HotCoffeeServer.playerHandler[i] == null){
					HotCoffeeServer.playerHandler[i] = this;
					this.playerid = i;
					break;
				}
			}
			main.gui.write("Player successfully connected: " + username + ":" + playerid);
			connect();
			
		}
	}
	
	public void connect() throws IOException
	{
		main.gui.write("beginning connection...");
		parsePacket.send(PacketType.INDENTIFICATION, new Object[]{7, "Hi", "Server", 0}, out);
		parsePacket.send(PacketType.LEVEL_INIT, new Object[]{}, out);
		//disconnect("Connection not yet created");
		this.tickEnabled = true;
	}
	
	
	public void disconnect(String reason) throws IOException
	{
		if(reason == null)
			reason = "Kicked";
		else
			reason = "Kicked: " + reason;
		if(out != null)
		{
			main.gui.write(username + " was disconnected: " + reason);
			parsePacket.send(PacketType.DISCONNECT, new Object[] {reason}, out);
			socket.close();
		}	
		HotCoffeeServer.playerHandler[playerid] = null;
	}
	
	public boolean sendPacket(PacketType type, Object[] obj)
	{
		return parsePacket.send(type, obj, out);
	}
	
	public String[] recievePacket() throws IOException
	{
		int opcode = 0;
		boolean opcodeValid = true;
		try{opcode = in.readByte();}catch(Exception ex){opcodeValid = false;}
		
		if(opcodeValid);
		{
			PacketType packet = PacketType.getPacketType(opcode);
			if(packet != null){
				String[] input = parsePacket.recieve(packet, in, opcode);
				if(input == null){
					this.socket.close();
					HotCoffeeServer.playerHandler[playerid] = null;
					main.gui.write(username + " has disconnected.");
					return null;}
				else{
				for(int i = 0; i < input.length; i++)
				{
				}
				return input;
				}
			}
			else
			{
				return null;
			}
			
		}
	}
	
	
	public void tick() throws IOException
	{
		if(tickEnabled)
		{
			String[] res = recievePacket();
			if(res != null)
			{
				
			}
		}
	}
	
}
