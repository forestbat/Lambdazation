package org.lambdazation.common.entity;

import net.minecraft.entity.ai.EntityAIBreakBlock;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;

public class EntityJavaObject extends EntityMob {
    public final Lambdazation lambdazation;
    public EntityJavaObject(Lambdazation lambdazation, World world){
        super(lambdazation.lambdazationEntityTypes.entityTypeJava,world);
        this.lambdazation=lambdazation;
    }
    public void initEntityAI(){
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class,true));
        this.targetTasks.addTask(2,new EntityAIBreakBlock(Blocks.TURTLE_EGG,this,1,3));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityZombie.class, true));
    }
}
