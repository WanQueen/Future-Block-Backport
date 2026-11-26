package com.futureblockbackport;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockPillar extends ItemBlock {

    private final Block block;

    public ItemBlockPillar(Block block) {
        super(block);
        this.block = block;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return BlockPillarVariants.AXIS_Y;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return block.getIcon(2, BlockPillarVariants.AXIS_Y);
    }
}
