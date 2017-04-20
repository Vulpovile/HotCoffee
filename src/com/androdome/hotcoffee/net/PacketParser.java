package com.androdome.hotcoffee.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PacketParser {

	public String[] recieve(PacketType pack, Socket sock) {
		try {
			DataInputStream data = new DataInputStream(sock.getInputStream());
			String[] packets = new String[pack.length];
			for(int i = 0; i < pack.length; i++)
			{
				if(pack.size[i] == 1)
				{
					packets[i] = String.valueOf(data.readUnsignedByte());
				}
				if(pack.size[i] == -1)
				{
					packets[i] = String.valueOf(data.readByte());
				}
				if(pack.size[i] == 2)
				{
					packets[i] = String.valueOf(data.readShort());
				}
				if(pack.size[i] == 64)
				{
					String inpack = "";
					for(int b = 0; b < 64; b++)
					{
						inpack = inpack + data.readChar();
					}
					packets[i] = inpack;
				}
				
			}
			data.close();
			return packets;
		} catch (IOException e) {
		}
		
		return null;
	}
	
	
	
	
	
	public boolean send(PacketType pack, Object[] attributes, Socket sock)
	{
		try {
			DataOutputStream send = new DataOutputStream(sock.getOutputStream());
			if(pack.length == attributes.length)
			{
				send.writeByte(pack.type);
				for(int i = 0; i < pack.length; i++)
				{
					if(pack.size[i] == 1)
					{
						send.writeByte(Integer.parseInt(String.valueOf(attributes[i])));
					}
					else if(pack.size[i] == -1)
					{
						send.writeByte(Integer.parseInt(String.valueOf(attributes[i])));
					}
					else if(pack.size[i] == 2)
					{
						send.writeShort(Short.parseShort(String.valueOf(attributes[i])));
					}
					else if(pack.size[i] == 64)
					{
						send.writeBytes(String.valueOf(attributes[i]));
					}
					else if(pack.size[i] == 1024)
					{
						send.write(String.valueOf(attributes[i]).getBytes());
					}
					else if(pack.size[i] == 0)
					{
						
					}
				}
				send.close();
				return true;
			}
			else
			{
				send.close();
				return false;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
