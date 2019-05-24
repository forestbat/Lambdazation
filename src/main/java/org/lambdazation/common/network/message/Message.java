package org.lambdazation.common.network.message;

import java.util.Optional;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.util.PacketByteBuf;
import org.lambdazation.common.util.EnumValue;
import org.lambdazation.common.util.GeneralizedBuilder;
import org.lambdazation.common.util.EnumValue.EnumObject;

public interface Message<M extends Message<M>> {
	default <F extends Field<M, F, T>, T> T get(F field) {
		return field.get(concrete());
	}

	M concrete();

	interface Handler<M extends Message<M>, F extends Field<M, F, ?>> {
		default void encode(M msg, PacketByteBuf buf) {
			metadata().values().forEachOrdered(field -> field.write(msg, buf));
		}

		default M decode(PacketByteBuf buf) {
			Builder<M, F, ?> builder = builder();
			metadata().values().forEachOrdered(field -> field.read(builder, buf));
			return builder.build();
		}

		default void consume(M msg, Supplier<PacketContext> ctx) {
			handle(msg, ctx.get());
		}

		EnumObject<F> metadata();

		Builder<M, F, ?> builder();

		void handle(M msg, PacketContext ctx);
	}

	interface Builder<M extends Message<M>, F extends Field<M, F, ?>, B extends Builder<M, F, B>>
		extends GeneralizedBuilder<B, M> {
		<T> B with(Field<M, F, T> field, T value);
	}

	interface Field<M extends Message<M>, F extends Field<M, F, ?>, T> extends EnumValue<F> {
		default <B extends Builder<M, F, B>> B set(B builder, T value) {
			return builder.with(this, value);
		}

		default Optional<T> initial() {
			return Optional.empty();
		}

		default void write(M msg, PacketByteBuf buf) {
			encode(get(msg), buf);
		}

		default void read(Builder<M, F, ?> builder, PacketByteBuf buf) {
			builder.with(this, decode(buf));
		}

		void encode(T value, PacketByteBuf buf);

		T decode(PacketByteBuf buf);

		T get(M msg);
	}
}
