package kovu.teaminfo;

import org.lwjgl.Sys;

import com.sun.corba.se.spi.ior.iiop.GIOPVersion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.common.ForgeVersion;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
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

            GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        
            if (gui != null && gui instanceof GuiChat && !(gui instanceof GuiTeamInfoChat))
            {
            	System.out.println("IN CHATGUI");
               // Minecraft.getMinecraft().displayGuiScreen(new GuiTeamInfoChat());
            }
        }      
        else if (event.type == TickEvent.Type.CLIENT) 
        {
        	// Do stuff
        }
    }
}
