// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, powney, teaminfo, GuiButton, 
//            GuiTeamInfo

public class GuiRemoveFromTeam extends GuiScreen
{

    private GuiScreen parentScreen;
    int players;
    int extra;
    
    public GuiAddtoTeam ga = new GuiAddtoTeam(parentScreen);

    public GuiRemoveFromTeam(GuiScreen guiscreen)
    {
        players = 0;
        extra = 0;
        parentScreen = guiscreen;
    }

    public void updateScreen()
    {
        if(Keyboard.isKeyDown(15))
        {
            powney.showlist = true;
        } else
        {
            powney.showlist = false;
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        byte byte0 = 0;
        if(TeamInfo.players.size() % 2 == 0 && TeamInfo.players.size() != 0)
        {
            byte0 = -1;
        }
        drawRect(width / 2 - 120, height / 2 - 50, width / 2 + 120, height / 2 + 30 + 23 * (players / 2 + byte0), 0x60000000);
        drawCenteredString(fontRenderer, "Remove Player", width / 2, height / 2 - 43, 0xffffff);
        if(TeamInfo.players.size() == 0)
        {
            drawCenteredString(fontRenderer, "No players in list", width / 2, height / 2 - 18, 0xffffff);
        }
        super.drawScreen(i, j, f);
    }

    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        players = 0;
        for(Iterator iterator = TeamInfo.players.keySet().iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            if(players % 2 == 0)
            {
                controlList.add(new GuiButton(players, width / 2 - 90, (height / 2 - 20) + 23 * (players / 2), 80, 20, s));
            } else
            {
                controlList.add(new GuiButton(players, width / 2 + 10, (height / 2 - 20) + 23 * (players / 2), 80, 20, s));
            }
            players++;
        }

        if(players % 2 != 0 || players == 0)
        {
            extra = 1;
        }
        controlList.add(new GuiButton(players, width / 2 - 30, (height / 2 - 20) + 23 * (players / 2 + extra), 60, 20, "Back"));
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    public void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == players)
        {
            mc.displayGuiScreen(parentScreen);
        }
        int i = 0;
        for(Iterator iterator = TeamInfo.players.keySet().iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            if(guibutton.id == i)
            {
            	TeamInfo tf = new TeamInfo(ga.getServerTextField());
            	
                tf.remove(s);
                mc.displayGuiScreen(new GuiRemoveFromTeam(new GuiTeamInfo(null)));
                return;
            }
            i++;
        }

    }

    public void mouseCicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
    }
}
