package org.lambdazation.common.network.message.field;

import java.util.Optional;

import org.lambdazation.common.network.message.Message;
import org.lambdazation.common.network.message.field.feature.FeatureEndian;

import net.minecraft.network.PacketByteBuf;

public interface FieldShort<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, Short>, FeatureEndian {
	@Override
	default Optional<Short> initial() {
		return Optional.of((short) 0);
	}

	@Override
	default void encode(Short value, PacketByteBuf buf) {
		if (networkEndian())
			buf.writeShort(value);
		else
			buf.writeShortLE(value);
	}

	@Override
	default Short decode(PacketByteBuf buf) {
		if (networkEndian())
			return buf.readShort();
		else
			return buf.readShortLE();
	}
}
