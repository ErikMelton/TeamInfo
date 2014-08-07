package kovu.teaminfo.teamspeak;

import kovu.teaminfo.ts3api.TS3Channel;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class MSguiPopChannel extends Gui
{
    private TS3Channel channel;
    private int yPos;
    private int xPos;
    private boolean isVisible = true;
    private int width = 100;
    private int height = 10;
    private FontRenderer f;

    public MSguiPopChannel(int var1, int var2, TS3Channel var3, FontRenderer var4)
    {
        this.xPos = var1;
        this.yPos = var2;
        this.channel = var3;
        this.f = var4;
    }

    public void draw()
    {
        drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -1357967601);
        this.drawString(this.f, "Join channel", this.xPos + 2, this.yPos + 1, 16777215);
    }

    public boolean isVisible()
    {
        return this.isVisible;
    }

    public void destroy()
    {
        this.isVisible = false;
    }

    public TS3Channel getChannel()
    {
        return this.channel;
    }

    public void setChannel(TS3Channel var1)
    {
        this.channel = var1;
    }

    public int getyPos()
    {
        return this.yPos;
    }

    public void setyPos(int var1)
    {
        this.yPos = var1;
    }

    public int getxPos()
    {
        return this.xPos;
    }

    public void setxPos(int var1)
    {
        this.xPos = var1;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getOptionPressed(int var1)
    {
        byte var2 = 0;

        if (var1 < this.yPos + 10)
        {
            var2 = 0;
        }

        return var2;
    }
}
