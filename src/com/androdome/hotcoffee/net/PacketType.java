    package com.androdome.hotcoffee.net;
     
    public enum PacketType {
     
       INDENTIFICATION (0, new int[]{1, 64, 64, 1}),
       PING (1, null),
       LEVEL_INIT (2, null),
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
     
       private int type;
       private int length;
     
       private PacketType(int type, int[] size) {
          this.type = type;
          this.length = size.length;
       }
     
    }