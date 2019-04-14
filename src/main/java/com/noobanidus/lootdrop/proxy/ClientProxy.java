package com.noobanidus.lootdrop.proxy;

import com.noobanidus.lootdrop.client.Keybinds;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        Keybinds.initKeybinds();
    }
}
