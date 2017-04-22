package com.androdome.hotcoffee.server;

public class MemUsage {
	public static void getmem(HotCoffeeServer main){
	Runtime runtime = Runtime.getRuntime();
	float mb = 1024.00f * 1024.00f;
	
	float used = (float)(runtime.totalMemory() - runtime.freeMemory()) /mb;
	float max = (float)runtime.maxMemory() / mb;
	float free = max - used;
	String m = String.format("%.2f", max);
	String u = String.format("%.2f", used);
	String f = String.format("%.2f", free);
	
	boolean urgent = false;
	if(free < 15)
	urgent = true;
	
	main.gui.tickMemory(m, u, f, urgent);

	}
} 
