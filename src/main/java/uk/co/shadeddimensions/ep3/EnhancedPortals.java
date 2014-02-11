package uk.co.shadeddimensions.ep3;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.WorldEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.LoggerConfig;

import uk.co.shadeddimensions.ep3.lib.Reference;
import uk.co.shadeddimensions.ep3.network.CommonProxy;
import uk.co.shadeddimensions.ep3.network.GuiHandler;
import uk.co.shadeddimensions.ep3.network.PacketPipeline;
import uk.co.shadeddimensions.ep3.portal.NetworkManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(name = Reference.NAME, modid = Reference.ID, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions = Reference.MC_VERSION)
public class EnhancedPortals
{
    public static final PacketPipeline packetPipeline = new PacketPipeline();
    
    @Instance(Reference.ID)
    public static EnhancedPortals instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy proxy;

    public static final Logger logger = LogManager.getLogger("EnhancedPortals");
    
    public EnhancedPortals()
    {
        LoggerConfig fml = new LoggerConfig(FMLCommonHandler.instance().getFMLLogger().getName(), Level.ALL, true);
        LoggerConfig modConf = new LoggerConfig(logger.getName(), Level.ALL, true);
        modConf.setParent(fml);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        packetPipeline.initalise();
        proxy.registerPackets();
        proxy.setupCrafting();
        proxy.miscSetup();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        packetPipeline.postInitialise();
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        event.getModMetadata().version = Reference.VERSION;
        proxy.setupConfiguration(new Configuration(event.getSuggestedConfigurationFile()));
        
        proxy.registerBlocks();
        proxy.registerItems();
        proxy.registerTileEntities();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        CommonProxy.networkManager = new NetworkManager(event);
    }
    
    @SubscribeEvent
    public void worldSave(WorldEvent.Save event)
    {
        if (!event.world.isRemote)
        {
            CommonProxy.networkManager.saveAllData();
        }
    }
}
