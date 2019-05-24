package org.lambdazation.common.network.message.field;

import org.lambdazation.common.network.message.Message;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;

public interface FieldCompoundTag<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, CompoundTag> {
	@Override
	default void encode(CompoundTag value, PacketByteBuf buf) {
		buf.writeCompoundTag(value);
	}

	@Override
	default CompoundTag decode(PacketByteBuf buf) {
		return buf.readCompoundTag();
	}
}
