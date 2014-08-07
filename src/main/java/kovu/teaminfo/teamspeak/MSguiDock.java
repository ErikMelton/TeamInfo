package kovu.teaminfo.teamspeak;

import kovu.teaminfo.TeamInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class MSguiDock extends Gui
{
    private Minecraft mc;
    private FontRenderer f;
    private MSgig ingame;
    private TeamInfo mod;

    public MSguiDock(Minecraft var1, FontRenderer var2, MSgig var3, TeamInfo var4)
    {
        this.mc = var1;
        this.f = var2;
        this.ingame = var3;
        this.mod = var4;
    }
}
