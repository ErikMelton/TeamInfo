package kovu.teaminfo.teamspeak;

import java.util.ArrayList;

import kovu.teaminfo.TeamInfo;
import kovu.teaminfo.ts3api.TS3User;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class MSguiChat extends Gui
{
    private Minecraft mc;
    private FontRenderer f;
    private MSgig ingame;
    private TeamInfo mod;
    private int xmod = 50;
    private int ymod = 50;
    protected int scroll = 0;
    protected int consoleScroll = 0;
    private boolean visible = true;
    private boolean isFocused = false;
    protected String userChat = "";
    protected int selectedChat = 1;
    protected int sideScroll = 0;
    protected boolean channelHasNewMessage = false;
    protected ArrayList consoleChat = new ArrayList();
    protected ArrayList channelChat = new ArrayList();
    protected ArrayList conversations = new ArrayList();

    public MSguiChat(Minecraft var1, FontRenderer var2, MSgig var3, TeamInfo var4)
    {
        this.mc = var1;
        this.f = var2;
        this.ingame = var3;
        this.mod = var4;
    }

    public void drawBody()
    {
        int var1 = this.xmod;
        int var2 = this.ymod;
        int var3 = 350 + this.xmod;
        int var4 = 150 + this.ymod;
        byte var5 = 1;
        int var6 = -1728053248;
        int var7 = -821096689;
        String var8 = "";
        int var9;

        if (this.visible)
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
            this.drawGradientRect(var1 + 4, var2 + var5 + 25, var3 - 4, var2 + var5 + 130, -1728053248, var7 + -1714368304);
            drawRect(var1 + 4, var4 - 15, var3 - 4, var4 - 5, var7 + -3092272);
            this.drawString(this.f, this.userChat, var1 + 6, var4 - 14, 16777215);
            drawRect(var1 + 4, var2 + 15, var1 + 15, var2 + 25, var7 + -3092272);
            drawRect(var1 + 17, var2 + 15, var1 + 60, var2 + 25, var7 + -3092272);
            drawRect(var1 + 64, var2 + 15, var1 + 74, var2 + 25, var7 + -3092272);
            this.drawString(this.f, "<", var1 + 66, var2 + 16, 16777215);
            int var10;

            for (var9 = 0; var9 < this.conversations.size(); ++var9)
            {
                var10 = var9;

                if (this.sideScroll > 0 && var9 + this.sideScroll < this.conversations.size())
                {
                    var10 = var9 + this.sideScroll;
                }

                if (var9 < 4)
                {
                    drawRect(var1 + 77 + var9 * 64, var2 + 15, var1 + 140 + var9 * 64, var2 + 25, var7 + -3092272);

                    if (this.selectedChat - 2 == var10)
                    {
                        this.drawString(this.f, "\u00a77" + ((MSguiConversation)this.conversations.get(var10)).ensureFit(), var1 + 78 + var9 * 64, var2 + 16, 16777215);
                        ((MSguiConversation)this.conversations.get(var10)).read();
                    }
                    else
                    {
                        this.drawString(this.f, ((MSguiConversation)this.conversations.get(var10)).ensureFit(), var1 + 78 + var9 * 64, var2 + 16, 16777215);
                    }
                }
            }

            drawRect(var1 + 335, var2 + 15, var1 + 345, var2 + 25, var7 + -3092272);
            this.drawString(this.f, ">", var1 + 338, var2 + 16, 16777215);

            if (this.selectedChat == 0)
            {
                this.drawString(this.f, "\u00a77@", var1 + 6, var2 + 16, 16777215);
                this.drawString(this.f, "Channel", var1 + 19, var2 + 16, 16777215);
            }
            else if (this.selectedChat == 1)
            {
                for (var9 = 0; var9 < this.channelChat.size(); ++var9)
                {
                    var10 = var9;

                    if (this.scroll > 0 && var9 + this.scroll < this.channelChat.size())
                    {
                        var10 = var9 + this.scroll;
                    }

                    if (var9 < 10)
                    {
                        this.drawString(this.f, (String)this.channelChat.get(var10), var1 + 7, var2 + 120 - var9 * 10, 16777215);
                    }
                }

                this.channelHasNewMessage = false;
                this.drawString(this.f, "@", var1 + 6, var2 + 16, 16777215);
                this.drawString(this.f, "\u00a77Channel", var1 + 19, var2 + 16, 16777215);
            }
            else if (this.selectedChat > 1)
            {
                this.drawVerticalLine(var1 + 313, var2 + 134, var2 + 145, var6);
                this.drawString(this.f, "Close", var1 + 317, var2 + 136, 16777215);

                for (var9 = 0; var9 < ((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getMessageList().size(); ++var9)
                {
                    var10 = var9;

                    if (((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getScroll() > 0 && var9 + ((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getScroll() < ((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getMessageList().size())
                    {
                        var10 = var9 + ((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getScroll();
                    }

                    if (var9 < 10)
                    {
                        this.drawString(this.f, (String)((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getMessageList().get(var10), var1 + 7, var2 + 120 - var9 * 10, 16777215);
                    }
                }

                this.drawString(this.f, "@", var1 + 6, var2 + 16, 16777215);
                this.drawString(this.f, "Channel", var1 + 19, var2 + 16, 16777215);
            }
        }

        if (this.channelHasNewMessage)
        {
            var8 = var8 + "channel, ";
        }

        this.drawGradientRect(var1 + var5, var2 + var5, var3, var2 + var5 + 10, -586215665, -4144960);
        drawRect(var1 + var5 + 337, var2 + var5 + 2, var3 - 1, var2 + var5 + 8, -4144960);

        for (var9 = 0; var9 < this.conversations.size(); ++var9)
        {
            if (((MSguiConversation)this.conversations.get(var9)).hasNewMessage())
            {
                var8 = var8 + ((MSguiConversation)this.conversations.get(var9)).ensureFit() + ", ";
            }
        }

        if (var8.length() > 1)
        {
            this.drawString(this.f, "[Chat]New Msg:" + var8, var1 + 12, var2 + 2, 16777215);
        }
        else
        {
            this.drawString(this.f, "[Chat]", var1 + 12, var2 + 2, 16777215);
        }

        this.drawString(this.f, "_", var1 + 341, var2 - 1, 16777215);
    }

    public void handlePrivateMessage(String var1, String var2, String var3, String var4)
    {
        int var5;

        for (var5 = 0; var5 < this.conversations.size(); ++var5)
        {
            if (((MSguiConversation)this.conversations.get(var5)).getClid().equals(var3))
            {
                ((MSguiConversation)this.conversations.get(var5)).addMessage("You:" + var1);
                return;
            }

            if (((MSguiConversation)this.conversations.get(var5)).getClid().equals(var4))
            {
                ((MSguiConversation)this.conversations.get(var5)).addMessage("Partner:" + var1);
                return;
            }
        }

        for (var5 = 0; var5 < this.ingame.users.size(); ++var5)
        {
            if (((TS3User)this.ingame.users.get(var5)).getClid().equals(var4))
            {
                this.conversations.add(new MSguiConversation((TS3User)this.ingame.users.get(var5), "Partner:" + var1));
            }
        }
    }

    public void openNewPrivateChat(String var1)
    {
        int var2;

        for (var2 = 0; var2 < this.conversations.size(); ++var2)
        {
            if (((MSguiConversation)this.conversations.get(var2)).getClid().equals(var1))
            {
                return;
            }
        }

        for (var2 = 0; var2 < this.ingame.users.size(); ++var2)
        {
            if (((TS3User)this.ingame.users.get(var2)).getClid().equals(var1))
            {
                this.conversations.add(new MSguiConversation((TS3User)this.ingame.users.get(var2)));
                this.selectedChat = this.conversations.size() + 1;
                return;
            }
        }
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

    public void scrollUp()
    {
        if (this.selectedChat != 0)
        {
            if (this.selectedChat == 1)
            {
                if (this.scroll < this.channelChat.size() - 10)
                {
                    ++this.scroll;
                }
            }
            else if (((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getScroll() < ((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getMessageList().size() - 10)
            {
                ((MSguiConversation)this.conversations.get(this.selectedChat - 2)).scrollUp();
            }
        }
    }

    public void scrollDown()
    {
        if (this.selectedChat != 0)
        {
            if (this.selectedChat == 1)
            {
                if (this.scroll > 0)
                {
                    --this.scroll;
                }
            }
            else if (((MSguiConversation)this.conversations.get(this.selectedChat - 2)).getScroll() > 0)
            {
                ((MSguiConversation)this.conversations.get(this.selectedChat - 2)).scrollDown();
            }
        }
    }

    public void scrollLeft()
    {
        if (this.sideScroll > 0)
        {
            --this.sideScroll;
        }
    }

    public void scrollRight()
    {
        if (this.sideScroll < this.conversations.size() - 4)
        {
            ++this.sideScroll;
        }
    }
}
