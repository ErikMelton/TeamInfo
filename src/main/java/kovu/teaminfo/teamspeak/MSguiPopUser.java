package kovu.teaminfo.teamspeak;

import kovu.teaminfo.ts3api.TS3User;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class MSguiPopUser extends Gui
{
    private TS3User user;
    private int yPos;
    private int xPos;
    private int width = 100;
    private int height = 50;
    private boolean isVisible = true;
    private FontRenderer f;

    public MSguiPopUser(int var1, int var2, TS3User var3, FontRenderer var4)
    {
        this.xPos = var1;
        this.yPos = var2;
        this.user = var3;
        this.f = var4;
    }

    public void draw()
    {
        drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -1357967601);
        this.drawString(this.f, "Message", this.xPos + 2, this.yPos + 1, 16777215);
        this.drawString(this.f, "Poke", this.xPos + 2, this.yPos + 11, 16777215);
        this.drawString(this.f, "Kick from channel", this.xPos + 2, this.yPos + 21, 16777215);
        this.drawString(this.f, "Kick from Server", this.xPos + 2, this.yPos + 31, 16777215);
        this.drawString(this.f, "Ban from Server", this.xPos + 2, this.yPos + 41, 16777215);
        this.drawHorizontalLine(this.xPos + 2, this.xPos + this.width - 2, this.yPos + 10, 637534207);
        this.drawHorizontalLine(this.xPos + 2, this.xPos + this.width - 2, this.yPos + 20, 637534207);
        this.drawHorizontalLine(this.xPos + 2, this.xPos + this.width - 2, this.yPos + 30, 637534207);
        this.drawHorizontalLine(this.xPos + 2, this.xPos + this.width - 2, this.yPos + 40, 637534207);
    }

    public boolean isVisible()
    {
        return this.isVisible;
    }

    public void destroy()
    {
        this.isVisible = false;
    }

    public TS3User getUser()
    {
        return this.user;
    }

    public void setChannel(TS3User var1)
    {
        this.user = var1;
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
        else if (var1 < this.yPos + 20)
        {
            var2 = 1;
        }
        else if (var1 < this.yPos + 30)
        {
            var2 = 2;
        }
        else if (var1 < this.yPos + 40)
        {
            var2 = 3;
        }
        else if (var1 < this.yPos + 50)
        {
            var2 = 4;
        }

        return var2;
    }
}
