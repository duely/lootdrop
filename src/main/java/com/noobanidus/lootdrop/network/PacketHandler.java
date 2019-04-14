package com.noobanidus.lootdrop.network;

import com.noobanidus.lootdrop.LootDrop;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public final static SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(LootDrop.MODID);

    public static void registerPackets() {
        channel.registerMessage(PacketNotifySelection.Handler.class, PacketNotifySelection.class, 0, Side.SERVER);
        channel.registerMessage(PacketRequestChest.Handler.class, PacketRequestChest.class, 1, Side.SERVER);
    }
}
