package kovu.teamstats;

import kovu.teamstats.gui.GuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "TeamInfo", name = "Team Info", version = "1.0")
public class TeamInfoMod {

    @SidedProxy(clientSide = "kovu.teamstats.ClientProxy", serverSide = "kovu.stats.CommonProxy")
    public static CommonProxy proxy;
    @Instance
    public static TeamInfoMod instance;

    @Init
    public void load(FMLInitializationEvent evt) {
        proxy.registerRenderInformation();
    }
    
    @PreInit
    public void preInit(FMLPreInitializationEvent evt)
    {
    	NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
    	proxy.initCapes();
    }
}
