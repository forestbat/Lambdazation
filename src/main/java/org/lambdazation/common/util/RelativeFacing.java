package org.lambdazation.common.util;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;

public final class RelativeFacing {
	private static final RelativeFacing[][] INDEXED_INSTANCES = ValueBuilder
		.build(new RelativeFacing[Direction.values().length][Rotation.values().length], builder -> {
			for (Direction facing : Direction.values())
			for (Rotation rotation : Rotation.values())
				builder[facing.ordinal()][rotation.ordinal()] = new RelativeFacing(facing, rotation);
		});

	private final Direction facing;
	private final Rotation rotation;
	private final Map<Direction, Direction> mapping;

	private RelativeFacing(Direction facing, Rotation rotation) {
		this.facing = facing;
		this.rotation = rotation;
		this.mapping = buildMapping(facing, rotation);
	}

	public Direction getFacing() {
		return facing;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public Direction transform(Direction originalFacing) {
		return mapping.get(originalFacing);
	}

	public static RelativeFacing of(Direction facing, Rotation rotation) {
		return INDEXED_INSTANCES[facing.ordinal()][rotation.ordinal()];
	}

	private static Direction rotateAround(Direction facing, Direction.Axis axis, Rotation rotation) {
		switch (rotation) {
		case NONE:
			return facing;
		case CLOCKWISE_90:
			return facing.rotateAround(axis);
		case CLOCKWISE_180:
			return facing.rotateAround(axis).rotateAround(axis);
		case COUNTERCLOCKWISE_90:
			return facing.rotateAround(axis).rotateAround(axis).rotateAround(axis);
		default:
			throw new IllegalStateException();
		}
	}

	private static Map<Direction, Direction> buildMapping(Direction facing, Rotation rotation) {
		Map<Direction, Direction> mapping = new EnumMap<>(Direction.class);
		for (Direction originalFacing : Direction.values()) {
			Direction rotatedFacing;
			switch (facing) {
			case DOWN:
				rotatedFacing = rotateAround(originalFacing, Direction.Axis.X, Rotation.COUNTERCLOCKWISE_90);
				break;
			case UP:
				rotatedFacing = rotateAround(originalFacing, Direction.Axis.X, Rotation.CLOCKWISE_90);
				break;
			case NORTH:
				rotatedFacing = rotateAround(originalFacing, Direction.Axis.Y, Rotation.CLOCKWISE_180);
				break;
			case SOUTH:
				rotatedFacing = rotateAround(originalFacing, Direction.Axis.Y, Rotation.NONE);
				break;
			case WEST:
				rotatedFacing = rotateAround(originalFacing, Direction.Axis.Y, Rotation.CLOCKWISE_90);
				break;
			case EAST:
				rotatedFacing = rotateAround(originalFacing, Direction.Axis.Y, Rotation.COUNTERCLOCKWISE_90);
				break;
			default:
				throw new IllegalStateException();
			}

			Direction resultFacing = rotateAround(rotatedFacing, facing.getAxis(), rotation);

			mapping.put(originalFacing, resultFacing);
		}
		return mapping;
	}
}
