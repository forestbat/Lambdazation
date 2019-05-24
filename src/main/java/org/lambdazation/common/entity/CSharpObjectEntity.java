package org.lambdazation.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import org.lambdazation.Lambdazation;

import java.util.Iterator;

public class CSharpObjectEntity extends MobEntity {
	public final Lambdazation lambdazation;

	public CSharpObjectEntity(Lambdazation lambdazation, EntityType<CSharpObjectEntity> entityType,World world) {
		super(lambdazation.lambdazationEntityTypes.entityTypeCSharpObject, world);
		this.lambdazation = lambdazation;
		setMovementSpeed(6);
	}

	public void initEntityAI() {
		this.tasks.addTask(1, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));
		this.tasks.addTask(2, new EntityAINearestAttackableTarget<>(this, JavaObjectEntity.class, true));
		this.tasks.addTask(3, new EntityAIBreakBlock(Blocks.TURTLE_EGG, this, 1, 3));
		//todo NYI
	}

	@SubscribeEvent
	public void chanceSpawn(LivingDeathEvent event) {
		if (event.getEntity() instanceof CSharpEntity) {
			Iterator<Biome> iterator = Biome.BIOMES.iterator();
			while (iterator.hasNext())
				iterator.next().getSpawns(EntityCategory.CREATURE).
					add(new Biome.SpawnListEntry(lambdazation.lambdazationEntityTypes.entityTypeCSharpObject,
						1, 1, 10));
		}
	}

	@SubscribeEvent
	public void changeCreatureToMobs(LivingAttackEvent event){
		if(event.getSource().getTrueSource()==this){
			Entity entity=event.getEntity();
			if(entity instanceof EntityAnimal){
				((EntityAnimal) entity).setAttackTarget(world.getClosestPlayerToEntity(entity,32));
				((EntityAnimal) entity).targetTasks.addTask(1,new EntityAIAttackMelee((EntityAnimal)entity,1,true));
			}
		}
	}
}

