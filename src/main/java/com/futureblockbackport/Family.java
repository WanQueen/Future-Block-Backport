package com.futureblockbackport;

final class Family {

    final String group;
    final BlockVariants variants;
    final BlockPillarVariants[] pillars;
    final Anchors.Spec base;
    final String sound;

    Family(String group, BlockVariants variants, Anchors.Spec base, String sound, BlockPillarVariants... pillars) {
        this.group = group;
        this.variants = variants;
        this.base = base;
        this.sound = sound;
        this.pillars = pillars;
    }
}
