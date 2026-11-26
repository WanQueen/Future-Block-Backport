package com.futureblockbackport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;

@Mod(
    modid = FutureBlockBackport.MODID,
    name = FutureBlockBackport.MODNAME,
    version = Tags.VERSION,
    dependencies = "required-after:chisel;after:GalacticraftCore;after:GalaxySpace;after:gregtech",
    acceptedMinecraftVersions = "[1.7.10]")
public class FutureBlockBackport {

    public static final String MODID = "futureblockbackport";
    public static final String MODNAME = "Future Block Backport";

    public static final Logger LOG = LogManager.getLogger(MODNAME);

    @Mod.Instance(MODID)
    public static FutureBlockBackport instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Carving.register();
        BasaltCarving.registerVisuals();
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        BasaltCarving.registerGroup(false);
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        BasaltCarving.registerGroup(true);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        BasaltCarving.registerGroup(true);
    }

    @Mod.EventHandler
    public void missingMappings(FMLMissingMappingsEvent event) {
        LegacyMapping.remap(event);
    }
}
