package org.lambdazation.common.entity;

import net.minecraft.entity.ai.EntityAIBreakBlock;
import net.minecraft.init.Blocks;
import org.lambdazation.Lambdazation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public final class EntityCSharp extends EntityMob implements IEntityMultiPart, IBoss, IRangedAttackMob {
	public static final DataParameter<Integer> PHASE = EntityDataManager.createKey(EntityCSharp.class,
		DataSerializers.VARINT);

	public final Lambdazation lambdazation;

	public EntityCSharp(Lambdazation lambdazation, World world) {
		super(lambdazation.lambdazationEntityTypes.entityTypeCSharp, world);

		this.lambdazation = lambdazation;
	}

	public void initEntityAI() {
		this.tasks.addTask(0, new EntityAIAttackRanged(this, 3, 100, 40));
		this.tasks.addTask(5, new EntityAIBreakBlock(Blocks.TURTLE_EGG, this, 1.0D, 3));
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart partEntityPart, DamageSource source, float harm) {
		// TODO NYI
		return true;
	}

	@Override
	public void registerAttributes() {
		super.registerAttributes();
		getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2560);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

	}

	@Override
	public void setSwingingArms(boolean swingingArms) {

	}
}
