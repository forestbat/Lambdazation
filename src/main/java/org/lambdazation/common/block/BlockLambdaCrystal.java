package org.lambdazation.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.lambdazation.Lambdazation;

public class BlockLambdaCrystal extends Block {
    public Lambdazation lambdazation;
    public BlockLambdaCrystal(Lambdazation lambdazation,Properties properties){
        super(properties);
    }

    @Override
    public boolean isBeaconBase(IBlockState state, IWorldReader world, BlockPos pos, BlockPos beacon) {
        return true;
    }
}
