package com.noobanidus.lootdrop.client;

import com.noobanidus.lootdrop.LootDrop;
import com.noobanidus.lootdrop.network.PacketHandler;
import com.noobanidus.lootdrop.network.PacketRequestChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = LootDrop.MODID)
public class Keybinds {
    public static KeyBinding LOOT_DROP;
    public static KeyBinding LOOT_SELECT;

    public static void initKeybinds() {
        LOOT_DROP = new KeyBinding("lootdrop.create_chest", 0, "lootdrop");
        ClientRegistry.registerKeyBinding(LOOT_DROP);
        LOOT_SELECT = new KeyBinding("lootdrop.select_loot", 0, "lootdrop");
        ClientRegistry.registerKeyBinding(LOOT_SELECT);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.capabilities.isCreativeMode) {
            if (mc.inGameHasFocus) {
                if (LOOT_SELECT.isKeyDown()) {
                    mc.player.openGui(LootDrop.instance, 0, mc.world, 0, 0, 0);
                } else if (LOOT_DROP.isKeyDown()) {
                    PacketRequestChest packet = new PacketRequestChest();
                    PacketHandler.channel.sendToServer(packet);
                }
            }
        }
    }
}
