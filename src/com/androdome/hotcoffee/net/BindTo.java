package com.androdome.hotcoffee.net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.DataInputStream;

import com.androdome.hotcoffee.server.HeartSaltSend;
import com.androdome.hotcoffee.server.Main;

public final class BindTo implements Runnable{

   public ServerSocket serverSocket;
   public Main server;
   public Socket socket;
   @SuppressWarnings("rawtypes")
public List c = new LinkedList();


   public BindTo(int var1, Main var2) throws IOException {
      this.serverSocket = new ServerSocket(var1, 10);
      server = var2;
      
   }
   public void run()
   {
	   while(server.running = true)
	      {
	   	   try {
	   		   socket = this.serverSocket.accept();
	   		   //System.out.print("Socket connected");
	   		   PacketParser pPacket = new PacketParser();
	   		   DataInputStream data = new DataInputStream(socket.getInputStream());
	   		   DataOutputStream send = new DataOutputStream(socket.getOutputStream());

//	   		   PacketType.
	   		   String[] info = pPacket.recieve(PacketType.INDENTIFICATION, data);
	   		   pPacket.send(PacketType.INDENTIFICATION, new Object[]{7, "Test", "tast", 0},  send);
	   		   
	   		   String mppass = info[2].trim();
	   		   String name = info[1].trim();
	   		   int packetversion = Integer.parseInt(info[0]);
	   		   

	   		   
	   		   System.out.println(name);
	   		   System.out.println(mppass);
	   		   
	   		if(mppass.trim().equalsIgnoreCase(HeartSaltSend.generate(Main.salt.trim() + name.trim()))) {
	   		 System.out.println("WHY WONT THIS WORK");
	   		} else {
	   		 System.out.println("YES");
	   		}
	   		   if(packetversion != 7)
	   		   {
	   			pPacket.send(PacketType.DISCONNECT, new Object[]{"Wrong packet version"}, send);  
	   			data.close();
	   			send.close();
	   			socket.close();
	   		   }
	   		   else
	   		   {
	   			pPacket.send(PacketType.DISCONNECT, new Object[]{"This is a packet test"}, send);
	   			data.close();
	   			send.close();
	   			socket.close();
	   		   }
		   	} catch (IOException e) {
		   		e.printStackTrace();
		   	}
	      }
   }
}
