package org.lambdazation.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;

public class ItemJavaEye extends Item {
	public final Lambdazation lambdazation;

	public ItemJavaEye(Lambdazation lambdazation, Settings properties) {
		super(properties);
		this.lambdazation = lambdazation;
	}

	@Override
	public TypedActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		MinecraftClient.getInstance().worldRenderer.loadEntityOutlineShader();//todo
		//todo
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}