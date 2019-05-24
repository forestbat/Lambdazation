package org.lambdazation.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.item.ItemPropertyGetter;
import net.minecraft.world.updater.WorldUpdater;
import org.lambdazation.Lambdazation;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public final class BlockLambdaOre extends OreBlock {
	public final Lambdazation lambdazation;

	public BlockLambdaOre(Lambdazation lambdazation, Block.Settings properties) {
		super(properties);
		this.lambdazation = lambdazation;
	}

	@Override
	public ItemPropertyGetter getItemDropped(BlockState state, World worldIn, BlockPos pos, int fortune) {
		if (equals(lambdazation.lambdazationBlocks.blockLambdaOre))
			return lambdazation.lambdazationItems.itemLambdaCrystal;
		else
			return this;
	}

	@Override
	public int quantityDropped(BlockState state, Random random) {
		return 1;
	}

	@Override
	public int getExpDrop(BlockState state, World reader, BlockPos pos, int fortune) {
		if (equals(lambdazation.lambdazationBlocks.blockLambdaOre))
			return MathHelper.nextInt(RANDOM, 550, 2920);
		else
			return 0;
	}
}
