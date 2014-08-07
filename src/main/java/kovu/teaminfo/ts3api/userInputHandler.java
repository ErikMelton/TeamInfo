package kovu.teaminfo.ts3api;

import java.util.ArrayList;

public class userInputHandler
{
    private String userMessage = null;
    private String lastMessage = null;
    private ArrayList queue = new ArrayList();

    public boolean isReady()
    {
        return this.queue.size() > 0;
    }

    public String getMessage()
    {
        String var1 = (String)this.queue.get(this.queue.size() - 1);
        this.queue.remove(this.queue.indexOf(var1));
        this.lastMessage = var1;
        return var1;
    }

    public void setMessage(String var1)
    {
        this.queue.add(0, var1);
        this.lastMessage = var1;
    }

    public String getLastMessage()
    {
        return this.lastMessage;
    }

    public void setLastMessage(String var1)
    {
        this.lastMessage = var1;
    }
}
