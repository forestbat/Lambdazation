package org.lambdazation.common.core;

import net.minecraft.block.Material;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.block.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;

public final class LambdazationBlocks {
	public final Lambdazation lambdazation;

	public final BlockLambdaOre blockLambdaOre;
	public final BlockLambdaBlock blockLambdaBlock;
	public final BlockCrystallizer blockCrystallizer;
	public final BlockTransformer blockTransformer;
	public final BlockCharger blockCharger;
	public final BlockReducer blockReducer;
	public final BlockLambdaGrass blockLambdaGrass;

	public LambdazationBlocks(Lambdazation lambdazation) {
		this.lambdazation = lambdazation;

		blockLambdaOre = new BlockLambdaOre(lambdazation, Block.Settings
			.of(Material.STONE)
			.strength(3.0F, 3.0F));
		//blockLambdaOre.setRegistryName(new Identifier("lambdazation:lambda_ore"));
		blockLambdaBlock = new BlockLambdaBlock(lambdazation, Block.Settings
			.of(Material.STONE)
			.strength(3.0F, 3.0F));
		//blockLambdaBlock.setRegistryName(new Identifier("lambdazation:lambda_block"));
		blockCrystallizer = new BlockCrystallizer(lambdazation, Block.Settings
			.of(Material.STONE)
			.strength(3.5F, 3.5F));
		//blockCrystallizer.setRegistryName(new Identifier("lambdazation:crystallizer"));
		blockTransformer = new BlockTransformer(lambdazation, Block.Settings
			.of(Material.STONE)
			.strength(3.5F, 3.5F));
		//blockTransformer.setRegistryName(new Identifier("lambdazation:transformer"));
		blockCharger = new BlockCharger(lambdazation, Block.Settings
			.of(Material.STONE)
			.strength(3.5F, 3.5F));
		//blockCharger.setRegistryName(new Identifier("lambdazation:charger"));
		blockReducer = new BlockReducer(lambdazation, Block.Settings
			.of(Material.STONE)
			.strength(3.5F, 3.5F));
		//blockReducer.setRegistryName(new Identifier("lambdazation:reducer"));
		blockLambdaGrass=new BlockLambdaGrass(lambdazation, Block.Settings
			.of(Material.EARTH)
			.strength(0.5F, 0.5F));
		//blockLambdaGrass.setRegistryName(new Identifier("lambdazation:lambda_grass"));
	}

	public void registerBlocks() {

	}

	public void finalizeBlocks(RegistryEvent.Register<Block> e) {

	}
}
