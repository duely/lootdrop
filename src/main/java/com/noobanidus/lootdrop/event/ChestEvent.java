package com.noobanidus.lootdrop.event;

import com.noobanidus.lootdrop.LootDrop;
import com.noobanidus.lootdrop.util.LootGenerator;
import com.noobanidus.lootdrop.util.LootSelector;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = LootDrop.MODID)
@SuppressWarnings("unused")
public class ChestEvent {
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntityPlayer().isSneaking()) return;

        if (!event.getEntityPlayer().capabilities.isCreativeMode) return;

        if (event.getWorld().isRemote) return;

        if (event.getHand() != EnumHand.MAIN_HAND) return;

        ResourceLocation loc = LootSelector.getSelectedFor(event.getEntityPlayer());

        if (loc == null) return;

        World world = event.getWorld();
        IBlockState state = world.getBlockState(event.getPos());
        if (state.getBlock() == Blocks.CHEST) {
            LootGenerator.generateLoot(event.getWorld(), event.getPos(), loc);
        }
    }
}
