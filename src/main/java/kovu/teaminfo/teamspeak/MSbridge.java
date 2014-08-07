package kovu.teaminfo.teamspeak;

import java.util.ArrayList;

import kovu.teaminfo.ts3api.TS3Channel;
import kovu.teaminfo.ts3api.TS3User;
import kovu.teaminfo.ts3api.TSBridge;

public class MSbridge extends TSBridge
{
    private String toServer = "";
    private MSgig ingame;

    public MSbridge(MSgig var1)
    {
        this.ingame = var1;
        this.main();
    }

    public void main()
    {
        try
        {
            this.connect();
        }
        catch (Exception var2)
        {
            ;
        }
    }

    public void handleInput(String var1)
    {
        this.toServer = var1;

        if (this.toServer.startsWith("cmsg"))
        {
            this.sendChannelMessage(this.toServer.substring(5));
        }
        else if (this.toServer.startsWith("smsg"))
        {
            this.sendServerMessage(this.toServer.substring(5));
        }
        else if (this.toServer.startsWith("pmsg"))
        {
            String[] var2 = this.toServer.split(" ", 3);
            this.sendPrivateMessage(var2[1], var2[2]);
        }
        else if (this.toServer.equalsIgnoreCase("channellist"))
        {
            this.getChannelList();
        }
        else if (this.toServer.equalsIgnoreCase("all users"))
        {
            this.getAllUsers();
        }
        else if (this.toServer.equalsIgnoreCase("enable poke"))
        {
            this.sendEnablePokeNotication();
        }
        else if (this.toServer.equalsIgnoreCase("disable poke"))
        {
            this.sendDisablePokeNotication();
        }
        else if (this.toServer.equalsIgnoreCase("enable message"))
        {
            this.sendEnableTextMessageNotication();
        }
        else if (this.toServer.equalsIgnoreCase("disable message"))
        {
            this.sendDisableTextMessageNotication();
        }
        else if (this.toServer.equalsIgnoreCase("enable talk"))
        {
            this.sendEnableTalkStatusChangeNotifcation();
        }
        else if (this.toServer.equalsIgnoreCase("disable talk"))
        {
            this.sendDisableTalkStatusChangeNotifcation();
            this.ingame.clearSpeakers();
        }
        else
        {
            this.sendConsoleMessage(this.toServer);
        }
    }

    protected void onConnect()
    {
        this.ingame.serverGui.isConnected = true;
        this.ingame.mod.isConnected = true;
        this.getAllUsers();
    }

    protected void onChannelTextMessage(String var1, String var2, String var3)
    {
        var2 = var2.replaceAll("\\\\s", " ");
        int var4 = var2.length() / 50;
        System.out.println(var4);

        if (var4 == 0)
        {
            this.ingame.chatGui.channelChat.add(0, "\u00a74" + var3.replaceAll("[^\\x20-\\x7e]", "") + "\u00a7f:" + var2.replaceAll("\\\\s", " "));
        }
        else
        {
            this.ingame.chatGui.channelChat.add(0, "\u00a74" + var3.replaceAll("[^\\x20-\\x7e]", "") + "\u00a7f:" + var2.substring(0, 50));

            for (int var5 = 0; var5 < var4 - 1; ++var5)
            {
                this.ingame.chatGui.channelChat.add(0, var2.substring((var5 + 1) * 50, (var5 + 1) * 50 + 50));

                if (this.ingame.chatGui.channelChat.size() > 20)
                {
                    this.ingame.chatGui.channelChat.remove(20);
                }
            }
        }

        this.ingame.chatGui.channelHasNewMessage = true;

        if (this.ingame.chatGui.channelChat.size() > 20)
        {
            this.ingame.chatGui.channelChat.remove(20);
        }
    }

    protected void onClose()
    {
        this.ingame.serverGui.isConnected = false;
        this.ingame.mod.isConnected = false;
    }

    protected void onServerTextMessage(String var1, String var2, String var3) {}

    protected void onPrivateTextMessage(String var1, String var2, String var3, String var4, String var5)
    {
        this.ingame.chatGui.handlePrivateMessage(var2.replaceAll("\\\\s", " "), var3, var4, var5);
    }

    protected void onTalkStatusChange(String var1, String var2, String var3)
    {
        if (var2.equals("1"))
        {
            this.ingame.addSpeaker(var3);
        }
        else
        {
            this.ingame.removeSpeaker(var3);
        }
    }

    protected void onChannelList(TS3Channel[] var1)
    {
        ArrayList var2 = new ArrayList();
        ArrayList var3 = new ArrayList(var1.length);
        String var4 = "0";
        int var5 = 0;
        boolean var6 = false;
        this.ingame.channels.clear();
        this.ingame.serverGui.serverList.clear();

        for (int var7 = 0; var7 < var1.length; ++var7)
        {
            var3.add(var1[var7]);
        }

        int var8 = var3.size();

        while (var5 < var8)
        {
            TS3Channel var9 = (TS3Channel)var3.get(var5);

            if (var9.getChannelOrder().equals(var4) && var9.getPid().equals("0"))
            {
                var2.addAll(this.getSubChannels(var3, var9));
                var4 = var9.getCid();
                var5 = 0;
            }
            else
            {
                ++var5;
            }
        }

        this.ingame.channels.addAll(var2);
        this.ingame.serverGui.fillServerList();
    }

    protected void onTeamspeakClientIsNotActive() {}

    protected void onPoke(String var1, String var2, String var3, String var4)
    {
        this.addChatMessage("\u00a74" + var3 + "\u00a7f pokes you:" + var4);
    }

    protected void onAllUsers(TS3User[] var1)
    {
        this.ingame.users.clear();
        this.ingame.clearSpeakers();

        for (int var2 = 0; var2 < var1.length; ++var2)
        {
            this.ingame.users.add(var1[var2]);
        }
    }

    protected void onConsoleMessage(String var1) {}

    private void addChatMessage(String var1)
    {
        this.ingame.addChatMessage(var1.replaceAll("\\\\s", " "));
    }

    private ArrayList getSubChannels(ArrayList var1, TS3Channel var2)
    {
        ArrayList var3 = new ArrayList();
        var3.add(0, var2);
        ArrayList var4 = new ArrayList();
        String var5 = "0";
        int var6 = 0;
        boolean var7 = false;

        for (int var8 = 0; var8 < var1.size(); ++var8)
        {
            TS3Channel var9 = (TS3Channel)var1.get(var8);

            if (var9.getPid().equals(var2.getCid()))
            {
                var4.add(var4.size(), var9);
            }
        }

        int var10 = var4.size();

        while (var6 < var10)
        {
            TS3Channel var11 = (TS3Channel)var4.get(var6);

            if (var11.getChannelOrder().equals(var5))
            {
                var3.addAll(var3.size(), this.getSubChannels(var1, (TS3Channel)var4.get(var6)));
                var5 = ((TS3Channel)var4.get(var6)).getCid();
                var6 = 0;
            }
            else
            {
                ++var6;
            }
        }

        return var3;
    }
}
