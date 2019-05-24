package org.lambdazation.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.blockentity.BlockEntityCrystallizer;
import org.lambdazation.common.state.properties.SlotState;
import org.lambdazation.common.util.RelativeFacing;
import org.lambdazation.common.util.ValueBuilder;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class BlockCrystallizer extends Block {
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

	public BlockCrystallizer(Lambdazation lambdazation, Settings properties) {
		super(properties);

		this.lambdazation = lambdazation;

		setDefaultState(getStateFactory().getDefaultState()
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
	public void onBlockRemoved(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity BlockEntity = worldIn.getBlockEntity(pos);
			if (BlockEntity instanceof BlockEntityCrystallizer) {
				dropStacks(state,worldIn, pos, BlockEntity);
				worldIn.updateHorizontalAdjacent(pos, this);
			}

			super.onBlockRemoved(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public boolean activate(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand,
		BlockHitResult result) {
		if (worldIn.isClient)
			return true;

		if (!(player instanceof ServerPlayerEntity))
			return false;
		ServerPlayerEntity ServerPlayerEntity = (ServerPlayerEntity) player;

		BlockEntity BlockEntity = worldIn.getBlockEntity(pos);
		if (!(BlockEntity instanceof BlockEntityCrystallizer))
			return false;
		BlockEntityCrystallizer blockEntityCrystallizer = (BlockEntityCrystallizer) BlockEntity;

		//NetworkHooks.openGui(ServerPlayerEntity, blockEntityCrystallizer, pos);

		return true;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		Direction placementFacing = context.getPlayerFacing();
		Direction placementHorizontalFacing = context.getPlayerHorizontalFacing();
		RelativeFacing relativeFacing = RelativeFacing.of(placementFacing.getOpposite(),
			placementFacing.getAxis().equals(Direction.Axis.Y)
				? BlockRotation.values()[placementHorizontalFacing.getOpposite().getHorizontal()]
				: BlockRotation.NONE);

		BlockState resultState = getDefaultState();
		for (Direction facing : Direction.values())
			resultState = resultState.with(FACING_PROPERTY_MAP.get(relativeFacing.transform(facing)),
				getDefaultState().get(FACING_PROPERTY_MAP.get(facing)));
		return resultState;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rot) {
		BlockState resultState = state;
		for (Direction facing : Direction.values())
			resultState = resultState.with(FACING_PROPERTY_MAP.get(rot.rotate(facing)),
				state.get(FACING_PROPERTY_MAP.get(facing)));
		return resultState;
	}


	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		FACING_PROPERTY_MAP.values().forEach(builder::add);
	}
}
