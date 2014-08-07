package kovu.teaminfo.teamspeak;

import java.util.ArrayList;

import kovu.teaminfo.TeamInfo;
import kovu.teaminfo.ts3api.TS3Channel;
import kovu.teaminfo.ts3api.TS3User;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class MSguiServer extends Gui
{
    private int xmod = 50;
    private int ymod = 50;
    private int scroll = 0;
    private boolean visible = true;
    private Minecraft mc;
    private FontRenderer f;
    private MSgig ingame;
    private TeamInfo mod;
    private boolean isFocused = false;
    protected MSguiPopChannel channelPop;
    protected MSguiPopUser userPop;
    protected ArrayList serverList = new ArrayList();
    protected boolean isConnected = false;

    public MSguiServer(Minecraft var1, FontRenderer var2, MSgig var3, TeamInfo var4)
    {
        this.mc = var1;
        this.f = var2;
        this.ingame = var3;
        this.mod = var4;
    }

    public MSguiServer(Minecraft var1, FontRenderer var2, MSgig var3, TeamInfo var4, int var5, int var6)
    {
        this.mc = var1;
        this.f = var2;
        this.ingame = var3;
        this.mod = var4;
        this.xmod = var5;
        this.ymod = var6;
    }

    public void drawBody()
    {
        int var1 = this.xmod;
        int var2 = this.ymod;
        int var3 = 200 + this.xmod;
        int var4 = 365 + this.ymod;
        byte var5 = 1;
        int var6 = -1728053248;
        int var7 = -821096689;

        if (this.isVisible())
        {
            this.drawVerticalLine(var1, var2 + 1, var4 - 2, var6);
            this.drawVerticalLine(var3 - 1, var2 + 1, var4 - 2, var6);
            this.drawHorizontalLine(var1 + 2, var3 - 3, var2, var6);
            this.drawHorizontalLine(var1 + 2, var3 - 3, var4 - 1, var6);
            this.drawHorizontalLine(var1 + 1, var1 + 1, var2 + 1, var6);
            this.drawHorizontalLine(var3 - 2, var3 - 2, var2 + 1, var6);
            this.drawHorizontalLine(var3 - 2, var3 - 2, var4 - 2, var6);
            this.drawHorizontalLine(var1 + 1, var1 + 1, var4 - 2, var6);
            drawRect(var1 + var5, var2 + var5, var3 - var5, var4 - var5, var7);
            this.drawString(this.f, "Chat", var1 + 10, var2 + 15, 16777215);
            this.drawString(this.f, "Console", var1 + 45, var2 + 15, 16777215);
            this.drawString(this.f, "Settings", var1 + 95, var2 + 15, 16777215);
            this.drawHorizontalLine(var1 + 2, var3 - 2, var2 + 25, 637534207);
            this.drawHorizontalLine(var1 + 2, var3 - 2, var2 + 10, 637534207);
            this.drawHorizontalLine(var1 + 2, var3 - 2, var4 - 25, 637534207);
            int var8;

            for (var8 = 0; var8 < 30; ++var8)
            {
                if (var8 % 2 == 1)
                {
                    drawRect(var1 + var5 + 10, var2 + var5 + 30 + 10 * var8, var3 - var5 - 10, var2 + var5 + 40 + 10 * var8, var7 + -3092272);
                }
                else
                {
                    drawRect(var1 + var5 + 10, var2 + var5 + 30 + 10 * var8, var3 - var5 - 10, var2 + var5 + 40 + 10 * var8, var7 + -4144960);
                }
            }

            if (this.serverList.size() > 0)
            {
                for (var8 = 0; var8 < this.serverList.size(); ++var8)
                {
                    int var9 = var8;

                    if (this.scroll > 0 && var8 + this.scroll < this.serverList.size() - 1)
                    {
                        var9 = var8 + this.scroll;
                    }

                    Object var10 = this.serverList.get(var9);

                    if (var8 < 30)
                    {
                        if (var10 instanceof MSguiListChannel)
                        {
                            MSguiListChannel var11 = (MSguiListChannel)var10;
                            this.drawString(this.f, "[]" + var11.getChannel().getChannelName().replaceAll("\\\\s", " ").replaceAll("[^a-zA-Z0-9\\s]", ""), var1 + 12, var2 + 32 + var8 * 10, 16777215);
                        }
                        else if (var10 instanceof MSguiListUser)
                        {
                            MSguiListUser var12 = (MSguiListUser)var10;
                            this.drawString(this.f, "  " + var12.getUser().getUsername().replaceAll("\\\\s", " ").replaceAll("[^a-zA-Z0-9\\s]", ""), var1 + 12, var2 + 32 + var8 * 10, 16777215);
                        }
                    }
                }
            }

            if (this.channelPop != null && this.channelPop.isVisible())
            {
                this.channelPop.draw();
            }
            else if (this.userPop != null && this.userPop.isVisible())
            {
                this.userPop.draw();
            }
        }

        this.drawGradientRect(var1 + var5, var2 + var5, var3 - var5, var2 + var5 + 10, -586215665, -4144960);
        this.drawString(this.f, "Minespeak", var1 + 12, var2 + 2, 16777215);

        if (this.isConnected)
        {
            this.drawString(this.f, "\u00a7a\u00a7l\u2713", var1 + 2, var2 + 1, 16777215);
        }
        else
        {
            this.drawString(this.f, "\u00a74X", var1 + 3, var2 + 2, 16777215);
        }

        drawRect(var1 + var5 + 186, var2 + var5 + 2, var3 - var5 - 1, var2 + var5 + 8, -4144960);
        this.drawString(this.f, "_", var1 + 190, var2 - 1, 16777215);
    }

    public int getxpos()
    {
        return this.xmod;
    }

    public int getypos()
    {
        return this.ymod;
    }

    public void setypos(int var1)
    {
        this.ymod = var1;
    }

    public void setxpos(int var1)
    {
        this.xmod = var1;
    }

    public void toggleVisibility()
    {
        this.visible = !this.visible;
    }

    public boolean isVisible()
    {
        return this.visible;
    }

    public int getScroll()
    {
        return this.scroll;
    }

    public void scrollUp()
    {
        if (this.scroll > 0)
        {
            --this.scroll;
        }
    }

    public void scrollDown()
    {
        if (this.scroll < this.serverList.size() - 31)
        {
            ++this.scroll;
        }
    }

    public void setFocused()
    {
        this.isFocused = true;
    }

    public void setUnfocused()
    {
        this.isFocused = false;
    }

    public boolean isFocused()
    {
        return this.isFocused;
    }

    public void fillServerList()
    {
        this.serverList.clear();

        for (int var1 = 0; var1 < this.ingame.channels.size(); ++var1)
        {
            this.serverList.add(new MSguiListChannel((TS3Channel)this.ingame.channels.get(var1)));

            for (int var2 = 0; var2 < this.ingame.users.size(); ++var2)
            {
                if (((TS3User)this.ingame.users.get(var2)).getCid().equals(((TS3Channel)this.ingame.channels.get(var1)).getCid()))
                {
                    this.serverList.add(new MSguiListUser((TS3User)this.ingame.users.get(var2)));
                }
            }
        }
    }
}
