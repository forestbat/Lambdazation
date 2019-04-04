package org.lambdazation.common.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.entity.EntityJava;

import java.util.Iterator;

public class EntityAIDestoryWorld extends EntityAIBase {
	public Lambdazation lambdazation;
	public World world;
	private EntityJava entityJava;
	private Timer timer;

	@Override
	public boolean shouldExecute() {
		EntityPlayer closestPlayer = world.getClosestPlayerToEntity(entityJava, 32);
		if (closestPlayer != null) {
			timer.updateTimer(0);
			return closestPlayer.getDistance(entityJava) < 8 && Math.random() < 0.2;
		} else
			return false;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
	}

	@Override
	public void resetTask() {
		super.resetTask();
	}

	@Override
	public void tick() {
		EntityPlayer closestPlayer = world.getClosestPlayerToEntity(entityJava, 32);
		if (closestPlayer != null && !closestPlayer.abilities.isFlying) {
			Iterable<BlockPos> blockPosIterable = BlockPos.getAllInBox(closestPlayer.getPosition(),
				closestPlayer.getPosition().east(15).south(15).down(128));
			Iterator<BlockPos> posIterator = blockPosIterable.iterator();
			while (posIterator.hasNext()) {
				world.setBlockState(posIterator.next(), Blocks.AIR.getDefaultState());
			}
		}
		if (closestPlayer != null) {
			BlockPos pos = entityJava.getPosition();
			Iterable<BlockPos> blockPosIterable = BlockPos.getAllInBox(pos.west(64).north(64),
				pos.east(64).south(64).down(pos.getY()));
			Iterator<BlockPos> posIterator = blockPosIterable.iterator();
			while (posIterator.hasNext()) {
				BlockPos blockPos = posIterator.next();
				if (world.getBlockState(blockPos).getBlock() == Blocks.AIR)
					world.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
			}
		}
	}
}
