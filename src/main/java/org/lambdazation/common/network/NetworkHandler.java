package org.lambdazation.common.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.GuiOpenS2CPacket;
import net.minecraft.container.Container;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.network.message.Message;
import org.lambdazation.common.network.message.MessagePing;
import org.lambdazation.common.network.message.handler.HandlerPing;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class NetworkHandler {
	public final Lambdazation lambdazation;

	public final HandlerPing handlerPing;
	//public final HandlerTest handlerTest;

	public NetworkHandler(Lambdazation lambdazation) {
		this.lambdazation = lambdazation;
		//this.simpleChannel = simpleChannel;

		this.handlerPing = new HandlerPing(lambdazation);
		//this.handlerTest = new HandlerTest(lambdazation);
	}

	public <M extends Message<M>> void sendMessage(M msg) {

	}

	public void registerMessage() {
		registerHandler(handlerPing, MessagePing.class, 0);
		//registerHandler(handlerTest, MessageTest.class, 1);
	}
	public Packet toPacket(){
		return new GuiOpenS2CPacket();
	}

	private <M extends Message<M>> void registerHandler(Message.Handler<M, ?> handler,
		Class<M> messageClass, int id) {
		new ClientConnection(NetworkSide.CLIENTBOUND).send(toPacket());
	}
	public static void openGui(ServerPlayerEntity player, LockableContainerBlockEntity containerSupplier, Consumer<PacketByteBuf> extraDataWriter)
	{
		if (player.world.isClient) return;
		Identifier id = new Identifier(containerSupplier.getName().getString());
		player.closeContainer();
		int openContainerId = player.getEntityId();
		PacketByteBuf extraData = new PacketByteBuf(Unpooled.buffer());
		extraDataWriter.accept(extraData);
		extraData.readerIndex(0);

		PacketByteBuf output = new PacketByteBuf(Unpooled.buffer());
		output.writeVarInt(extraData.readableBytes());
		output.writeBytes(extraData);

		if (output.readableBytes() > 32600 || output.readableBytes() < 1) {
			throw new IllegalArgumentException("Invalid PacketByteBuf for openGui, found "+ output.readableBytes()+ " bytes");
		}
		OpenContainer msg = new OpenContainer(id, openContainerId, output);
		//FMLNetworkConstants.playChannel.sendTo(msg, player.networkHandler, NetworkSide.CLIENTBOUND);

		Container c = containerSupplier.createMenu(0,player.inventory, player);
		player.openContainer(containerSupplier);
	}
	public static class OpenContainer
	{
		private final Identifier id;
		private final int windowId;
		private final PacketByteBuf additionalData;


		OpenContainer(Identifier id, int windowId, PacketByteBuf additionalData)
		{
			this.id = id;
			this.windowId = windowId;
			this.additionalData = additionalData;
		}

		public static void encode(OpenContainer msg, PacketByteBuf buf)
		{
			buf.writeIdentifier(msg.id);
			buf.writeVarInt(msg.windowId);
			buf.writeByteArray(msg.additionalData.readByteArray());
		}

		public static OpenContainer decode(PacketByteBuf buf)
		{
			return new OpenContainer(buf.readIdentifier(), buf.readVarInt(), new PacketByteBuf(Unpooled.wrappedBuffer(buf.readByteArray(32600))));
		}

		public static void handle(OpenContainer msg, Supplier<PacketContext> ctx)
		{
			ctx.get().getTaskQueue().executeFuture(ctx);
		}

		public final Identifier getId() {
			return this.id;
		}

		public int getWindowId() {
			return windowId;
		}

		public PacketByteBuf getAdditionalData() {
			return additionalData;
		}
	}
}

