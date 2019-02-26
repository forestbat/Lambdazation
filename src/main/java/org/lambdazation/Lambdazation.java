package org.lambdazation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lambdazation.client.core.LambdazationClientProxy;
import org.lambdazation.common.core.LambdazationCommonProxy;
import org.lambdazation.common.core.LambdazationBiomes;
import org.lambdazation.common.core.LambdazationBlocks;
import org.lambdazation.common.core.LambdazationItemGroup;
import org.lambdazation.common.core.LambdazationItems;
import org.lambdazation.server.core.LambdazationServerProxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("lambdazation")
public final class Lambdazation {
	public final Logger logger;
	public final LambdazationCommonProxy lambdazationCommonProxy;
	public final LambdazationItemGroup lambdazationItemGroup;
	public final LambdazationBlocks lambdazationBlocks;
	public final LambdazationItems lambdazationItems;
	public final LambdazationBiomes lamblambdazationBiomes;

	public Lambdazation() {
		logger = LogManager.getLogger();
		lambdazationCommonProxy = DistExecutor.runForDist(() -> () -> new LambdazationClientProxy(this), () -> () -> new LambdazationServerProxy(this));
		lambdazationItemGroup = new LambdazationItemGroup(this);
		lambdazationBlocks = new LambdazationBlocks(this);
		lambdazationItems = new LambdazationItems(this);
		lamblambdazationBiomes = new LambdazationBiomes(this);

		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, lambdazationBlocks::registerBlocks);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, EventPriority.LOWEST, lambdazationBlocks::finalizeBlocks);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, lambdazationItems::registerItems);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, EventPriority.LOWEST, lambdazationItems::finalizeItems);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, lamblambdazationBiomes::registerBiomes);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, EventPriority.LOWEST, lamblambdazationBiomes::finalizeBiomes);
	}
}