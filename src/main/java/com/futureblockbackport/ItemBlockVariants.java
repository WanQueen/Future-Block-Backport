package com.futureblockbackport;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import team.chisel.item.ItemCarvable;

public class ItemBlockVariants extends ItemCarvable {

    public ItemBlockVariants(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + stack.getItemDamage();
    }
}
