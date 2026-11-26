package com.futureblockbackport;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public final class LegacyMapping {

    private static final String OLD_MODID = "chiselbackport";

    private LegacyMapping() {}

    static void remap(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
            String name = mapping.name;
            if (name == null || !name.startsWith(OLD_MODID + ":")) {
                continue;
            }

            String path = name.substring(OLD_MODID.length() + 1);
            if (mapping.type == GameRegistry.Type.BLOCK) {
                Block block = GameRegistry.findBlock(FutureBlockBackport.MODID, path);
                if (block != null) {
                    mapping.remap(block);
                    FutureBlockBackport.LOG.info("Remapped the legacy block '{}' to '{}'.", name, path);
                    continue;
                }
            } else if (mapping.type == GameRegistry.Type.ITEM) {
                Item item = GameRegistry.findItem(FutureBlockBackport.MODID, path);
                if (item != null) {
                    mapping.remap(item);
                    FutureBlockBackport.LOG.info("Remapped the legacy item '{}' to '{}'.", name, path);
                    continue;
                }
            }

            mapping.warn();
        }
    }
}
