package com.androdome.hotcoffee.net;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.LinkedList;
import java.util.List;

import com.androdome.hotcoffee.server.Main;

public final class BindTo {

   public ServerSocketChannel serverChannel;
   public Main server;
   @SuppressWarnings("rawtypes")
public List c = new LinkedList();


   public BindTo(int var1, Main var2) throws IOException {
      this.server = var2;
      this.serverChannel = ServerSocketChannel.open();
      this.serverChannel.socket().bind(new InetSocketAddress(var1));
      this.serverChannel.configureBlocking(false);
   }
}
