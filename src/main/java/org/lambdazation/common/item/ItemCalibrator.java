package org.lambdazation.common.item;

import org.lambdazation.Lambdazation;
import org.lambdazation.common.inventory.ContainerCalibrator;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class ItemCalibrator extends Item implements IInteractionObject {
	public final Lambdazation lambdazation;

	public ItemCalibrator(Lambdazation lambdazation, Settings properties) {
		super(properties);

		this.lambdazation = lambdazation;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, EnumHand handIn) {
		playerIn.swingArm(handIn);

		if (worldIn.isClient)
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

		if (!(playerIn instanceof ServerPlayerEntity))
			return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
		ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity) playerIn;

		NetworkHooks.openGui(entityPlayerMP, this);

		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public TextComponent getCustomName() {
		return null;
	}

	@Override
	public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
		return new ContainerCalibrator(lambdazation, playerInventory);
	}

	@Override
	public String getGuiID() {
		return ContainerCalibrator.GUI_ID.toString();
	}
}
