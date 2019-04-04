package org.lambdazation.common.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.entity.EntityJava;

public class EntityAISpawnMinions extends EntityAIBase {
	public Lambdazation lambdazation;
	private EntityJava entityJava;
	private World world;

	@Override
	public boolean shouldExecute() {
		return entityJava.getHealth() / entityJava.getMaxHealth() < 0.55;
	}

	@Override
	public void tick() {
		if (entityJava.getWorld().getDayTime() % 1000 == 0) {
			world.spawnEntity(new EntityWither(world));//todo although I want to spawn Wither
		}
	}
}
