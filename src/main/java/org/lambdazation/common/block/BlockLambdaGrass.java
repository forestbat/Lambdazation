package org.lambdazation.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.lambdazation.Lambdazation;

public class BlockLambdaGrass extends Block {
    public final Lambdazation lambdazation;

    public BlockLambdaGrass(Lambdazation lambdazation,Settings properties){
        super(properties);
        this.lambdazation=lambdazation;
    }

    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable){
        return true;
    }

}
