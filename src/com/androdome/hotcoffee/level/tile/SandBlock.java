package com.androdome.hotcoffee.level.tile;

public class SandBlock extends Block{

	protected SandBlock(int id) {
		super(id);
		
	}
	
	public void update()
	{
		this.fall();
	}
	
	protected void fall()
	{
		
	}

}
