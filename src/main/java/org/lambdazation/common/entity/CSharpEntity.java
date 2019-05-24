package org.lambdazation.common.entity;

import net.minecraft.block.Blocks;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;

public final class CSharpEntity extends MobEntityWithAi implements IBoss, RangedAttackMob {
	//public static final DataParameter<Integer> PHASE = EntityDataManager.createKey(CSharpEntity.class,DataSerializers.VARINT);
	public static final String CSHARP_LAUGH1 = I18n.translate("entity.csharp.laugh_word");
	public static final String CSHARP_LAUGH2 = I18n.translate("entity.csharp.laugh_word2");
	public static final String CSHARP_DEATH = I18n.translate("entity.csharp.death_word");
	public final CSharpEntityPart octopusHead;
	public final CSharpEntityPart octupusLeg1_1;
	public final CSharpEntityPart octupusLeg1_2;
	public final CSharpEntityPart octupusLeg1_3;
	public final CSharpEntityPart octupusLeg2_1;
	public final CSharpEntityPart octupusLeg2_2;
	public final CSharpEntityPart octupusLeg2_3;
	public final CSharpEntityPart octupusLeg3_1;
	public final CSharpEntityPart octupusLeg3_2;
	public final CSharpEntityPart octupusLeg3_3;
	public final CSharpEntityPart octupusLeg4_1;
	public final CSharpEntityPart octupusLeg4_2;
	public final CSharpEntityPart octupusLeg4_3;
	public final CSharpEntityPart octupusLeg5_1;
	public final CSharpEntityPart octupusLeg5_2;
	public final CSharpEntityPart octupusLeg5_3;
	public final CSharpEntityPart octupusLeg6_1;
	public final CSharpEntityPart octupusLeg6_2;
	public final CSharpEntityPart octupusLeg6_3;
	public final CSharpEntityPart octupusLeg7_1;
	public final CSharpEntityPart octupusLeg7_2;
	public final CSharpEntityPart octupusLeg7_3;
	public final CSharpEntityPart octupusLeg8_1;
	public final CSharpEntityPart octupusLeg8_2;
	public final CSharpEntityPart octupusLeg8_3;



	public final Lambdazation lambdazation;

	public CSharpEntity(Lambdazation lambdazation, EntityType<CSharpEntity> entityType,World world) {
		super(lambdazation.lambdazationEntityTypes.entityTypeCSharp, world);

		this.lambdazation = lambdazation;
		octopusHead=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 15,20);
		octupusLeg1_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg1_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg1_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg2_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg2_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg2_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg3_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg3_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg3_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg4_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg4_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg4_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg5_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg5_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg5_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg6_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg6_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg6_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg7_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg7_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg7_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg8_1=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg8_2=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
		octupusLeg8_3=new CSharpEntityPart(lambdazation,lambdazation.lambdazationEntityTypes.entityTypeCSharpPart,this.world, 5,10);
	}

	public void initEntityAI() {
		this.goalSelector.add(0,new ProjectileAttackGoal(this,3,100,40));
		//this.goalSelector.add();
		this.goalSelector.add(5, new StepAndDestroyBlockGoal(Blocks.TURTLE_EGG, this, 1.0D, 3));
	}

	public World getWorld() {
		return world;
	}

	@Override
	public void attack(LivingEntity var1, float var2) {

	}

	@Override
	public void initAttributes() {
		super.initAttributes();
		getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(2560);
		getAttributeInstance(EntityAttributes.FLYING_SPEED).setBaseValue(6);
	}

	@Override
	public void kill() {
		setHealth(getMaxBreath());
		sendMessage(new TextComponent(CSHARP_LAUGH1));
	}

	public boolean damage(DamageSource source,float harm) {
		Entity entity=source.getAttacker();
		if(entity instanceof PlayerEntity)
			return (!((PlayerEntity) entity).abilities.creativeMode && harm>=40);
		return false;
	}
}