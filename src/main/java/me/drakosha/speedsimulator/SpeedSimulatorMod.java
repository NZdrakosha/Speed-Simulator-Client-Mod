package me.drakosha.speedsimulator;

import client.ClientEvent;
import client.SphereEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SpeedSimulatorMod.MODID, name = SpeedSimulatorMod.NAME, version = SpeedSimulatorMod.VERSION)
public class SpeedSimulatorMod
{
    public static final String MODID = "speedsimulator";
    public static final String NAME = "Speed Simulator Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        SphereEvent.initMap();
        MinecraftForge.EVENT_BUS.register(new SphereEvent());
        MinecraftForge.EVENT_BUS.register(new ClientEvent());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
