package com.futureblockbackport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.carving.CarvableHelper;
import com.cricketcraft.chisel.api.carving.ICarvingGroup;
import com.cricketcraft.chisel.api.carving.ICarvingRegistry;
import com.cricketcraft.chisel.api.carving.ICarvingVariation;

public final class BasaltCarving {

    private static final String[] PACK_GROUP_NAMES = { "basalts", "basalt" };

    private static final String FALLBACK_GROUP = "basalt";

    private static final String FALLBACK_ORE_NAME = "stoneBasalt";

    private static String activeGroup = null;

    private static boolean ownsGroup = false;

    private BasaltCarving() {}

    public static void registerVisuals() {
        CarvableHelper helper = ModBlocks.basalt.carverHelper;
        boolean previous = helper.forbidChiseling;
        helper.forbidChiseling = true;
        try {
            helper.registerVariations(PACK_GROUP_NAMES[0]);
        } finally {
            helper.forbidChiseling = previous;
        }
    }

    public static void registerGroup(boolean createFallback) {
        ICarvingRegistry registry = CarvingUtils.getChiselRegistry();
        if (registry == null) {
            FutureBlockBackport.LOG.error("Chisel's carving registry is unavailable, the basalt bricks cannot be chiseled.");
            return;
        }

        try {
            ICarvingGroup packGroup = findPackGroup(registry);
            if (packGroup != null) {
                String groupName = packGroup.getName();
                if (ownsGroup && activeGroup != null && !activeGroup.equals(groupName)) {
                    if (registry.getGroup(activeGroup) != null) {
                        registry.removeGroup(activeGroup);
                    }
                    activeGroup = null;
                    ownsGroup = false;
                }
                ensureBricksAtTail(registry, groupName, "pack's");
                ownsGroup = false;
                return;
            }

            if (ownsGroup && activeGroup != null && registry.getGroup(activeGroup) != null) {
                return;
            }

            if (!createFallback) {
                return;
            }

            if (registry.getGroup(FALLBACK_GROUP) == null) {
                registry.addGroup(CarvingUtils.getDefaultGroupFor(FALLBACK_GROUP));
            }
            ICarvingGroup fallback = registry.getGroup(FALLBACK_GROUP);
            if (fallback.getOreName() == null && !isOreNameClaimed(registry, FALLBACK_ORE_NAME)) {
                registry.registerOre(FALLBACK_GROUP, FALLBACK_ORE_NAME);
            }
            if (fallback.getSound() == null) {
                registry.setVariationSound(FALLBACK_GROUP, "dig.stone");
            }

            ensureBricksAtTail(registry, FALLBACK_GROUP, "fallback");
            ownsGroup = true;
        } catch (Exception e) {
            FutureBlockBackport.LOG.error("Failed to register the moon basalt bricks.", e);
        }
    }

    private static void ensureBricksAtTail(ICarvingRegistry registry, String groupName, String kind) {
        ICarvingGroup group = registry.getGroup(groupName);
        if (group == null) {
            FutureBlockBackport.LOG
                .error("The '{}' carving group does not exist, the basalt bricks were not added.", groupName);
            return;
        }

        activeGroup = groupName;

        List<ICarvingVariation> variations = group.getVariations();
        List<ICarvingVariation> bricks = collectBricks(variations);
        if (isTail(variations, bricks)) {
            return;
        }

        for (ICarvingVariation brick : bricks) {
            if (!group.removeVariation(brick)) {
                FutureBlockBackport.LOG.warn("Could not remove a moon basalt variation from the '{}' group.", groupName);
            }
        }

        int order = maxOrder(group) + 1;
        for (int meta : ModBlocks.basalt.metas()) {
            registry.addVariation(groupName, ModBlocks.basalt, meta, order++);
        }

        FutureBlockBackport.LOG.info(
            "Appended the {} moon basalt variations to the {} '{}' chisel group.",
            ModBlocks.basalt.size(),
            kind,
            groupName);
    }

    private static List<ICarvingVariation> collectBricks(List<ICarvingVariation> variations) {
        List<ICarvingVariation> bricks = new ArrayList<ICarvingVariation>();
        if (variations != null) {
            for (ICarvingVariation variation : variations) {
                if (variation != null && variation.getBlock() == ModBlocks.basalt) {
                    bricks.add(variation);
                }
            }
        }
        return bricks;
    }

    private static boolean isTail(List<ICarvingVariation> variations, List<ICarvingVariation> bricks) {
        int[] metas = ModBlocks.basalt.metas();
        if (variations == null || bricks.size() != metas.length || variations.size() < metas.length) {
            return false;
        }

        int offset = variations.size() - metas.length;
        for (int i = 0; i < metas.length; i++) {
            ICarvingVariation variation = variations.get(offset + i);
            if (variation == null || variation.getBlock() != ModBlocks.basalt || variation.getBlockMeta() != metas[i]) {
                return false;
            }
        }
        return true;
    }

    private static int maxOrder(ICarvingGroup group) {
        int order = 0;
        List<ICarvingVariation> variations = group.getVariations();
        if (variations != null) {
            for (ICarvingVariation variation : variations) {
                if (variation != null) {
                    order = Math.max(order, variation.getOrder());
                }
            }
        }
        return order;
    }

    private static ICarvingGroup findPackGroup(ICarvingRegistry registry) {
        String skip = ownsGroup ? activeGroup : null;

        for (String name : PACK_GROUP_NAMES) {
            if (name.equals(skip)) {
                continue;
            }
            ICarvingGroup group = registry.getGroup(name);
            if (group != null) {
                return group;
            }
        }
        return findBasaltGroup(registry, skip);
    }

    private static ICarvingGroup findBasaltGroup(ICarvingRegistry registry, String skip) {
        ICarvingGroup best = null;
        int bestScore = 0;

        for (String name : registry.getSortedGroupNames()) {
            if (name == null || name.equals(skip)) {
                continue;
            }
            ICarvingGroup group = registry.getGroup(name);
            if (group == null) {
                continue;
            }

            int score = score(group);
            if (score > bestScore) {
                bestScore = score;
                best = group;
            }
        }

        return best;
    }

    private static int score(ICarvingGroup group) {
        int score = 0;

        if (isBasaltName(group.getOreName())) {
            score += 10;
        }

        List<ICarvingVariation> variations = group.getVariations();
        if (variations == null) {
            return score;
        }

        for (ICarvingVariation variation : variations) {
            Block block = variation.getBlock();
            if (block == null || block == ModBlocks.basalt) {
                continue;
            }
            if (isBasaltOre(block, variation.getBlockMeta())) {
                score += 4;
            } else if (isBasaltName(describe(block, variation.getBlockMeta()))) {
                score += 2;
            }
        }

        return score;
    }

    private static String describe(Block block, int meta) {
        StringBuilder name = new StringBuilder();

        String unlocalized = block.getUnlocalizedName();
        if (unlocalized != null) {
            name.append(unlocalized);
        }

        String registryName = Block.blockRegistry.getNameForObject(block);
        if (registryName != null) {
            name.append(' ')
                .append(registryName);
        }

        name.append(' ')
            .append(block.getClass().getName());

        try {
            String itemName = new ItemStack(block, 1, meta).getUnlocalizedName();
            if (itemName != null) {
                name.append(' ')
                    .append(itemName);
            }
        } catch (Exception ignored) {}

        return name.toString();
    }

    private static boolean isBasaltOre(Block block, int meta) {
        try {
            for (int id : OreDictionary.getOreIDs(new ItemStack(block, 1, meta))) {
                if (isBasaltName(OreDictionary.getOreName(id))) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    private static boolean isBasaltName(String name) {
        return name != null && name.toLowerCase(Locale.ROOT)
            .contains("basalt");
    }

    private static boolean isOreNameClaimed(ICarvingRegistry registry, String oreName) {
        for (String name : registry.getSortedGroupNames()) {
            ICarvingGroup group = registry.getGroup(name);
            if (group != null && oreName.equals(group.getOreName())) {
                return true;
            }
        }
        return false;
    }
}
