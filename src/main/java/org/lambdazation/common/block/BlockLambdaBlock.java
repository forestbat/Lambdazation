package org.lambdazation.common.block;

import org.lambdazation.Lambdazation;

import net.minecraft.block.Block;
public final class BlockLambdaBlock extends Block {
	public final Lambdazation lambdazation;

	public BlockLambdaBlock(Lambdazation lambdazation, Settings properties) {
		super(properties);
		this.lambdazation = lambdazation;
	}

	/*@Override
	public boolean isBeaconBase(BlockState state, IWorldReader world, BlockPos pos, BlockPos beacon) {
		return true;
	}*/
}
