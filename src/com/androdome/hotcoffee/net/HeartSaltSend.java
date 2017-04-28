package com.androdome.hotcoffee.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
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
        String heartHash = stringer((Map<String, Comparable>)heartbeatHashMap);
        return hl.doHeartBeat(heartHash, name);
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
	
	
	
	private static String stringer(Map map) {
		      try {
		         String hash = "";

		         String hashBasic;
		         for(Iterator<String> reader = map.keySet().iterator(); reader.hasNext(); hash = hash + hashBasic + "=" + URLEncoder.encode(map.get(hashBasic).toString(), "UTF-8")) {
		            hashBasic = reader.next();
		            if(hash != "") {
		               hash = hash + "&";
		            }
		         }

		         return hash;
		      } catch (Exception ex) {
		         return "Failed to assemble heartbeat! This is pretty fatal";
		      }
		   }

	public String doHeartBeat(String hash, String name)
	{
		HttpsURLConnection heartBeat = null;

	      try {
	         heartBeat = (HttpsURLConnection)(new URL("https://www.classicube.net/heartbeat.jsp")).openConnection();
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
	         (datapage = new DataOutputStream(heartBeat.getOutputStream())).writeBytes(hash);
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
	    	  return "Failed to generate heartbeat!";
	      }
		
	}
}
