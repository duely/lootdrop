package com.noobanidus.lootdrop.proxy;

import com.noobanidus.lootdrop.LootDrop;
import com.noobanidus.lootdrop.gui.GuiHandler;
import com.noobanidus.lootdrop.network.PacketHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy implements ISidedProxy {
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.registerPackets();
        NetworkRegistry.INSTANCE.registerGuiHandler(LootDrop.instance, new GuiHandler());
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
        LootDrop.LOG.info("LootDrop: Load Complete.");
    }

    public void serverStarting(FMLServerStartingEvent event) {
    }

    public void serverStarted(FMLServerStartedEvent event) {
    }
}
