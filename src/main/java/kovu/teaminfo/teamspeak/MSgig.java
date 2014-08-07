package kovu.teaminfo.teamspeak;

import java.util.ArrayList;

import kovu.teaminfo.TeamInfo;
import kovu.teaminfo.ts3api.TS3User;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatComponentText;

public class MSgig extends Gui
{
    private Minecraft mc;
    private FontRenderer f;
    protected MSbridge bridge;
    protected TeamInfo mod;
    public MSguiServer serverGui;
    public MSguiChat chatGui;
    protected ArrayList users = new ArrayList();
    private ArrayList speakers = new ArrayList();
    protected ArrayList channels = new ArrayList();

    public MSgig(Minecraft var1, FontRenderer var2, TeamInfo var3)
    {
        this.mc = var1;
        this.f = var2;
        this.mod = var3;
        this.serverGui = new MSguiServer(var1, var2, this, var3);
        this.chatGui = new MSguiChat(var1, var2, this, var3);
    }

    public void drawGui()
    {
        this.chatGui.drawBody();
        this.serverGui.drawBody();
    }

    public void drawSpeakers()
    {
        for (int var1 = 0; var1 < this.speakers.size(); ++var1)
        {
            if (this.speakers.size() > 0)
            {
                this.drawString(this.f, this.clidToUsername((String)this.speakers.get(var1)), 10, 10 + var1 * 10, 16777215);
            }
        }
    }

    public void addChatMessage(String var1)
    {
        this.mc.thePlayer.addChatMessage(new ChatComponentText(var1));
    }

    public void createBridge()
    {
        this.bridge = new MSbridge(this);
    }

    public void sendTSMessage(String var1)
    {
        try
        {
            this.bridge.handleInput(var1);
        }
        catch (Exception var3)
        {
            ;
        }
    }

    public void addSpeaker(String var1)
    {
        this.speakers.add(var1);
    }

    public void removeSpeaker(String var1)
    {
        int var2;

        if ((var2 = this.speakers.indexOf(var1)) != -1)
        {
            this.speakers.remove(var2);
        }
    }

    public String clidToUsername(String var1)
    {
        String var2 = "";

        for (int var3 = 0; var3 < this.users.size(); ++var3)
        {
            if (((TS3User)this.users.get(var3)).getClid().equals(var1))
            {
                return ((TS3User)this.users.get(var3)).getUsername().replaceAll("\\\\s", "").replaceAll("[^a-zA-Z0-9\\s]", "");
            }
        }

        return var2;
    }

    public void clearSpeakers()
    {
        this.speakers.clear();
    }

    public void listAllUsers()
    {
        for (int var1 = 0; var1 < this.users.size(); ++var1)
        {
            this.mc.thePlayer.addChatMessage(new ChatComponentText(((TS3User)this.users.get(var1)).getClid() + "::" + ((TS3User)this.users.get(var1)).getUsername()));
        }
    }
}
