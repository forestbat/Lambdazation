package org.lambdazation.common.entity;

import net.minecraft.client.network.packet.EntityS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;

public class CSharpEntityPart extends Entity {
	public final Lambdazation lambdazation;
	public final EntitySize entitySize;
	public CSharpEntityPart(Lambdazation lambdazation,EntityType<CSharpEntityPart> entityType,World world,int width,int height){
		super(lambdazation.lambdazationEntityTypes.entityTypeCSharp, world);
		this.lambdazation=lambdazation;
		entitySize=EntitySize.resizeable(width,height);
	}

	public void initDataTracker(){

	}
	public void readCustomDataFromTag(CompoundTag compoundTag){

	}

	public void writeCustomDataToTag(CompoundTag compoundTag){

	}

	public Packet createSpawnPacket(){
		return new EntityS2CPacket();
	}

}
