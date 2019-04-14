package com.noobanidus.lootdrop.network;

import com.noobanidus.lootdrop.util.LootGenerator;
import com.noobanidus.lootdrop.util.LootSelector;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestChest implements IMessage {

    public PacketRequestChest() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<PacketRequestChest, IMessage> {
        @Override
        public IMessage onMessage(PacketRequestChest message, MessageContext ctx) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> handleMessage(message, ctx));

            return null;
        }

        private void handleMessage(PacketRequestChest message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            if (player.capabilities.isCreativeMode) {
                ResourceLocation loc = LootSelector.getSelectedFor(player);
                if (loc != null) {
                    EnumFacing facing = player.getHorizontalFacing();
                    BlockPos pos = player.getPosition().offset(facing);
                    World world = player.getServerWorld();
                    LootGenerator.generateLoot(world, pos, loc);
                }
            }
        }
    }
}
