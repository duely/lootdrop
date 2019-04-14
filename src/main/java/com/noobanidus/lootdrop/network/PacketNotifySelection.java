package com.noobanidus.lootdrop.network;

import com.noobanidus.lootdrop.util.LootSelector;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketNotifySelection implements IMessage {
    private ResourceLocation table;

    public PacketNotifySelection() {
    }

    public PacketNotifySelection(ResourceLocation table) {
        this.table = table;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        table = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, table.toString());
    }

    public ResourceLocation getTable() {
        return table;
    }

    public static class Handler implements IMessageHandler<PacketNotifySelection, IMessage> {
        @Override
        public IMessage onMessage(PacketNotifySelection message, MessageContext ctx) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> handleMessage(message, ctx));

            return null;
        }

        private void handleMessage(PacketNotifySelection message, MessageContext ctx) {
            LootSelector.setSelectedFor(ctx.getServerHandler().player, message.getTable());
        }
    }
}
