package com.androdome.hotcoffee.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@SuppressWarnings({"rawtypes", "unchecked"})
public class HeartSaltSend {

	

	public static String Beat(String salt, int port, boolean public_, int users, int max, String name)
	{		
		HeartSaltSend hl = new HeartSaltSend();
		HashMap<String, Comparable> heartbeatHashMap;
        (heartbeatHashMap = new HashMap<String, Comparable>()).put("name", name);
        heartbeatHashMap.put("users", Integer.valueOf(users));
        heartbeatHashMap.put("max", Integer.valueOf(max));
        heartbeatHashMap.put("public", Boolean.valueOf(public_));
        heartbeatHashMap.put("port", Integer.valueOf(port));
        heartbeatHashMap.put("salt", salt);
        heartbeatHashMap.put("software", "HotCoffee");
        heartbeatHashMap.put("admin-slot", Boolean.valueOf(false));
        heartbeatHashMap.put("version", Byte.valueOf((byte)7));
        String var13 = stringer((Map<String, Comparable>)heartbeatHashMap);
        return hl.doHeartBeat(var13, name);
	}
	
	
	public final static String generate(String salt) {
	      try {
	         MessageDigest var4;
	         (var4 = MessageDigest.getInstance("MD5")).update(salt.getBytes(), 0, salt.length());
	         return (new BigInteger(1, var4.digest())).toString(16);
	      } catch (NoSuchAlgorithmException ex) {
	    	  return null;
	      }
	   }
	
	
	
	private static String stringer(Map var0) {
		      try {
		         String var1 = "";

		         String var3;
		         for(Iterator<String> var2 = var0.keySet().iterator(); var2.hasNext(); var1 = var1 + var3 + "=" + URLEncoder.encode(var0.get(var3).toString(), "UTF-8")) {
		            var3 = var2.next();
		            if(var1 != "") {
		               var1 = var1 + "&";
		            }
		         }

		         return var1;
		      } catch (Exception var4) {
		         var4.printStackTrace();
		         throw new RuntimeException("Failed to assemble heartbeat! This is pretty fatal");
		      }
		   }

	public String doHeartBeat(String a, String name)
	{
		HttpURLConnection heartBeat = null;

	      try {
	         heartBeat = (HttpURLConnection)(new URL("http://www.classicube.net/heartbeat.jsp")).openConnection();
	         heartBeat.setRequestMethod("POST");
	         heartBeat.setDoOutput(true);
	         heartBeat.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	         heartBeat.setRequestProperty("Content-Length", Integer.toString(name.getBytes().length));
	         heartBeat.setRequestProperty("Content-Language", "en-US");
	         heartBeat.setUseCaches(false);
	         heartBeat.setDoInput(true);
	         heartBeat.setDoOutput(true);
	         heartBeat.connect();
	         DataOutputStream datapage;
	         (datapage = new DataOutputStream(heartBeat.getOutputStream())).writeBytes(a);
	         datapage.flush();
	         datapage.close();
	         BufferedReader read;
	         String link = (read = new BufferedReader(new InputStreamReader(heartBeat.getInputStream()))).readLine();
	         read.close();
	         return link;

	      }
	      catch(Exception ex)
	      {
	    	  System.out.println("Something went wrong");
	      }
		return "Failed to generate heartbeat";
	}
}
