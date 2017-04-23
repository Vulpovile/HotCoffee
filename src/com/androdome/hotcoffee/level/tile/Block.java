package com.androdome.hotcoffee.level.tile;

public class Block {
	protected Block(int id)
	{
		explodes = true;
		blocks[id] = this;
		this.id = id;
		liquid[id] = false;
	}
	protected boolean explodes;
	public static final Block[] blocks = new Block[256];
	public static final boolean[] physics = new boolean[256];
	public static final boolean[] liquid = new boolean[256];
	private static int[] tickDelay = new int[256];
	public int id;
	
	static {

	}
	public void update()
	{
		
	}
}
