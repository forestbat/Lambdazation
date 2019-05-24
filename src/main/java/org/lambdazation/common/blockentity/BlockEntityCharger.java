package org.lambdazation.common.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.BlockEntityFurnace;
import net.minecraft.tileentity.BlockEntityLockable;
import net.minecraft.util.Direction;
import net.minecraft.util.ITickable;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import java.util.Arrays;

import org.lambdazation.Lambdazation;
import org.lambdazation.common.block.BlockCharger;
import org.lambdazation.common.inventory.ContainerCharger;
import org.lambdazation.common.inventory.field.InventoryField;
import org.lambdazation.common.item.ItemLambdaCrystal;
import org.lambdazation.common.state.properties.SlotState;

public final class BlockEntityCharger extends BlockEntityLockable implements ISidedInventory, ITickable, IEnergyStorage {
	public static final int SLOT_INPUT_0 = 0;
	public static final int SLOT_FUEL_1 = 1;
	public static final int SLOT_OUTPUT_2 = 2;

	public static final int[] SLOTS_NONE = new int[] {};
	public static final int[] SLOTS_INPUT = new int[] { SLOT_INPUT_0, SLOT_FUEL_1 };
	public static final int[] SLOTS_OUTPUT = new int[] { SLOT_OUTPUT_2 };
	public static final int[] SLOTS_ALL = new int[] { SLOT_INPUT_0, SLOT_FUEL_1, SLOT_OUTPUT_2 };

	public final Lambdazation lambdazation;

	public final DefaultedList<ItemStack> inventoryContents;
	public int capacity;
	public int energy;
	public int chargeSpeed;
	public int burnSpeed;
	public int burnTime;

	private final LazyOptional<? extends IItemHandler>[] itemHandlers = SidedInvWrapper.create(this, Direction.DOWN,
		Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
	private final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(() -> this);

	public BlockEntityCharger(Lambdazation lambdazation) {
		super(lambdazation.lambdazationBlockEntityTypes.tileEntityTypeCharger);

		this.lambdazation = lambdazation;

		this.inventoryContents = DefaultedList.create(3, ItemStack.EMPTY);
		this.capacity = 8192;
		this.energy = 0;
		this.chargeSpeed = 1;
		this.burnSpeed = 1;
		this.burnTime = 0;
	}

	@Override
	public void fromTag(CompoundTag compound) {
		super.fromTag(compound);

		Inventories.fromTag(compound, inventoryContents);
		capacity = compound.getInt("capacity");
		energy = compound.getInt("energy");
		chargeSpeed = compound.getInt("chargeSpeed");
		burnSpeed = compound.getInt("burnSpeed");
		burnTime = compound.getInt("burnTime");
	}

	@Override
	public CompoundTag write(CompoundTag compound) {
		super.write(compound);

		Inventories.toTag(compound, inventoryContents);
		compound.putInt("capacity", capacity);
		compound.putInt("energy", energy);
		compound.putInt("chargeSpeed", chargeSpeed);
		compound.putInt("burnSpeed", burnSpeed);
		compound.putInt("burnTime", burnTime);

		return compound;
	}

	@Override
	public void remove() {
		super.remove();
		Arrays.stream(itemHandlers).forEach(LazyOptional::invalidate);
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean isEmpty() {
		return inventoryContents.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= 0 && index < getSizeInventory() ? inventoryContents.get(index) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return Inventories.getAndSplit(inventoryContents, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return Inventories.getAndRemove(inventoryContents, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index >= 0 && index < getSizeInventory())
			inventoryContents.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public void openInventory(PlayerEntity player) {

	}

	@Override
	public void closeInventory(PlayerEntity player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		switch (index) {
		case SLOT_INPUT_0:
			return stack.getItem().equals(lambdazation.lambdazationItems.itemLambdaCrystal);
		case SLOT_FUEL_1:
			return getBurnTime(stack) > 0;
		case SLOT_OUTPUT_2:
			return false;
		default:
			return false;
		}
	}

	@Override
	public int getField(int id) {
		if (id < 0 || id >= InventoryFieldCharger.values().length)
			return 0;

		int value = InventoryFieldCharger.values()[id].getField(this);
		return value;
	}

	@Override
	public void setField(int id, int value) {
		if (id < 0 || id >= InventoryFieldCharger.values().length)
			return;

		InventoryFieldCharger.values()[id].setField(this, value);
	}

	@Override
	public int getFieldCount() {
		return InventoryFieldCharger.values().length;
	}

	@Override
	public void clear() {
		inventoryContents.clear();
	}

	@Override
	public TextComponent getName() {
		return new TextComponent("Charger");
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
		return new ContainerCharger(lambdazation, playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return ContainerCharger.GUI_ID.toString();
	}

	@Override
	public void tick() {
		if (canBurn())
			burnFuel();
		if (canGenerate())
			generateEnergy();
		if (canCharge())
			charge();
		if (completed())
			move();
	}

	private boolean canBurn() {
		ItemStack fuelItemStack = inventoryContents.get(SLOT_FUEL_1);

		int fuelBurnTime = getBurnTime(fuelItemStack);

		return burnTime <= 0 && fuelBurnTime > 0;
	}

	private void burnFuel() {
		if (world.isClient)
			return;

		ItemStack fuelItemStack = inventoryContents.get(SLOT_FUEL_1);

		int fuelBurnTime = getBurnTime(fuelItemStack);

		if (fuelItemStack.hasContainerItem())
			fuelItemStack = fuelItemStack.getContainerItem();
		else
			fuelItemStack.shrink(1);
		burnTime += fuelBurnTime;

		inventoryContents.set(SLOT_FUEL_1, fuelItemStack);

		markDirty();
	}

	private boolean canGenerate() {
		return burnTime > 0;
	}

	private void generateEnergy() {
		int delta = Math.min(burnSpeed, burnTime);

		burnTime -= delta;
		energy += Math.min(capacity - energy, delta);

		markDirty();
	}

	private boolean canCharge() {
		ItemLambdaCrystal itemLambdaCrystal = lambdazation.lambdazationItems.itemLambdaCrystal;

		ItemStack inputItemStack = inventoryContents.get(SLOT_INPUT_0);

		if (!inputItemStack.isEmpty() && inputItemStack.getItem().equals(itemLambdaCrystal)) {
			int capacity = itemLambdaCrystal.getCapacity(inputItemStack).orElse(0);
			int energy = itemLambdaCrystal.getEnergy(inputItemStack).orElse(0);

			return energy < capacity && this.energy > 0;
		} else
			return false;
	}

	private void charge() {
		if (world.isClient)
			return;

		ItemLambdaCrystal itemLambdaCrystal = lambdazation.lambdazationItems.itemLambdaCrystal;

		ItemStack inputItemStack = inventoryContents.get(SLOT_INPUT_0);

		int capacity = itemLambdaCrystal.getCapacity(inputItemStack).orElse(0);
		int energy = itemLambdaCrystal.getEnergy(inputItemStack).orElse(0);
		int delta = Math.min(Math.min(chargeSpeed, this.energy), capacity - energy);

		itemLambdaCrystal.setEnergy(inputItemStack, energy + delta);
		this.energy -= delta;

		markDirty();
	}

	private boolean completed() {
		ItemLambdaCrystal itemLambdaCrystal = lambdazation.lambdazationItems.itemLambdaCrystal;

		ItemStack inputItemStack = inventoryContents.get(SLOT_INPUT_0);
		ItemStack outputItemStack = inventoryContents.get(SLOT_OUTPUT_2);

		if (!inputItemStack.isEmpty() && inputItemStack.getItem().equals(itemLambdaCrystal)
			&& outputItemStack.isEmpty()) {
			int capacity = itemLambdaCrystal.getCapacity(inputItemStack).orElse(0);
			int enegry = itemLambdaCrystal.getEnergy(inputItemStack).orElse(0);
			return enegry >= capacity;
		} else
			return false;
	}

	private void move() {
		if (world.isClient)
			return;

		ItemStack inputItemStack = inventoryContents.get(SLOT_INPUT_0);
		ItemStack resultItemStack = inputItemStack.split(1);

		inventoryContents.set(SLOT_INPUT_0, inputItemStack.isEmpty() ? ItemStack.EMPTY : inputItemStack);
		inventoryContents.set(SLOT_OUTPUT_2, resultItemStack);

		markDirty();
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		SlotState slotState = getBlockState().get(BlockCharger.FACING_PROPERTY_MAP.get(side));
		switch (slotState) {
		case NONE:
			return SLOTS_NONE;
		case INPUT:
			return SLOTS_INPUT;
		case OUTPUT:
			return SLOTS_OUTPUT;
		case ALL:
			return SLOTS_ALL;
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return true;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (!removed) {
			if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
				if (side != null) {
					switch (side) {
					case DOWN:
						return itemHandlers[0].cast();
					case UP:
						return itemHandlers[1].cast();
					case NORTH:
						return itemHandlers[2].cast();
					case SOUTH:
						return itemHandlers[3].cast();
					case WEST:
						return itemHandlers[4].cast();
					case EAST:
						return itemHandlers[5].cast();
					default:
						throw new IllegalStateException();
					}
				}
			} else if (cap == CapabilityEnergy.ENERGY) {
				return energyStorage.cast();
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!canReceive())
			return 0;

		int energyReceived = Math.min(capacity - energy, maxReceive);
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!canExtract())
			return 0;

		int energyExtracted = Math.min(energy, maxExtract);
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	public static int getBurnTime(ItemStack stack) {
		int forgeBurnTime = stack.getBurnTime();
		int vanillaBurnTime = BlockEntityFurnace.getBurnTimes().getOrDefault(stack.getItem(), 0);
		return ForgeEventFactory.getItemBurnTime(stack, forgeBurnTime < 0 ? vanillaBurnTime : forgeBurnTime);
	}

	public enum InventoryFieldCharger implements InventoryField<BlockEntityCharger> {
		CAPACITY {
			@Override
			public int getField(BlockEntityCharger inventory) {
				return inventory.capacity;
			}

			@Override
			public void setField(BlockEntityCharger inventory, int value) {
				inventory.capacity = value;
			}
		},
		ENERGY {
			@Override
			public int getField(BlockEntityCharger inventory) {
				return inventory.energy;
			}

			@Override
			public void setField(BlockEntityCharger inventory, int value) {
				inventory.energy = value;
			}
		},
		CHARGE_SPEED {
			@Override
			public int getField(BlockEntityCharger inventory) {
				return inventory.chargeSpeed;
			}

			@Override
			public void setField(BlockEntityCharger inventory, int value) {
				inventory.chargeSpeed = value;
			}
		},
		BURN_SPEED {
			@Override
			public int getField(BlockEntityCharger inventory) {
				return inventory.burnSpeed;
			}

			@Override
			public void setField(BlockEntityCharger inventory, int value) {
				inventory.burnSpeed = value;
			}
		},
		BURN_TIME {
			@Override
			public int getField(BlockEntityCharger inventory) {
				return inventory.burnTime;
			}

			@Override
			public void setField(BlockEntityCharger inventory, int value) {
				inventory.burnTime = value;
			}
		};

		@Override
		public int localFieldID() {
			return ordinal();
		}
	}
}
