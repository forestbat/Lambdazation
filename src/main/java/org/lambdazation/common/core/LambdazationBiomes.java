package org.lambdazation.common.core;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.biome.BiomeMusicValley;

public final class LambdazationBiomes {
	public final Lambdazation lambdazation;

	public final BiomeMusicValley biomeMusicValley;
	public final SurfaceConfig lambda_grass;

	public LambdazationBiomes(Lambdazation lambdazation) {
		this.lambdazation = lambdazation;

		biomeMusicValley = new BiomeMusicValley(lambdazation);
		//biomeMusicValley.setRegistryName(new Identifier("lambdazation:music_valley"));
		lambda_grass=new TernarySurfaceConfig(
			lambdazation.lambdazationBlocks.blockLambdaGrass.getDefaultState(),
			lambdazation.lambdazationBlocks.blockLambdaGrass.getDefaultState(),
			lambdazation.lambdazationBlocks.blockLambdaGrass.getDefaultState());
	}

	public void registerBiomes() {
		Registry.register(Registry.BIOME,"lambdazation:music_valley",biomeMusicValley);
	}

	public void finalizeBiomes() {
		Biome.BIOMES.forEach(biome -> biome(
			GenerationStep.Feature.UNDERGROUND_ORES,
			Biome.configureFeature(
				Feature.ORE,
				new OreFeatureConfig(OreFeatureConfig.DEFAULT, lambdazation.lambdazationBlocks.blockLambdaOre.getDefau),
				Decorator.COUNT_RANGE,
				new RangeDecoratorConfig(1, 0, 0, 256))));
	}
}
