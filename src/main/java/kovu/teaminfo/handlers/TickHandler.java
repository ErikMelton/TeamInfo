package kovu.teaminfo.handlers;

import kovu.teaminfo.gui.GuiTeamInfoIngame;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class TickHandler
{
    public static boolean ticked = false;

    @SubscribeEvent
    public void RenderTickEvent(RenderTickEvent event) 
    {
        if (event.type == TickEvent.Type.RENDER)
        {
            if (!ticked && Minecraft.getMinecraft().ingameGUI != null) 
            {
                Minecraft.getMinecraft().ingameGUI = new GuiTeamInfoIngame(Minecraft.getMinecraft());
                ticked = true;
            }
        }      
        else if (event.type == TickEvent.Type.CLIENT) 
        {
        	// Do stuff
        }
    }
}
