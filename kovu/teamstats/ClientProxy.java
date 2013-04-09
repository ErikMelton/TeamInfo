package kovu.teamstats;

import com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInformation() {
        TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
    }
    
    @Override
    public void initCapes()
    {
    	DeveloperCapesAPI.getInstance().init("http://dl.dropbox.com/u/17382409/SampleCape.txt");
    }
}
