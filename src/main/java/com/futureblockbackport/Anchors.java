package com.futureblockbackport;

import java.util.Locale;

import net.minecraft.block.Block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public final class Anchors {

    public static final class Base {

        final Block block;
        final int meta;

        Base(Block block, int meta) {
            this.block = block;
            this.meta = meta;
        }
    }

    static final class Spec {

        private final String modid;
        private final String[] names;
        private final String[] keyHints;
        private final String[] classHints;
        private final int meta;

        Spec(String modid, String[] names, String[] keyHints, String[] classHints, int meta) {
            this.modid = modid;
            this.names = names;
            this.keyHints = keyHints;
            this.classHints = classHints;
            this.meta = meta;
        }

        Base resolve() {
            Block block = find(modid, names, keyHints, classHints);
            if (block == null) {
                return null;
            }
            FutureBlockBackport.LOG.info(
                "Using {} meta {} as the base block of a carving group.",
                Block.blockRegistry.getNameForObject(block),
                meta);
            return new Base(block, meta);
        }
    }

    private Anchors() {}

    static Spec fixed(String modid, String[] names, String[] keyHints, String[] classHints, int meta) {
        return new Spec(modid, names, keyHints, classHints, meta);
    }

    private static Block find(String modid, String[] names, String[] keyHints, String[] classHints) {
        if (!Loader.isModLoaded(modid)) {
            return null;
        }

        for (String name : names) {
            Block block = GameRegistry.findBlock(modid, name);
            if (block != null) {
                return block;
            }
        }

        String prefix = modid.toLowerCase(Locale.ROOT) + ":";
        for (Object key : Block.blockRegistry.getKeys()) {
            String id = String.valueOf(key)
                .toLowerCase(Locale.ROOT);
            if (!id.startsWith(prefix)) {
                continue;
            }
            for (String hint : keyHints) {
                if (id.contains(hint.toLowerCase(Locale.ROOT))) {
                    Object candidate = Block.blockRegistry.getObject(key);
                    if (candidate instanceof Block) {
                        return (Block) candidate;
                    }
                }
            }
        }

        for (Object key : Block.blockRegistry.getKeys()) {
            Object candidate = Block.blockRegistry.getObject(key);
            if (!(candidate instanceof Block)) {
                continue;
            }
            String className = candidate.getClass()
                .getName();
            for (String hint : classHints) {
                if (className.endsWith("." + hint)) {
                    return (Block) candidate;
                }
            }
        }

        return null;
    }
}
