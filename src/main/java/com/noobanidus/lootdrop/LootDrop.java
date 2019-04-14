package com.noobanidus.lootdrop;

import com.noobanidus.lootdrop.proxy.ISidedProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = LootDrop.MODID, name = LootDrop.MODNAME, version = LootDrop.VERSION)
@SuppressWarnings("WeakerAccess")
public class LootDrop {
    public static final String MODID = "lootdrop";
    public static final String MODNAME = "LootDrop";
    public static final String VERSION = "GRADLE:VERSION";

    @SuppressWarnings("unused")
    public final static Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(modId = MODID, clientSide = "com.noobanidus.lootdrop.proxy.ClientProxy", serverSide = "com.noobanidus.lootdrop.proxy.CommonProxy")
    public static ISidedProxy proxy;

    @Mod.Instance(LootDrop.MODID)
    public static LootDrop instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }

}
