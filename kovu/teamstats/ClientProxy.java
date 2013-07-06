package kovu.teamstats;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInformation() {
        TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
    }
}