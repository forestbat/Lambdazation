package org.lambdazation.common.core;

import net.minecraft.item.Rarity;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraftforge.event.RegistryEvent;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.item.*;

public final class LambdazationItems {
	public final Lambdazation lambdazation;

	public final ItemLambdaCrystal itemLambdaCrystal;
	public final ItemLens itemLens;
	public final ItemCalibrator itemCalibrator;
	public final ItemJavaEye itemJavaEye;
	public final ItemOOPSoul itemOOPSoul;

	public final BlockItem itemBlockLambdaOre;
	public final BlockItem itemBlockLambdaBlock;
	public final BlockItem itemBlockCrystallizer;
	public final BlockItem itemBlockTransformer;
	public final BlockItem itemBlockCharger;
	public final BlockItem itemBlockReducer;
	public final BlockItem itemBlockLambdaGrass;

	public LambdazationItems(Lambdazation lambdazation) {
		this.lambdazation = lambdazation;

		itemLambdaCrystal = new ItemLambdaCrystal(lambdazation, new Item.Settings()
			.durability(Integer.MAX_VALUE)
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.EPIC));
		itemLambdaCrystal.setRegistryName(new Identifier("lambdazation:lambda_crystal"));

		itemLens = new ItemLens(lambdazation, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup));
		itemLens.setRegistryName(new Identifier("lambdazation:lens"));
		itemCalibrator = new ItemCalibrator(lambdazation, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup));
		itemCalibrator.setRegistryName(new Identifier("lambdazation:calibrator"));
		itemJavaEye = new ItemJavaEye(lambdazation, new Item.Settings().itemGroup(lambdazation.lambdazationItemGroup));
		itemJavaEye.setRegistryName(new Identifier("lambdazation:java_eye"));
		itemOOPSoul = new ItemOOPSoul(lambdazation, new Item.Settings().itemGroup(lambdazation.lambdazationItemGroup));
		itemOOPSoul.setRegistryName(new Identifier("lambdazation:oop_soul"));

		itemBlockLambdaOre = new BlockItem(lambdazation.lambdazationBlocks.blockLambdaOre, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.EPIC));
		itemBlockLambdaOre.setRegistryName(new Identifier("lambdazation:lambda_ore"));
		itemBlockLambdaBlock = new BlockItem(lambdazation.lambdazationBlocks.blockLambdaBlock, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.EPIC));
		itemBlockLambdaBlock.setRegistryName(new Identifier("lambdazation:lambda_block"));
		itemBlockCrystallizer = new BlockItem(lambdazation.lambdazationBlocks.blockCrystallizer, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.COMMON));
		itemBlockCrystallizer.setRegistryName(new Identifier("lambdazation:crystallizer"));
		itemBlockTransformer = new BlockItem(lambdazation.lambdazationBlocks.blockTransformer, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.COMMON));
		itemBlockTransformer.setRegistryName(new Identifier("lambdazation:transformer"));
		itemBlockCharger = new BlockItem(lambdazation.lambdazationBlocks.blockCharger, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.COMMON));
		itemBlockCharger.setRegistryName(new Identifier("lambdazation:charger"));
		itemBlockReducer = new BlockItem(lambdazation.lambdazationBlocks.blockReducer, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.COMMON));
		itemBlockLambdaGrass = new BlockItem(lambdazation.lambdazationBlocks.blockLambdaGrass, new Item.Settings()
			.itemGroup(lambdazation.lambdazationItemGroup)
			.rarity(Rarity.COMMON));
		itemBlockReducer.setRegistryName(new Identifier("lambdazation:reducer"));
	}

	public void registerItems(RegistryEvent.Register<Item> e) {
		e.getRegistry().register(itemLambdaCrystal);
		e.getRegistry().register(itemLens);
		e.getRegistry().register(itemCalibrator);
		e.getRegistry().register(itemBlockLambdaOre);
		e.getRegistry().register(itemBlockLambdaBlock);
		e.getRegistry().register(itemBlockCrystallizer);
		e.getRegistry().register(itemBlockTransformer);
		e.getRegistry().register(itemBlockCharger);
		e.getRegistry().register(itemBlockReducer);
		e.getRegistry().register(itemOOPSoul);
	}

	public void finalizeItems(RegistryEvent.Register<Item> e) {

	}
}
