// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiButton, teaminfo, GuiTextField

public class GuiAddReject extends GuiScreen
{

    private GuiScreen parentScreen;
    private GuiTextField serverTextField;
    private String playername;
    private String fullMessage;
    public TeamInfo tf;
    
    public GuiAddReject(GuiScreen guiscreen, String s, String s1)
    {
        parentScreen = guiscreen;
        playername = s;
        fullMessage = s1;
    }

    public void updateScreen()
    {
    }

    public void drawScreen(int i, int j, float f)
    {
        drawRect(width / 2 - 120, height / 2 - 20, width / 2 + 120, height / 2 + 30, 0x60000000);
        drawCenteredString(fontRenderer, (new StringBuilder((new StringBuilder()).append("\247c").append(playername).append("\247f").toString())).append(" wants to share team info with you").toString(), width / 2, height / 2 - 13, 0xffffff);
        super.drawScreen(i, j, f);
    }

    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 66, height / 2 + 5, 60, 20, "Add"));
        controlList.add(new GuiButton(1, width / 2 + 6, height / 2 + 5, 60, 20, "Reject"));
    }

    public void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
            tf.acceptadd(playername);
            TeamInfo.interpretUpdate(fullMessage);
            mc.displayGuiScreen(parentScreen);
        }
        if(guibutton.id == 1)
        {
            tf.rejectadd(playername);
            mc.displayGuiScreen(parentScreen);
        }
    }
}
