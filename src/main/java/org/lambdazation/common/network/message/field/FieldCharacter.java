package org.lambdazation.common.network.message.field;

import java.util.Optional;

import org.lambdazation.common.network.message.Message;

import net.minecraft.network.PacketByteBuf;

public interface FieldCharacter<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, Character> {
	@Override
	default Optional<Character> initial() {
		return Optional.of('\0');
	}

	@Override
	default void encode(Character value, PacketByteBuf buf) {
		buf.writeChar(value);
	}

	@Override
	default Character decode(PacketByteBuf buf) {
		return buf.readChar();
	}
}
