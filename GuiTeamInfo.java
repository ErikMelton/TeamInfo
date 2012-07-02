// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, teaminfo, RenderEngine, GuiButton, 
//            GuiAddtoTeam, GuiRemoveFromTeam, GuiTeamInfoConfig

public class GuiTeamInfo extends GuiScreen
{

    private GuiScreen parentScreen;

    public GuiTeamInfo(GuiScreen guiscreen)
    {
        parentScreen = guiscreen;
    }

    public void updateScreen()
    {
    }

    public void drawScreen(int i, int j, float f)
    {
        mc.renderEngine.setupTexture(TeamInfo.spowney, 0);
        GL11.glTranslated(width / 2 - 155, height / 2 - 10, 0.0D);
        GL11.glScalef(1.0F, 0.5F, 1.0F);
        GL11.glScalef(0.75F, 0.75F, 1.0F);
        drawTexturedModalRect(0, 0, 32, 64, 32, 64);
        drawTexturedModalRect(0, 0, 160, 64, 32, 64);
        GL11.glScalef(1.333333F, 1.333333F, 1.0F);
        GL11.glScalef(1.0F, 2.0F, 1.0F);
        GL11.glTranslated(-(width / 2 - 155), -height / 2 + 10, 0.0D);
        mc.renderEngine.setupTexture(TeamInfo.jay92, 0);
        GL11.glTranslated(width / 2 + 130, height / 2 - 10, 0.0D);
        GL11.glScalef(1.0F, 0.5F, 1.0F);
        GL11.glScalef(0.75F, 0.75F, 1.0F);
        drawTexturedModalRect(0, 0, 32, 64, 32, 64);
        drawTexturedModalRect(0, 0, 160, 64, 32, 64);
        GL11.glScalef(1.333333F, 1.333333F, 1.0F);
        GL11.glScalef(1.0F, 2.0F, 1.0F);
        GL11.glTranslated(-(width / 2 + 130), -height / 2 + 10, 0.0D);
        drawRect(width / 2 - 120, height / 2 - 20, width / 2 + 120, height / 2 + 30, 0x60000000);
        drawCenteredString(fontRenderer, "\247cTeam Info by \247bRainfur", width / 2, height / 2 - 13, 0xffffff);
        super.drawScreen(i, j, f);
    }

    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 102, height / 2 + 5, 60, 20, "Add"));
        controlList.add(new GuiButton(1, width / 2 - 30, height / 2 + 5, 60, 20, "Remove"));
        controlList.add(new GuiButton(2, width / 2 + 42, height / 2 + 5, 60, 20, "Config"));
    }

    public void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
            mc.displayGuiScreen(new GuiAddtoTeam(this));
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(new GuiRemoveFromTeam(this));
        }
        if(guibutton.id == 2)
        {
            mc.displayGuiScreen(new GuiTeamInfoConfig(this));
        }
    }
}
