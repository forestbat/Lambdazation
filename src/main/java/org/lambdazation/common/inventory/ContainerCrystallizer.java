package org.lambdazation.common.inventory;

import org.lambdazation.Lambdazation;
import org.lambdazation.common.inventory.field.InventoryField;
import org.lambdazation.common.inventory.field.InventoryFieldCache;
import org.lambdazation.common.inventory.field.InventoryRef;
import org.lambdazation.common.blockentity.BlockEntityCrystallizer;
import org.lambdazation.common.util.GeneralizedEnum;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class ContainerCrystallizer extends Container {
	public static final Identifier GUI_ID = new Identifier("lambdazation:crystallizer");

	public final Lambdazation lambdazation;

	public final PlayerInventory playerInventory;
	public final BlockEntityCrystallizer crystallizerInventory;
	public final InventoryFieldCache<ContainerCrystallizer> inventoryFieldCache;

	public ContainerCrystallizer(Lambdazation lambdazation, PlayerInventory playerInventory,
		BlockEntityCrystallizer crystallizerInventory) {
		this.lambdazation = lambdazation;

		this.playerInventory = playerInventory;
		this.crystallizerInventory = crystallizerInventory;

		this.inventoryFieldCache = InventoryFieldCache
			.builder(this, listeners, InventoryRefCrystallizer.METADATA)
			.build();

		addSlot(new SlotInput(crystallizerInventory, 0, 27, 47));
		addSlot(new SlotInput(crystallizerInventory, 1, 76, 47));
		addSlot(new SlotOutput(crystallizerInventory, 2, 134, 47));

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int k = 0; k < 9; ++k)
			addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
	}

	public <T extends IInventory> int lookupInventoryField(InventoryRefCrystallizer<T> inventoryRef,
		InventoryField<T> inventoryField) {
		return inventoryFieldCache.lookup(inventoryRef, inventoryField);
	}

	@Override
	public void addListener(ContainerListener listener) {
		super.addListener(listener);

		InventoryRefCrystallizer.METADATA.values()
			.map(inventoryRef -> inventoryRef.getInventory(this))
			.forEach(inventory -> listener.sendAllWindowProperties(this, inventory));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		inventoryFieldCache.refresh();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void updateProgressBar(int id, int data) {
		inventoryFieldCache.update(id, data);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return crystallizerInventory.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack affectedStack = ItemStack.EMPTY;

		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack currentStack = slot.getStack();
			affectedStack = currentStack.copy();

			if (index >= 0 && index < 3) {
				if (!this.mergeItemStack(currentStack, 3, 39, false))
					return ItemStack.EMPTY;
			} else if (index >= 3 && index < 39) {
				if (!this.mergeItemStack(currentStack, 0, 3, false))
					return ItemStack.EMPTY;
			} else
				return ItemStack.EMPTY;

			if (currentStack.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (currentStack.getCount() == affectedStack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(playerIn, currentStack);
		}

		return affectedStack;
	}

	public final class SlotInput extends Slot {
		public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return inventory.isItemValidForSlot(getSlotIndex(), stack);
		}
	}

	public final class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return inventory.isItemValidForSlot(getSlotIndex(), stack);
		}
	}

	public static abstract class InventoryRefCrystallizer<T extends IInventory>
		extends GeneralizedEnum<InventoryRefCrystallizer<?>> implements InventoryRef<ContainerCrystallizer, T> {
		public static final InventoryRefCrystallizer<PlayerInventory> PLAYER;
		public static final InventoryRefCrystallizer<BlockEntityCrystallizer> CRYSTALLIZER;

		public static final GeneralizedEnum.Metadata<InventoryRefCrystallizer<?>> METADATA;

		static {
			GeneralizedEnum.Metadata.Builder<InventoryRefCrystallizer<?>> builder = GeneralizedEnum.Metadata.builder();

			class Player extends InventoryRefCrystallizer<PlayerInventory> {
				Player(String name, int ordinal) {
					super(name, ordinal);
				}

				@Override
				public PlayerInventory getInventory(ContainerCrystallizer container) {
					return container.playerInventory;
				}
			}
			PLAYER = builder.withValue("PLAYER", Player::new);

			class Crystallizer extends InventoryRefCrystallizer<BlockEntityCrystallizer> {
				Crystallizer(String name, int ordinal) {
					super(name, ordinal);
				}

				@Override
				public BlockEntityCrystallizer getInventory(ContainerCrystallizer container) {
					return container.crystallizerInventory;
				}
			}
			CRYSTALLIZER = builder.withValue("CRYSTALLIZER", Crystallizer::new);

			METADATA = builder.build();
		}

		InventoryRefCrystallizer(String name, int ordinal) {
			super(name, ordinal);
		}

		@Override
		public int inventoryID() {
			return ordinal();
		}
	}
}
