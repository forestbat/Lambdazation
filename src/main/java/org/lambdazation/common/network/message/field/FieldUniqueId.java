package org.lambdazation.common.network.message.field;

import java.util.UUID;

import org.lambdazation.common.network.message.Message;

import net.minecraft.network.PacketByteBuf;

public interface FieldUniqueId<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, UUID> {
	@Override
	default void encode(UUID value, PacketByteBuf buf) {
		buf.writeUniqueId(value);
	}

	@Override
	default UUID decode(PacketByteBuf buf) {
		return buf.readUniqueId();
	}
}
