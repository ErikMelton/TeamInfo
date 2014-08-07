package kovu.teaminfo.teamspeak;

import java.util.ArrayList;

import kovu.teaminfo.ts3api.TS3User;

public class MSguiConversation
{
    private TS3User user;
    private ArrayList conversation = new ArrayList();
    private boolean isVisible = false;
    private int scroll = 0;
    private boolean hasNewMessage = false;

    public MSguiConversation(TS3User var1, String var2)
    {
        this.user = var1;
        int var3 = var2.length() / 50;

        if (var3 == 0)
        {
            this.conversation.add(0, var2);
            this.hasNewMessage = true;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                this.conversation.add(0, var2.substring(var4 * 50, var4 * 50 + 50));
            }
        }

        this.conversation.add(var2);
        this.hasNewMessage = true;
    }

    public MSguiConversation(TS3User var1)
    {
        this.user = var1;
    }

    public void addMessage(String var1)
    {
        int var2 = var1.length() / 50;

        if (var2 == 0)
        {
            this.conversation.add(0, var1);
            this.hasNewMessage = true;
        }
        else
        {
            for (int var3 = 0; var3 < var2; ++var3)
            {
                this.conversation.add(0, var1.substring(var3 * 50, var3 * 50 + 50));
            }
        }
    }

    public String getUsername()
    {
        return this.user.getUsername();
    }

    public String getCid()
    {
        return this.user.getCid();
    }

    public String getClid()
    {
        return this.user.getClid();
    }

    public String ensureFit()
    {
        String var1 = this.user.getUsername().replaceAll("[^\\x20-\\x7e]", "").replaceAll("\\\\s", " ");
        return var1.length() > 10 ? var1.substring(0, 9) + "..." : var1;
    }

    public ArrayList getMessageList()
    {
        return this.conversation;
    }

    public void scrollUp()
    {
        ++this.scroll;
    }

    public void scrollDown()
    {
        --this.scroll;
    }

    public int getScroll()
    {
        return this.scroll;
    }

    public boolean hasNewMessage()
    {
        return this.hasNewMessage;
    }

    public void read()
    {
        this.hasNewMessage = false;
    }
}
