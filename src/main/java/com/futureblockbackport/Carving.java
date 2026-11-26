package com.futureblockbackport;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.carving.ICarvingRegistry;

public final class Carving {

    private Carving() {}

    public static void register() {
        ICarvingRegistry registry = CarvingUtils.getChiselRegistry();
        if (registry == null) {
            FutureBlockBackport.LOG.error("Chisel's carving registry is unavailable, no carving group was registered.");
            return;
        }

        for (Family family : ModBlocks.FAMILIES) {
            try {
                register(registry, family);
            } catch (Exception e) {
                FutureBlockBackport.LOG.error("Failed to register the '{}' carving group.", family.group, e);
            }
        }
    }

    private static void register(ICarvingRegistry registry, Family family) {
        family.variants.carverHelper.registerVariations(family.group);

        int order = family.variants.size() + 1;
        for (BlockPillarVariants pillar : family.pillars) {
            registry.addVariation(family.group, pillar, BlockPillarVariants.AXIS_Y, order++);
        }

        registry.setVariationSound(family.group, family.sound == null ? "dig.stone" : family.sound);

        Anchors.Base base = family.base == null ? null : family.base.resolve();
        if (base == null) {
            FutureBlockBackport.LOG.warn(
                "The base block of the '{}' carving group was not found, the group only holds the new variants.",
                family.group);
        } else if (registry.getVariation(base.block, base.meta) == null) {
            registry.addVariation(family.group, base.block, base.meta, 0);
        }

        FutureBlockBackport.LOG.info(
            "Registered the '{}' carving group with {} variations.",
            family.group,
            family.variants.size() + family.pillars.length);
    }
}
