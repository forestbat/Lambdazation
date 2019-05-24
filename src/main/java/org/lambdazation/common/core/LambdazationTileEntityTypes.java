package org.lambdazation.common.core;

import org.lambdazation.Lambdazation;
import org.lambdazation.common.blockentity.BlockEntityCharger;
import org.lambdazation.common.blockentity.BlockEntityCrystallizer;
import org.lambdazation.common.blockentity.BlockEntityReducer;
import org.lambdazation.common.blockentity.BlockEntityTransformer;

import net.minecraft.tileentity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;

public final class LambdazationBlockEntityTypes {
	public final Lambdazation lambdazation;

	public final BlockEntityType<BlockEntityCrystallizer> tileEntityTypeCrystallizer;
	public final BlockEntityType<BlockEntityTransformer> tileEntityTypeTransformer;
	public final BlockEntityType<BlockEntityCharger> tileEntityTypeCharger;
	public final BlockEntityType<BlockEntityReducer> tileEntityTypeReducer;

	public LambdazationBlockEntityTypes(Lambdazation lambdazation) {
		this.lambdazation = lambdazation;

		tileEntityTypeCrystallizer = BlockEntityType.Builder
			.create(() -> new BlockEntityCrystallizer(lambdazation))
			.build(null);
		tileEntityTypeCrystallizer.setRegistryName(new Identifier("lambdazation:crystallizer"));
		tileEntityTypeTransformer = BlockEntityType.Builder
			.create(() -> new BlockEntityTransformer(lambdazation))
			.build(null);
		tileEntityTypeTransformer.setRegistryName(new Identifier("lambdazation:transformer"));
		tileEntityTypeCharger = BlockEntityType.Builder
			.create(() -> new BlockEntityCharger(lambdazation))
			.build(null);
		tileEntityTypeCharger.setRegistryName(new Identifier("lambdazation:charger"));
		tileEntityTypeReducer = BlockEntityType.Builder
			.create(() -> new BlockEntityReducer(lambdazation))
			.build(null);
		tileEntityTypeReducer.setRegistryName(new Identifier("lambdazation:reducer"));
	}

	public void registerBlockEntityTypes(RegistryEvent.Register<BlockEntityType<?>> e) {
		e.getRegistry().register(tileEntityTypeCrystallizer);
		e.getRegistry().register(tileEntityTypeTransformer);
		e.getRegistry().register(tileEntityTypeCharger);
		e.getRegistry().register(tileEntityTypeReducer);
	}

	public void finalizeBlockEntityTypes(RegistryEvent.Register<BlockEntityType<?>> e) {

	}
}
