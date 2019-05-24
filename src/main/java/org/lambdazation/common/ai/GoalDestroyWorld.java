import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.entity.JavaEntity;

import java.util.Iterator;

public class GoalDestroyWorld extends Goal {
	public final Lambdazation lambdazation;
	public final World world;
	private final JavaEntity javaEntity;
	private int timer = 0;

	public GoalDestroyWorld(Lambdazation lambdazation, JavaEntity javaEntity, World world) {
		this.lambdazation = lambdazation;
		this.javaEntity = javaEntity;
		this.world = world;
	}

	@Override
	public boolean canStart() {
		PlayerEntity closestPlayer = world.getClosestPlayer(javaEntity, 32);
		if (closestPlayer != null && !closestPlayer.abilities.flying) {
			timer = 10000;
			return closestPlayer.distanceTo(javaEntity) < 8 && Math.random() < 0.2;
		} else
			return false;
	}

	@Override
	public void start() {
		canStart();
	}

	@Override
	public void tick() {
		timer++;
		PlayerEntity closestPlayer = world.getClosestPlayer(javaEntity, 32);
		if (closestPlayer != null) {
			Iterable<BlockPos> blockPosIterable = BlockPos.iterate(closestPlayer.getBlockPos(), closestPlayer
				.getBlockPos()
				.east(15)
				.south(15)
				.down(128));
			Iterator<BlockPos> posIterator = blockPosIterable.iterator();
			while (posIterator.hasNext()) {
				world.setBlockState(posIterator.next(), Blocks.AIR.getDefaultState());
			}
		}
		if (closestPlayer == null && timer > 100) {
			BlockPos pos = javaEntity.getBlockPos();
			Iterable<BlockPos> blockPosIterable = BlockPos.iterate(pos
				.west(64)
				.north(64), pos
				.east(64)
				.south(64)
				.down(pos.getY()));
			Iterator<BlockPos> posIterator = blockPosIterable.iterator();
			while (posIterator.hasNext()) {
				BlockPos blockPos = posIterator.next();
				if (world
					.getBlockState(blockPos)
					.getBlock() == Blocks.AIR)
					world.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
			}
		}
	}
}

