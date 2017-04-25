package com.androdome.hotcoffee.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketParser {

	public String[] recieve(PacketType pack, DataInputStream data, int type) {
		try {
			String[] packets = new String[pack.length];
			if(type == pack.type)
			{
			for(int i = 0; i < pack.length; i++)
			{
				if(pack.size[i] == 1)
				{
					packets[i] = String.valueOf(data.readUnsignedByte());
				}
				else if(pack.size[i] == -1)
				{
					packets[i] = String.valueOf(data.readByte());
				}
				else if(pack.size[i] == 2)
				{
					packets[i] = String.valueOf(data.readShort());
				}
				else if(pack.size[i] == 64)
				{
					byte[] string = new byte[64];
			   		   for(int b = 0; b < string.length; b++)
			   		   {
			   			   string[b] = (byte) data.readUnsignedByte();
			   		   }
			   		packets[i] = new String(string, "Cp437");
				}
				
			}
			}
			else
			{
				packets = null;
			}
			return packets;
		} catch (IOException e) {
		}
		
		return null;
	}
	
	
	
	
	
	public boolean send(PacketType pack, Object[] attributes, DataOutputStream send)
	{
		try {
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
						for(int b = 0; b < 64; b++)
						{
							String cp437 = new String(String.valueOf(attributes[i]));
							byte[] sr = cp437.getBytes("Cp437");
							if(b >= sr.length)
							send.writeByte(0);
							else
								send.writeByte(sr[b]);
						}
					}
					else if(pack.size[i] == 1024)
					{
						send.write(String.valueOf(attributes[i]).getBytes());
					}
					else if(pack.size[i] == 0)
					{
						
					}
				}
				return true;
			}
			else
			{
				return false;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
