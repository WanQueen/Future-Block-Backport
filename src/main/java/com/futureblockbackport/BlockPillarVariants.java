package com.futureblockbackport;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.cricketcraft.chisel.api.ChiselTabs;
import com.cricketcraft.chisel.api.ICarvable;
import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.carving.IVariationInfo;
import com.cricketcraft.chisel.api.carving.VariationInfoBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import team.chisel.ctmlib.ISubmapManager;

public class BlockPillarVariants extends Block implements ICarvable {

    public static final int AXIS_Y = 0;
    public static final int AXIS_X = 4;
    public static final int AXIS_Z = 8;

    private final String dir;
    private final String sideTexture;
    private final String endTexture;
    private final String descKey;
    private final IVariationInfo info;

    @SideOnly(Side.CLIENT)
    private IIcon iconSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    public BlockPillarVariants(String blockName, String dir, String sideTexture, String endTexture, float hardness,
        float resistance, int harvestLevel) {
        super(Material.rock);
        this.dir = dir;
        this.sideTexture = sideTexture;
        this.endTexture = endTexture;
        this.descKey = FutureBlockBackport.MODID + "." + dir + "." + sideTexture + ".desc";
        this.info = new PillarInfo();

        setBlockName(blockName);
        setHardness(hardness);
        setResistance(resistance);
        setStepSound(Block.soundTypeStone);
        setHarvestLevel("pickaxe", harvestLevel);
        setCreativeTab(ChiselTabs.tabModdedChiselBlocks);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        iconSide = register.registerIcon(FutureBlockBackport.MODID + ":" + dir + "/" + sideTexture);
        iconTop = register.registerIcon(FutureBlockBackport.MODID + ":" + dir + "/" + endTexture);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (meta & (AXIS_X | AXIS_Z)) {
            case AXIS_X:
                return side == ForgeDirection.EAST.ordinal() || side == ForgeDirection.WEST.ordinal() ? iconTop
                    : iconSide;
            case AXIS_Z:
                return side == ForgeDirection.NORTH.ordinal() || side == ForgeDirection.SOUTH.ordinal() ? iconTop
                    : iconSide;
            default:
                return side == ForgeDirection.DOWN.ordinal() || side == ForgeDirection.UP.ordinal() ? iconTop
                    : iconSide;
        }
    }

    @Override
    public IVariationInfo getManager(int meta) {
        return info;
    }

    @Override
    public IVariationInfo getManager(IBlockAccess world, int x, int y, int z, int side) {
        return info;
    }

    private final class PillarInfo extends VariationInfoBase {

        PillarInfo() {
            super(
                CarvingUtils.getDefaultVariationFor(BlockPillarVariants.this, AXIS_Y, 1),
                descKey,
                (ISubmapManager) null);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IIcon getIcon(int side, int meta) {
            return BlockPillarVariants.this.getIcon(side, meta);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
            return BlockPillarVariants.this.getIcon(side, world.getBlockMetadata(x, y, z));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        world.setBlockMetadataWithNotify(x, y, z, axisForPlacer(placer), 2);
    }

    private static int axisForPlacer(EntityLivingBase placer) {
        if (placer == null) {
            return AXIS_Y;
        }
        if (placer.rotationPitch > 45.0F || placer.rotationPitch < -45.0F) {
            return AXIS_Y;
        }
        int facing = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        return facing == 0 || facing == 2 ? AXIS_Z : AXIS_X;
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        int meta = world.getBlockMetadata(x, y, z);
        int next = meta == AXIS_Y ? AXIS_X : meta == AXIS_X ? AXIS_Z : AXIS_Y;
        world.setBlockMetadataWithNotify(x, y, z, next, 2);
        return true;
    }

    @Override
    public int damageDropped(int meta) {
        return AXIS_Y;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, AXIS_Y));
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return AXIS_Y;
    }
}
