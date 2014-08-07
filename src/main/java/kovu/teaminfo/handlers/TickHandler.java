package kovu.teaminfo.handlers;

import org.lwjgl.input.Keyboard;

import kovu.teaminfo.TeamInfo;
import kovu.teaminfo.gui.GuiTeamInfoIngame;
import kovu.teaminfo.teamspeak.MSgc;
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
            if (this.shouldToggle(23) && !TeamInfo.isConnected)
            {
                TeamInfo.ingame.createBridge();
            }
            else if (this.shouldToggle(22))
            {
                Minecraft.getMinecraft().displayGuiScreen(new MSgc(TeamInfo.instance, TeamInfo.ingame.serverGui, TeamInfo.ingame.chatGui));
            }
            else if (this.shouldToggle(38))
            {
                
            }

            TeamInfo.ingame.drawGui();
            TeamInfo.ingame.drawSpeakers();

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
    
    public boolean shouldToggle(int var1)
    {
        if (Minecraft.getMinecraft().currentScreen != null)
        {
            return false;
        }
        else if (Keyboard.isKeyDown(var1) && !TeamInfo.cannot_toggle[var1])
        {
        	TeamInfo.cannot_toggle[var1] = true;
            return true;
        }
        else if (!Keyboard.isKeyDown(var1))
        {
        	TeamInfo.cannot_toggle[var1] = false;
            return false;
        }
        else
        {
            return false;
        }
    }
}
