package com.androdome.hotcoffee.net;

@SuppressWarnings({"rawtypes"})
public final class PacketType {

   public static final PacketType[] packets = new PacketType[256];
   public static final PacketType INDENTIFICATION = new PacketType(0, new Class[]{Byte.TYPE, String.class, String.class, Byte.TYPE});
   public static final PacketType PING = new PacketType(1, new Class[0]);
   public static final PacketType LEVEL_INITIALIZE = new PacketType(2, new Class[0]);
   public static final PacketType LEVEL_DATA_CHUNK = new PacketType(3, new Class[]{Short.TYPE, byte[].class, Byte.TYPE});
   public static final PacketType LEVEL_FINALIZE = new PacketType(4, new Class[]{Short.TYPE, Short.TYPE, Short.TYPE});
   public static final PacketType PLAYER_SET_BLOCK = new PacketType(5, new Class[]{Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType BLOCK_CHANGE = new PacketType(6, new Class[]{Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE});
   public static final PacketType SPAWN_PLAYER = new PacketType(7, new Class[]{Byte.TYPE, String.class, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType POSITION_ROTATION = new PacketType(8, new Class[]{Byte.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType POSITION_ROTATION_UPDATE = new PacketType(9, new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType POSITION_UPDATE = new PacketType(10, new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType ROTATION_UPDATE = new PacketType(11, new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType DESPAWN_PLAYER = new PacketType(12, new Class[]{Byte.TYPE});
   public static final PacketType CHAT_MESSAGE = new PacketType(13, new Class[]{Byte.TYPE, String.class});
   public static final PacketType DISCONNECT = new PacketType(14, new Class[]{String.class});
   public static final PacketType UPDATE_PLAYER_TYPE = new PacketType(15, new Class[]{Byte.TYPE});
   public static final PacketType CPE_EXTINFO = new PacketType(16, new Class[]{String.class, Short.TYPE});
   public static final PacketType CPE_EXTENTRY = new PacketType(17, new Class[]{String.class, Integer.TYPE});
   public static final PacketType CPE_DIST = new PacketType(18, new Class[]{Short.TYPE});
   public static final PacketType CPE_BLOCK = new PacketType(19, new Class[]{Byte.TYPE});
   public final int length;
   public final byte opCode;
   public Class[] params;


   private PacketType(int i, Class ... var1) {
      this.opCode = (byte) i;
      packets[this.opCode] = this;
      this.params = new Class[var1.length];
      int var2 = 0;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         Class var4 = var1[var3];
         this.params[var3] = var4;
         if(var4 == Long.TYPE) {
            var2 += 8;
         } else if(var4 == Integer.TYPE) {
            var2 += 4;
         } else if(var4 == Short.TYPE) {
            var2 += 2;
         } else if(var4 == Byte.TYPE) {
            ++var2;
         } else if(var4 == Float.TYPE) {
            var2 += 4;
         } else if(var4 == Double.TYPE) {
            var2 += 8;
         } else if(var4 == byte[].class) {
            var2 += 1024;
         } else if(var4 == String.class) {
            var2 += 64;
         }
      }

      this.length = var2;
   }

}
