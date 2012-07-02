// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiButton, powney

public class GuiTeamInfoConfig extends GuiScreen
{

    private GuiScreen parentScreen;

    public GuiTeamInfoConfig(GuiScreen guiscreen)
    {
        parentScreen = guiscreen;
    }

    public void updateScreen()
    {
    }

    public void drawScreen(int i, int j, float f)
    {
        drawRect(width / 2 - 165, height / 2 - 20, width / 2 + 165, height / 2 + 60, 0x60000000);
        drawCenteredString(fontRenderer, "\247cConfig:", width / 2, height / 2 - 13, 0xffffff);
        super.drawScreen(i, j, f);
    }

    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 155, height / 2 + 5, 100, 20, (new StringBuilder()).append("Scale ").append((int)(powney.scale * 100F)).append("%").toString()));
        controlList.add(new GuiButton(1, width / 2 - 50, height / 2 + 5, 100, 20, (new StringBuilder()).append("Armor ").append(powney.armour ? "On" : "Off").toString()));
        controlList.add(new GuiButton(2, width / 2 + 55, height / 2 + 5, 100, 20, (new StringBuilder()).append("EmptyArmor ").append(powney.noarmour ? "On" : "Off").toString()));
        controlList.add(new GuiButton(3, width / 2 - 155, height / 2 + 5 + 24, 100, 20, (new StringBuilder()).append("Co-ords ").append(powney.coords ? "On" : "Off").toString()));
        controlList.add(new GuiButton(4, width / 2 - 50, height / 2 + 5 + 24, 100, 20, (new StringBuilder()).append("Hunger ").append(powney.hunger ? "On" : "Off").toString()));
        controlList.add(new GuiButton(5, width / 2 + 55, height / 2 + 5 + 24, 100, 20, "Back"));
    }

    public void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
            if(powney.scale != 1.5F)
            {
                powney.scale = (float)((double)powney.scale + 0.25D);
            } else
            {
                powney.scale = 0.5F;
            }
            guibutton.displayString = (new StringBuilder()).append("Scale ").append((int)(powney.scale * 100F)).append("%").toString();
        }
        if(guibutton.id == 1)
        {
            powney.armour = !powney.armour;
            guibutton.displayString = (new StringBuilder()).append("Armor ").append(powney.armour ? "On" : "Off").toString();
        }
        if(guibutton.id == 2)
        {
            powney.noarmour = !powney.noarmour;
            guibutton.displayString = (new StringBuilder()).append("EmptyArmor ").append(powney.noarmour ? "On" : "Off").toString();
        }
        if(guibutton.id == 3)
        {
            powney.coords = !powney.coords;
            guibutton.displayString = (new StringBuilder()).append("Co-ords ").append(powney.coords ? "On" : "Off").toString();
        }
        if(guibutton.id == 4)
        {
            powney.hunger = !powney.hunger;
            guibutton.displayString = (new StringBuilder()).append("Hunger ").append(powney.hunger ? "On" : "Off").toString();
        }
        if(guibutton.id == 5)
        {
            mc.displayGuiScreen(parentScreen);
        }
    }
}
