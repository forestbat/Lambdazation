package org.lambdazation.common.entity;

import net.minecraft.entity.ai.EntityAIBreakBlock;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;

public class EntityCSharpObject extends EntityMob {
	public final Lambdazation lambdazation;

	public EntityCSharpObject(Lambdazation lambdazation, World world){
		super(lambdazation.lambdazationEntityTypes.entityTypeCSharpObject,world);
		this.lambdazation=lambdazation;
	}

	public void initEntityAI(){
		this.tasks.addTask(1,new EntityAINearestAttackableTarget<>(this,EntityPlayer.class,true ));
		this.tasks.addTask(2,new EntityAINearestAttackableTarget<>(this,EntityJavaObject.class,true));
		this.tasks.addTask(3,new EntityAIBreakBlock(Blocks.TURTLE_EGG,this,1,3));
	}
}
