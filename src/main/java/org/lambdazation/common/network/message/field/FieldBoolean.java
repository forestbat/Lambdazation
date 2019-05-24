package org.lambdazation.common.network.message.field;

import java.util.Optional;

import org.lambdazation.common.network.message.Message;

import net.minecraft.network.PacketByteBuf;

public interface FieldBoolean<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, Boolean> {
	@Override
	default Optional<Boolean> initial() {
		return Optional.of(false);
	}

	@Override
	default void encode(Boolean value, PacketByteBuf buf) {
		buf.writeBoolean(value);
	}

	@Override
	default Boolean decode(PacketByteBuf buf) {
		return buf.readBoolean();
	}
}
