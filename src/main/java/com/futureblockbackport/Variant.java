package com.futureblockbackport;

final class Variant {

    final int meta;
    final String texture;

    Variant(int meta, String texture) {
        this.meta = meta;
        this.texture = texture;
    }

    String path(String dir) {
        return dir + "/" + texture;
    }

    String desc(String dir) {
        return FutureBlockBackport.MODID + "." + dir + "." + texture + ".desc";
    }
}
