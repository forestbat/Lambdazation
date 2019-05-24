package org.lambdazation.common.network.message.field;

import org.lambdazation.common.network.message.Message;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public interface FieldBlockPos<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, BlockPos> {
	@Override
	default void encode(BlockPos value, PacketByteBuf buf) {
		buf.writeBlockPos(value);
	}

	@Override
	default BlockPos decode(PacketByteBuf buf) {
		return buf.readBlockPos();
	}
}
