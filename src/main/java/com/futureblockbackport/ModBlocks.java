package com.futureblockbackport;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.registry.GameRegistry;

public final class ModBlocks {

    private static final Variant[] MOON_ROCK = { new Variant(0, "chiseled_moon_rock_brick"),
        new Variant(1, "moon_rock_brick"), new Variant(2, "cracked_moon_rock_brick"), new Variant(3, "polished_moon_rock"),
        new Variant(4, "cobbled_moon_rock"), new Variant(5, "dark_moon_rock"), new Variant(6, "chiseled_moon_stone"),
        new Variant(7, "moon_cobblestone"), new Variant(8, "moon_deepslate"), new Variant(9, "moon_stone"),
        new Variant(10, "moon_stone_bricks"), new Variant(11, "cracked_moon_stone_bricks"),
        new Variant(12, "moon_stone_dust"), new Variant(13, "polished_moon_stone") };

    private static final Variant[] BASALT = { new Variant(0, "moon_basalt_brick"),
        new Variant(1, "cracked_moon_basalt_brick") };

    private static final Variant[] VENUS_SURFACE = { new Variant(0, "soft_venus_rock"), new Variant(1, "venus_sand"),
        new Variant(2, "venus_sandstone"), new Variant(3, "venus_sandstone_bricks"),
        new Variant(4, "cracked_venus_sandstone_bricks") };

    private static final Variant[] VENUS_SUBSURFACE = { new Variant(0, "venus_dungeon_brick_block_red"),
        new Variant(1, "venus_dungeon_brick_block_yellow"), new Variant(2, "venus_stone"),
        new Variant(3, "venus_stone_bricks"), new Variant(4, "cracked_venus_stone_bricks"),
        new Variant(5, "chiseled_venus_stone"), new Variant(6, "polished_venus_stone"),
        new Variant(7, "venus_cobblestone") };

    private static final Variant[] MOON_SOIL = { new Variant(0, "moon_sand") };

    private static final Variant[] MARS_COBBLESTONE = { new Variant(0, "mars_cobblestone") };

    private static final Variant[] MARS_SURFACE = { new Variant(0, "mars_sand") };

    private static final Variant[] MARS_STONE = { new Variant(0, "mars_stone"), new Variant(1, "mars_stone_bricks"),
        new Variant(2, "cracked_mars_stone_bricks"), new Variant(3, "chiseled_mars_stone_bricks"),
        new Variant(4, "polished_mars_stone") };

    private static final Variant[] MERCURY = { new Variant(0, "mercury_stone"), new Variant(1, "mercury_cobblestone"),
        new Variant(2, "mercury_stone_bricks"), new Variant(3, "cracked_mercury_stone_bricks"),
        new Variant(4, "chiseled_mercury_stone"), new Variant(5, "polished_mercury_stone") };

    private static final String GALACTICRAFT = "GalacticraftCore";
    private static final String[] GC_MOON_NAMES = { "moonBlock", "tile.moonBlock" };
    private static final String[] GC_MOON_CLASSES = { "BlockBasicMoon" };
    private static final String[] GC_MARS_NAMES = { "mars", "marsBlock", "tile.mars" };
    private static final String[] GC_MARS_CLASSES = { "BlockBasicMars", "BlockMars" };
    private static final String[] GC_MARS_HINTS = { "mars" };

    private static final String GALAXYSPACE = "GalaxySpace";
    private static final String[] GS_VENUS_NAMES = { "venusblocks", "VenusBlocks" };
    private static final String[] GS_VENUS_HINTS = { "venus" };
    private static final String[] GS_VENUS_CLASSES = { "VenusBlocks" };
    private static final String[] GS_MERCURY_NAMES = { "mercuryblocks", "MercuryBlocks" };
    private static final String[] GS_MERCURY_HINTS = { "mercury" };
    private static final String[] GS_MERCURY_CLASSES = { "MercuryBlocks", "BlockMercury" };

    private static final String[] EMPTY = {};

    public static BlockVariants moonRock;
    public static BlockVariants basalt;
    public static BlockVariants venusSurface;
    public static BlockVariants venusSubsurface;
    public static BlockVariants moonSoil;
    public static BlockVariants marsCobblestone;
    public static BlockVariants marsSurface;
    public static BlockVariants marsStone;
    public static BlockVariants mercuryCore;

    public static BlockPillarVariants moonRockPillar;
    public static BlockPillarVariants moonStonePillar;
    public static BlockPillarVariants moonPillar;
    public static BlockPillarVariants venusPillar;
    public static BlockPillarVariants marsStonePillar;
    public static BlockPillarVariants marsPillar;
    public static BlockPillarVariants mercuryStonePillar;

    static final List<Family> FAMILIES = new ArrayList<Family>();

    private ModBlocks() {}

    public static void preInit() {
        moonRock = variants(
            "moonRockVariants",
            "moon_rock",
            MOON_ROCK,
            Material.rock,
            1.5F,
            6.0F,
            1,
            Block.soundTypeStone);
        register(moonRock, "moon_rock_variants", "moonRockVariants");

        basalt = variants(
            "basaltVariants",
            "basalt",
            BASALT,
            Material.rock,
            4.5F,
            60.0F,
            2,
            Block.soundTypeStone);
        register(basalt, "moon_basalt_variants", "basaltVariants");

        venusSurface = variants(
            "venusSurfaceVariants",
            "venus_surface_block",
            VENUS_SURFACE,
            Material.rock,
            3.0F,
            10.0F,
            2,
            Block.soundTypeStone);
        register(venusSurface, "venus_surface_variants", "venusSurfaceVariants");

        venusSubsurface = variants(
            "venusSubsurfaceVariants",
            "venus_subsurface_block",
            VENUS_SUBSURFACE,
            Material.rock,
            4.0F,
            40.0F,
            3,
            Block.soundTypeStone);
        register(venusSubsurface, "venus_subsurface_variants", "venusSubsurfaceVariants");

        moonSoil = shovel(
            "moonSoilVariants",
            "moon_soil",
            MOON_SOIL,
            Material.ground,
            0.6F,
            2.0F,
            0,
            Block.soundTypeGravel);
        register(moonSoil, "moon_soil_variants", "moonSoilVariants");

        marsCobblestone = variants(
            "marsCobblestoneVariants",
            "mars_cobblestone",
            MARS_COBBLESTONE,
            Material.rock,
            2.0F,
            10.0F,
            1,
            Block.soundTypeStone);
        register(marsCobblestone, "mars_cobblestone_variants", "marsCobblestoneVariants");

        marsSurface = variants(
            "marsSurfaceVariants",
            "mars_surface_rock",
            MARS_SURFACE,
            Material.rock,
            1.8F,
            8.0F,
            1,
            Block.soundTypeStone);
        register(marsSurface, "mars_surface_variants", "marsSurfaceVariants");

        marsStone = variants(
            "marsStoneVariants",
            "mars_stone",
            MARS_STONE,
            Material.rock,
            2.0F,
            10.0F,
            1,
            Block.soundTypeStone);
        register(marsStone, "mars_stone_variants", "marsStoneVariants");

        mercuryCore = variants(
            "mercuryCoreVariants",
            "mercury_core",
            MERCURY,
            Material.rock,
            3.0F,
            15.0F,
            2,
            Block.soundTypeStone);
        register(mercuryCore, "mercury_core_variants", "mercuryCoreVariants");

        moonRockPillar = pillar(
            "moonRockPillar",
            "moon_rock",
            "moon_rock_pillar_side",
            "moon_rock_pillar_top",
            1.5F,
            6.0F,
            1);
        register(moonRockPillar, "moon_rock_pillar", "moonRockPillar");

        moonStonePillar = pillar(
            "moonStonePillar",
            "moon_rock",
            "moon_stone_pillar_side",
            "moon_stone_pillar_end",
            1.5F,
            6.0F,
            1);
        register(moonStonePillar, "moon_stone_pillar", "moonStonePillar");

        moonPillar = pillar(
            "moonPillar",
            "moon_rock",
            "moon_pillar_side",
            "moon_pillar_end",
            1.5F,
            6.0F,
            1);
        register(moonPillar, "moon_pillar", "moonPillar");

        venusPillar = pillar(
            "venusPillar",
            "venus_subsurface_block",
            "venus_pillar_side",
            "venus_pillar_end",
            4.0F,
            40.0F,
            3);
        register(venusPillar, "venus_pillar", "venusPillar");

        marsStonePillar = pillar(
            "marsStonePillar",
            "mars_stone",
            "mars_stone_pillar_side",
            "mars_stone_pillar_end",
            2.0F,
            10.0F,
            1);
        register(marsStonePillar, "mars_stone_pillar", "marsStonePillar");

        marsPillar = pillar("marsPillar", "mars_stone", "mars_pillar_side", "mars_pillar_end", 2.0F, 10.0F, 1);
        register(marsPillar, "mars_pillar", "marsPillar");

        mercuryStonePillar = pillar(
            "mercuryStonePillar",
            "mercury_core",
            "mercury_stone_pillar_side",
            "mercury_stone_pillar_end",
            3.0F,
            15.0F,
            2);
        register(mercuryStonePillar, "mercury_stone_pillar", "mercuryStonePillar");

        FAMILIES.add(
            new Family(
                "moonRock",
                moonRock,
                Anchors.fixed(GALACTICRAFT, GC_MOON_NAMES, EMPTY, GC_MOON_CLASSES, 4),
                null,
                moonRockPillar,
                moonStonePillar,
                moonPillar));

        FAMILIES.add(
            new Family(
                "venusSurface",
                venusSurface,
                Anchors.fixed(GALAXYSPACE, GS_VENUS_NAMES, GS_VENUS_HINTS, GS_VENUS_CLASSES, 0),
                null));

        FAMILIES.add(
            new Family(
                "venusSubsurface",
                venusSubsurface,
                Anchors.fixed(GALAXYSPACE, GS_VENUS_NAMES, GS_VENUS_HINTS, GS_VENUS_CLASSES, 1),
                null,
                venusPillar));

        FAMILIES.add(
            new Family(
                "moonSoil",
                moonSoil,
                Anchors.fixed(GALACTICRAFT, GC_MOON_NAMES, EMPTY, GC_MOON_CLASSES, 3),
                "dig.gravel"));

        FAMILIES.add(
            new Family(
                "marsCobblestone",
                marsCobblestone,
                Anchors.fixed(GALACTICRAFT, GC_MARS_NAMES, GC_MARS_HINTS, GC_MARS_CLASSES, 4),
                null));

        FAMILIES.add(
            new Family(
                "marsSurface",
                marsSurface,
                Anchors.fixed(GALACTICRAFT, GC_MARS_NAMES, GC_MARS_HINTS, GC_MARS_CLASSES, 5),
                null));

        FAMILIES.add(
            new Family(
                "marsStone",
                marsStone,
                Anchors.fixed(GALACTICRAFT, GC_MARS_NAMES, GC_MARS_HINTS, GC_MARS_CLASSES, 9),
                null,
                marsStonePillar,
                marsPillar));

        FAMILIES.add(
            new Family(
                "mercuryCore",
                mercuryCore,
                Anchors.fixed(GALAXYSPACE, GS_MERCURY_NAMES, GS_MERCURY_HINTS, GS_MERCURY_CLASSES, 2),
                null,
                mercuryStonePillar));
    }

    private static BlockVariants variants(String blockName, String dir, Variant[] variants, Material material,
        float hardness, float resistance, int harvestLevel, Block.SoundType sound) {
        return new BlockVariants(
            blockName,
            dir,
            variants,
            material,
            hardness,
            resistance,
            "pickaxe",
            harvestLevel,
            sound);
    }

    private static BlockVariants shovel(String blockName, String dir, Variant[] variants, Material material,
        float hardness, float resistance, int harvestLevel, Block.SoundType sound) {
        return new BlockVariants(
            blockName,
            dir,
            variants,
            material,
            hardness,
            resistance,
            "shovel",
            harvestLevel,
            sound);
    }

    private static BlockPillarVariants pillar(String blockName, String dir, String side, String end, float hardness,
        float resistance, int harvestLevel) {
        return new BlockPillarVariants(blockName, dir, side, end, hardness, resistance, harvestLevel);
    }

    private static void register(BlockVariants block, String name, String unlocalized) {
        GameRegistry.registerBlock(block, ItemBlockVariants.class, name);
        block.setBlockName(unlocalized);
    }

    private static void register(BlockPillarVariants block, String name, String unlocalized) {
        GameRegistry.registerBlock(block, ItemBlockPillar.class, name);
        block.setBlockName(unlocalized);
    }
}
