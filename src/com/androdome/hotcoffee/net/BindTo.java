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
	   		   PacketParser pParse= new PacketParser();
	   		   socket = this.serverSocket.accept();
	   		   socket.setSendBufferSize(1024);
	   		   socket.setReceiveBufferSize(1024);
	   		   //System.out.print("Socket connected");
	   		   
	   		   String[] id = pParse.recieve(PacketType.INDENTIFICATION, socket);
	   		   
	   		   
	   		   String mppass = id[1];
	   		   String name = id[2];
	   		   System.out.print(mppass);
	   		   
	   		   pParse.send(PacketType.INDENTIFICATION, new Object[]{5, "Welcome", "Yes", 0}, socket);
	   		   
	   		if(mppass.equalsIgnoreCase(HeartSaltSend.generate(name.trim() + Main.salt.trim()))) {
	   		 System.out.println("GODDAMNIT WHY WONT THIS WORK");
	   		} else {
	   		 System.out.println("FALSE");
	   		}
	   		
		   	} catch (IOException e) {
		   		e.printStackTrace();
		   	}
	      }
   }
}
