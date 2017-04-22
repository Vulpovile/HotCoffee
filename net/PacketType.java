    package com.androdome.hotcoffee.net;
     
    public enum PacketType {
     
       INDENTIFICATION(0, new int[]{1, 64, 64, 1}),
       PING(1, new int[]{0}),
       LEVEL_INIT(2, new int[]{0}),
       LEVEL_CHUNK(3, new int[]{2, 1024, 1}),
       LEVEL_FINAL(4, new int[]{2, 2, 2}),
       GET_BLOCK(5, new int[]{2,2,2,1,1}),
       SEND_BLOCK(6, new int[]{2, 2, 2, 1}),
       SPAWN(7, new int[]{11, 64, 2, 2, 2, 1, 1}),
       POSITION(8, new int[]{-1,2,2,2,1,1}),
       PO_UPDATE(9, new int[]{-1,-1,-1,-1,1,1}),
       P_UPDATE(10, new int[]{-1,-1,-1,-1}),
       O_UPDATE(11, new int[]{-1,1,1}),
       DESPAWN(12, new int[]{-1}),
       MESSAGE(13, new int[]{1, 64}),
       DISCONNECT(14, new int[]{64}),
       UPDATE_PLAYER(15, new int[]{1});
     
       public int type;
       public int length;
       public int[] size;
     
       private PacketType(int type, int[] types) {
          this.type = type;
          this.length = types.length;
          this.size = types;
       }
       
       public static PacketType getPacketType(int type)
       {
    	   if(type == 0) return PacketType.INDENTIFICATION;
    	   if(type == 1) return PacketType.PING;
    	   if(type == 2) return PacketType.LEVEL_INIT;
    	   if(type == 3) return PacketType.LEVEL_CHUNK;
    	   if(type == 4) return PacketType.LEVEL_FINAL;
    	   if(type == 5) return PacketType.GET_BLOCK;
    	   if(type == 6) return PacketType.SEND_BLOCK;
    	   if(type == 7) return PacketType.SPAWN;
    	   if(type == 8) return PacketType.POSITION;
    	   if(type == 9) return PacketType.PO_UPDATE;
    	   if(type == 10) return PacketType.P_UPDATE;
    	   if(type == 11) return PacketType.O_UPDATE;
    	   if(type == 12) return PacketType.DESPAWN;
    	   if(type == 13) return PacketType.MESSAGE;
    	   if(type == 14) return PacketType.DISCONNECT;
    	   if(type == 15) return PacketType.UPDATE_PLAYER;
    	   
    	   else return null;
       }
     
    }