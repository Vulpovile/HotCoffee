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
	public static final boolean[] liquid = new boolean[256];
	public int id;
	public static final Block STONE;
	public static final Block GRASS;
	
	static {
		STONE = new Block(1);
		GRASS = new Block(2);
	}
	
	public void update()
	{
		
	}
}
