package com.androdome.hotcoffee;

import java.util.List;

import com.androdome.hotcoffee.level.tile.Block;

public class PermittedBlocks {
	public static List<Block> allowed;
	static{
		allowed.add(Block.GRASS);
		allowed.add(Block.STONE);
	}
}
