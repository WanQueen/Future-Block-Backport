package com.futureblockbackport;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.cricketcraft.chisel.api.ChiselTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import team.chisel.block.BlockCarvable;
import team.chisel.ctmlib.ISubmapManager;

public class BlockVariants extends BlockCarvable {

    private final String dir;
    private final Variant[] variants;

    public BlockVariants(String blockName, String dir, Variant[] variants, Material material, float hardness,
        float resistance, String tool, int harvestLevel, Block.SoundType sound) {
        super(material);
        this.dir = dir;
        this.variants = variants;

        setBlockName(blockName);
        setHardness(hardness);
        setResistance(resistance);
        setStepSound(sound);
        setHarvestLevel(tool, harvestLevel);
        setCreativeTab(ChiselTabs.tabModdedChiselBlocks);

        for (Variant variant : variants) {
            carverHelper.addVariation(
                variant.desc(dir),
                variant.meta,
                variant.path(dir),
                (Block) null,
                0,
                FutureBlockBackport.MODID,
                (ISubmapManager) null,
                variant.meta + 1);
        }
    }

    int size() {
        return variants.length;
    }

    int[] metas() {
        int[] metas = new int[variants.length];
        for (int i = 0; i < variants.length; i++) {
            metas[i] = variants[i].meta;
        }
        return metas;
    }

    String dir() {
        return dir;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        carverHelper.registerBlockIcons(FutureBlockBackport.MODID, this, register);
    }
}
