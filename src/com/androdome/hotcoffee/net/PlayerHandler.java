package com.androdome.hotcoffee.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.androdome.hotcoffee.server.HeartSaltSend;
import com.androdome.hotcoffee.server.Main;

public class PlayerHandler {
Socket socket;
String username;
Main main;
int playerid;
	public PlayerHandler(Socket sock, Main m) {
		socket = sock;
		main = m;
	}
	
	public void Player() throws IOException
	{
		boolean failed = false;
		   main.gui.write("Socket connected at " + socket.getInetAddress());
		   PacketParser pPacket = new PacketParser();
		   DataInputStream data = new DataInputStream(socket.getInputStream());
		   DataOutputStream send = new DataOutputStream(socket.getOutputStream());

//		   PacketType.
		   String[] info = pPacket.recieve(PacketType.INDENTIFICATION, data);
		   pPacket.send(PacketType.INDENTIFICATION, new Object[]{7, "Test", "tast", 0},  send);
		   
		   String mppass = info[2].replace(" ", "");
		   String name = info[1].replace(" ", "");
		   int packetversion = Integer.parseInt(info[0]);
		   

		   
		   main.gui.write(name);
		   main.gui.write(mppass);
		   String verify = HeartSaltSend.generate(Main.salt + name);
		   main.gui.write(verify);
		if(mppass.equalsIgnoreCase(verify)) {
		 main.gui.write("Verified");
		} else {
			if(Main.verify)
				failed = true;
		  main.gui.write("Not verified");
		}
		   if(packetversion != 7)
		   {
			pPacket.send(PacketType.DISCONNECT, new Object[]{"Wrong packet version"}, send);  
			data.close();
			send.close();
			socket.close();
		   }
		   else if(failed)
		   {
			   pPacket.send(PacketType.DISCONNECT, new Object[]{"You could not be verified by classicube.net!"}, send);  
			   data.close();
			   send.close();
			   socket.close();
		   }
		   else if(Main.users >= Main.max)
		   {
				pPacket.send(PacketType.DISCONNECT, new Object[]{"This server is full"}, send);
				data.close();
				send.close();
				socket.close();
		   }
		   else
		   {
		   }
	   	}
	}

