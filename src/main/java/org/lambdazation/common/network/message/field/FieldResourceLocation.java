package org.lambdazation.common.network.message.field;

import org.lambdazation.common.network.message.Message;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface FieldIdentifier<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, Identifier> {
	@Override
	default void encode(Identifier value, PacketByteBuf buf) {
		buf.writeIdentifier(value);
	}

	@Override
	default Identifier decode(PacketByteBuf buf) {
		return buf.readIdentifier();
	}
}
