package teamstats;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "TeamInfo", name = "Team Info", version = "1.0")
public class TeamInfoMod {

    @SidedProxy(clientSide = "kovu.teaminfo.ClientProxy", serverSide = "kovu.teaminfo.CommonProxy")
    public static CommonProxy proxy;
    @Instance
    public static TeamInfoMod instance;

    @Init
    public void load(FMLInitializationEvent evt) {
        proxy.registerRenderInformation();
    }
}
