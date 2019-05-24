package org.lambdazation.common.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.lambdazation.Lambdazation;

import java.util.Iterator;

public class JavaObjectEntity extends MobEntityWithAi {
	public final Lambdazation lambdazation;

	public JavaObjectEntity(Lambdazation lambdazation, EntityType<JavaObjectEntity> entityType,World world) {
		super(lambdazation.lambdazationEntityTypes.entityTypeJavaObject, world);
		this.lambdazation = lambdazation;
	}

	public void initAttributes() {
		super.initAttributes();
		getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(4);
		getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(40);
	}

	public void initEntityAI() {
		this.goalSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.goalSelector.add(2, new MeleeAttackGoal(this, 4, true));
		this.goalSelector.add(3, new StepAndDestroyBlockGoal(Blocks.TURTLE_EGG, this, 1, 3));
	}

	@Override
	public void onAttacking(Entity entityLiving) {
		if (entityLiving instanceof LivingEntity) {
			JavaObjectEntity javaObjectEntity = new JavaObjectEntity(lambdazation, lambdazation.lambdazationEntityTypes.entityTypeJavaObject,world);
			javaObjectEntity.setPosition(entityLiving.x, entityLiving.y, entityLiving.z);
			if(entityLiving instanceof MobEntity)
				((MobEntity) entityLiving).setAiDisabled(true);
			world.spawnEntity(javaObjectEntity);
			entityLiving.remove();
		}
	}

	public void chanceSpawn() {
		Iterator<Biome> iterator = Registry.BIOME.iterator();
		while (iterator.hasNext())
			iterator.next().getEntitySpawnList(EntityCategory.CREATURE).
				add(new Biome.SpawnEntry(lambdazation.lambdazationEntityTypes.entityTypeJavaObject,
					1, 1, 10));
	}

	@Override
	public void onDeath(DamageSource cause) {
		dropStack(new ItemStack(lambdazation.lambdazationItems.itemOOPSoul),2);//todo
	}
}
