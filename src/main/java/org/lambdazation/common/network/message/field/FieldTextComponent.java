package org.lambdazation.common.network.message.field;

import org.lambdazation.common.network.message.Message;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.text.TextComponent;

public interface FieldTextComponent<M extends Message<M>, F extends Message.Field<M, F, ?>>
	extends Message.Field<M, F, TextComponent> {
	@Override
	default void encode(TextComponent value, PacketByteBuf buf) {
		buf.writeTextComponent(value);
	}

	@Override
	default TextComponent decode(PacketByteBuf buf) {
		return buf.readTextComponent();
	}
}
