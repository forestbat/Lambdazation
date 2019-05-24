package org.lambdazation.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemPropertyGetter;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tileentity.BlockEntity;
import net.minecraft.util.BlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.blockentity.BlockEntityTransformer;
import org.lambdazation.common.state.properties.SlotState;
import org.lambdazation.common.util.RelativeFacing;
import org.lambdazation.common.util.ValueBuilder;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class BlockTransformer extends Block {
	public static final EnumProperty<SlotState> DOWN = SlotState.SLOT_STATE_DOWN;
	public static final EnumProperty<SlotState> UP = SlotState.SLOT_STATE_UP;
	public static final EnumProperty<SlotState> NORTH = SlotState.SLOT_STATE_NORTH;
	public static final EnumProperty<SlotState> SOUTH = SlotState.SLOT_STATE_SOUTH;
	public static final EnumProperty<SlotState> WEST = SlotState.SLOT_STATE_WEST;
	public static final EnumProperty<SlotState> EAST = SlotState.SLOT_STATE_EAST;
	public static final Map<Direction, EnumProperty<SlotState>> FACING_PROPERTY_MAP = ValueBuilder
		.<Map<Direction, EnumProperty<SlotState>>> build(new EnumMap<>(Direction.class), builder -> {
			builder.put(Direction.DOWN, DOWN);
			builder.put(Direction.UP, UP);
			builder.put(Direction.NORTH, NORTH);
			builder.put(Direction.SOUTH, SOUTH);
			builder.put(Direction.WEST, WEST);
			builder.put(Direction.EAST, EAST);
		}, Collections::unmodifiableMap);

	public final Lambdazation lambdazation;

	public BlockTransformer(Lambdazation lambdazation, Block.Settings properties) {
		super(properties);

		this.lambdazation = lambdazation;

		setDefaultState(stateFactory.getDefaultState()
			.with(DOWN, SlotState.OUTPUT)
			.with(UP, SlotState.OUTPUT)
			.with(NORTH, SlotState.INPUT)
			.with(SOUTH, SlotState.INPUT)
			.with(WEST, SlotState.INPUT)
			.with(EAST, SlotState.INPUT));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof BlockEntityTransformer) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (BlockEntityTransformer) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, EnumHand hand,
		Direction side, float hitX, float hitY, float hitZ) {
		if (worldIn.isClient)
			return true;

		if (!(player instanceof ServerPlayerEntity))
			return false;
		ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity) player;

		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (!(tileEntity instanceof BlockEntityTransformer))
			return false;
		BlockEntityTransformer blockEntityTransformer = (BlockEntityTransformer) tileEntity;

		NetworkHooks.openGui(entityPlayerMP, blockEntityTransformer, pos);

		return true;
	}

	@Override
	public BlockEntity createNewBlockEntity(BlockReader worldIn) {
		return new BlockEntityTransformer(lambdazation);
	}

	@Override
	public BlockState getStateForPlacement(ItemPlacementContext context) {
		Direction placementFacing = context.getNearestLookingDirection();
		Direction placementHorizontalFacing = context.getPlacementHorizontalFacing();
		RelativeFacing relativeFacing = RelativeFacing.of(placementFacing.getOpposite(),
			placementFacing.getAxis().equals(Direction.Axis.Y)
				? Rotation.values()[placementHorizontalFacing.getOpposite().getHorizontalIndex()]
				: Rotation.NONE);

		BlockState resultState = getDefaultState();
		for (Direction facing : Direction.values())
			resultState = resultState.with(FACING_PROPERTY_MAP.get(relativeFacing.transform(facing)),
				getDefaultState().get(FACING_PROPERTY_MAP.get(facing)));
		return resultState;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		BlockState resultState = state;
		for (Direction facing : Direction.values())
			resultState = resultState.with(FACING_PROPERTY_MAP.get(rot.rotate(facing)),
				state.get(FACING_PROPERTY_MAP.get(facing)));
		return resultState;
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		BlockState resultState = state;
		for (Direction facing : Direction.values())
			resultState = resultState.with(FACING_PROPERTY_MAP.get(mirrorIn.mirror(facing)),
				state.get(FACING_PROPERTY_MAP.get(facing)));
		return resultState;
	}

	@Override
	protected void fillStateContainer(StateFactory.Builder<Block, BlockState> builder) {
		FACING_PROPERTY_MAP.values().forEach(builder::add);
	}
}
