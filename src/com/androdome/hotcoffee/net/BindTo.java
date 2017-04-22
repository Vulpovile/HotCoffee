package com.androdome.hotcoffee.net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import com.androdome.hotcoffee.server.Main;

public final class BindTo implements Runnable{

   public ServerSocket serverSocket;
   public Main server;
   public Socket socket;
   @SuppressWarnings("rawtypes")
public List c = new LinkedList();


   public BindTo(int var1, Main var2) throws IOException {
      this.serverSocket = new ServerSocket(var1, Main.max);
      server = var2;
      
   }
   public void run()
   {
	   while(server.running = true)
	      {

	   		   try {
				socket = this.serverSocket.accept();
				PlayerHandler pl = new PlayerHandler(socket, server);
				pl.Player();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      }
   }
}
