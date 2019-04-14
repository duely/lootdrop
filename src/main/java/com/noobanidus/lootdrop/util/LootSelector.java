package com.noobanidus.lootdrop.util;

import com.noobanidus.lootdrop.network.PacketHandler;
import com.noobanidus.lootdrop.network.PacketNotifySelection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LootSelector {
    private static Map<UUID, ResourceLocation> lootMap = new HashMap<>();

    // Client only
    private static ResourceLocation clientSelected = null;

    @SideOnly(Side.CLIENT)
    public static ResourceLocation getSelectedFor() {
        return clientSelected;
    }

    @SideOnly(Side.CLIENT)
    public static void setSelectedFor(ResourceLocation loc) {
        clientSelected = loc;

        PacketNotifySelection packet = new PacketNotifySelection(clientSelected);
        PacketHandler.channel.sendToServer(packet);
    }

    // Server only
    public static ResourceLocation getSelectedFor(EntityPlayer player) {
        return getSelectedFor(player.getUniqueID());
    }

    public static ResourceLocation getSelectedFor(UUID uuid) {
        return lootMap.get(uuid);
    }

    public static void setSelectedFor(EntityPlayer player, ResourceLocation loc) {
        setSelectedFor(player.getUniqueID(), loc);
    }

    public static void setSelectedFor(UUID uuid, ResourceLocation loc) {
        lootMap.put(uuid, loc);
    }
}
