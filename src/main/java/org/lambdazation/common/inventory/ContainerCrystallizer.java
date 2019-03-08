package org.lambdazation.common.inventory;

import org.lambdazation.Lambdazation;
import org.lambdazation.common.tileentity.TileEntityCrystallizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class ContainerCrystallizer extends Container {
	public static final ResourceLocation GUI_ID = new ResourceLocation("lambdazation:crystallizer");

	public final Lambdazation lambdazation;

	public final InventoryPlayer inventoryPlayer;
	public final TileEntityCrystallizer tileEntityCrystallizer;

	public ContainerCrystallizer(Lambdazation lambdazation, InventoryPlayer inventoryPlayer,
		TileEntityCrystallizer tileEntityCrystallizer) {
		this.lambdazation = lambdazation;

		this.inventoryPlayer = inventoryPlayer;
		this.tileEntityCrystallizer = tileEntityCrystallizer;

		addSlot(new Slot(tileEntityCrystallizer, 0, 27, 47));
		addSlot(new Slot(tileEntityCrystallizer, 1, 76, 47));
		addSlot(new Slot(tileEntityCrystallizer, 2, 134, 47));

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int k = 0; k < 9; ++k)
			addSlot(new Slot(inventoryPlayer, k, 8 + k * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tileEntityCrystallizer.isUsableByPlayer(playerIn);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		// TODO NYI
		return ItemStack.EMPTY;
	}
}