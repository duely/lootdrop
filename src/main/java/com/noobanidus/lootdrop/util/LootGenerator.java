package com.noobanidus.lootdrop.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class LootGenerator {
    private static Random random = new Random();

    public static void generateLoot(World world, BlockPos pos, ResourceLocation loc) {
        IBlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        if (state.getBlock() == Blocks.CHEST) {
            if (te instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest) te;
                for (int i = 0; i < chest.getSizeInventory(); i++) {
                    chest.setInventorySlotContents(i, ItemStack.EMPTY);
                }
            }
            world.setBlockToAir(pos);
        }
        if (world.isAirBlock(pos) || state.getBlock().isReplaceable(world, pos)) {
            world.setBlockState(pos, Blocks.CHEST.getDefaultState());
            te = world.getTileEntity(pos);
            if (te instanceof TileEntityChest) {
                ((TileEntityChest) te).setLootTable(loc, random.nextLong());
                ((TileEntityChest) te).setCustomName(loc.getPath().replace("chests/", ""));
            }
        }
    }
}
