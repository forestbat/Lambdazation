package org.lambdazation.client.core;

import net.minecraft.client.MinecraftClientClient;
import net.minecraft.client.gui.screen.Screen;
import org.lambdazation.Lambdazation;
import org.lambdazation.client.gui.GuiCalibrator;
import org.lambdazation.client.gui.GuiCharger;
import org.lambdazation.client.gui.GuiCrystallizer;
import org.lambdazation.client.gui.GuiLens;
import org.lambdazation.client.gui.GuiReducer;
import org.lambdazation.client.gui.GuiTransformer;
import org.lambdazation.common.inventory.ContainerCalibrator;
import org.lambdazation.common.inventory.ContainerCharger;
import org.lambdazation.common.inventory.ContainerCrystallizer;
import org.lambdazation.common.inventory.ContainerLens;
import org.lambdazation.common.inventory.ContainerReducer;
import org.lambdazation.common.inventory.ContainerTransformer;
import org.lambdazation.common.blockentity.BlockEntityCharger;
import org.lambdazation.common.blockentity.BlockEntityCrystallizer;
import org.lambdazation.common.blockentity.BlockEntityReducer;
import org.lambdazation.common.blockentity.BlockEntityTransformer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;

@OnlyIn(Dist.CLIENT)
public final class LambdazationGuiFactory {
	public final Lambdazation lambdazation;

	public LambdazationGuiFactory(Lambdazation lambdazation) {
		this.lambdazation = lambdazation;
	}

	public Screen openGui(FMLPlayMessages.OpenContainer msg) {
		if (msg.getId().equals(ContainerLens.GUI_ID)) {
			PlayerInventory playerInventory = MinecraftClientClient.getInstance().player.inventory;

			return new GuiLens(lambdazation, playerInventory);
		} else if (msg.getId().equals(ContainerCalibrator.GUI_ID)) {
			PlayerInventory playerInventory = MinecraftClient.getInstance().player.inventory;

			return new GuiCalibrator(lambdazation, playerInventory);
		} else if (msg.getId().equals(ContainerCrystallizer.GUI_ID)) {
			BlockPos blockPos = msg.getAdditionalData().readBlockPos();
			BlockEntity tileEntity = MinecraftClient.getInstance().world.getBlockEntity(blockPos);

			PlayerInventory playerInventory = MinecraftClient.getInstance().player.inventory;

			if (!(tileEntity instanceof BlockEntityCrystallizer))
				return null;
			BlockEntityCrystallizer crystallizerInventory = (BlockEntityCrystallizer) tileEntity;

			return new GuiCrystallizer(lambdazation, playerInventory, crystallizerInventory);
		} else if (msg.getId().equals(ContainerTransformer.GUI_ID)) {
			BlockPos blockPos = msg.getAdditionalData().readBlockPos();
			BlockEntity tileEntity = MinecraftClient.getInstance().world.getBlockEntity(blockPos);

			PlayerInventory playerInventory = MinecraftClient.getInstance().player.inventory;

			if (!(tileEntity instanceof BlockEntityTransformer))
				return null;
			BlockEntityTransformer transformerInventory = (BlockEntityTransformer) tileEntity;

			return new GuiTransformer(lambdazation, playerInventory, transformerInventory);
		} else if (msg.getId().equals(ContainerCharger.GUI_ID)) {
			BlockPos blockPos = msg.getAdditionalData().readBlockPos();
			BlockEntity tileEntity = MinecraftClient.getInstance().world.getBlockEntity(blockPos);

			PlayerInventory playerInventory = MinecraftClient.getInstance().player.inventory;

			if (!(tileEntity instanceof BlockEntityCharger))
				return null;
			BlockEntityCharger chargerInventory = (BlockEntityCharger) tileEntity;

			return new GuiCharger(lambdazation, playerInventory, chargerInventory);
		} else if (msg.getId().equals(ContainerReducer.GUI_ID)) {
			BlockPos blockPos = msg.getAdditionalData().readBlockPos();
			BlockEntity tileEntity = MinecraftClient.getInstance().world.getBlockEntity(blockPos);

			PlayerInventory playerInventory = MinecraftClient.getInstance().player.inventory;

			if (!(tileEntity instanceof BlockEntityReducer))
				return null;
			BlockEntityReducer reducerInventory = (BlockEntityReducer) tileEntity;

			return new GuiReducer(lambdazation, playerInventory, reducerInventory);
		} else
			return null;
	}
}
