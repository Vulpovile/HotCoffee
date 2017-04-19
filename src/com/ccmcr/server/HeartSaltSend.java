package com.ccmcr.server;

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
import java.util.Random;

public class HeartSaltSend {

	
	@SuppressWarnings("rawtypes")
	public static String Beat(String salt, int port, boolean public_, int users, int max, String name)
	{		
		HeartSaltSend hl = new HeartSaltSend();
		HashMap<String, Comparable> var9;
        (var9 = new HashMap<String, Comparable>()).put("name", name);
        var9.put("users", Integer.valueOf(users));
        var9.put("max", Integer.valueOf(max));
        var9.put("public", Boolean.valueOf(public_));
        var9.put("port", Integer.valueOf(port));
        var9.put("salt", salt);
        var9.put("software", "HotCoffee");
        var9.put("admin-slot", Boolean.valueOf(false));
        var9.put("version", Byte.valueOf((byte)7));
        String var13 = stringer((Map<String, Comparable>)var9);
        return hl.doHeartBeat(var13, name);
	}
	
	
	public final String generate(String var1) {
	      try {
	         String var3 = var1;
	         MessageDigest var4;
	         (var4 = MessageDigest.getInstance("MD5")).update(var3.getBytes(), 0, var3.length());
	         return (new BigInteger(1, var4.digest())).toString(16);
	      } catch (NoSuchAlgorithmException var2) {
	         throw new RuntimeException(var2);
	      }
	   }
	
	
	
	   private static String stringer(Map<String, Comparable> var0) {
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
		HttpURLConnection var1 = null;

	      try {
	         var1 = (HttpURLConnection)(new URL("http://www.classicube.net/heartbeat.jsp")).openConnection();
	         var1.setRequestMethod("POST");
	         var1.setDoOutput(true);
	         var1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	         var1.setRequestProperty("Content-Length", Integer.toString(name.getBytes().length));
	         var1.setRequestProperty("Content-Language", "en-US");
	         var1.setUseCaches(false);
	         var1.setDoInput(true);
	         var1.setDoOutput(true);
	         var1.connect();
	         DataOutputStream var7;
	         (var7 = new DataOutputStream(var1.getOutputStream())).writeBytes(a);
	         var7.flush();
	         var7.close();
	         BufferedReader var9;
	         String var3 = (var9 = new BufferedReader(new InputStreamReader(var1.getInputStream()))).readLine();
	         var9.close();
	         return var3;

	      }
	      catch(Exception ex)
	      {
	    	  System.out.println("Something went wrong");
	      }
		return "Failed to generate heartbeat";
	}
}
