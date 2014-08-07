package kovu.teaminfo.ts3api;

public class TS3Channel
{
    private String cid;
    private String pid;
    private String channelOrder;
    private String channelName;
    private String isSubscribed;

    public TS3Channel(String var1, String var2, String var3, String var4, String var5)
    {
        this.setCid(var1);
        this.setPid(var2);
        this.setChannelOrder(var3);
        this.setChannelName(var4);
        this.setIsSubscribed(var5);
    }

    public String getCid()
    {
        return this.cid;
    }

    public void setCid(String var1)
    {
        this.cid = var1;
    }

    public String getPid()
    {
        return this.pid;
    }

    public void setPid(String var1)
    {
        this.pid = var1;
    }

    public String getChannelOrder()
    {
        return this.channelOrder;
    }

    public void setChannelOrder(String var1)
    {
        this.channelOrder = var1;
    }

    public String getChannelName()
    {
        return this.channelName;
    }

    public void setChannelName(String var1)
    {
        this.channelName = var1;
    }

    public String getIsSubscribed()
    {
        return this.isSubscribed;
    }

    public void setIsSubscribed(String var1)
    {
        this.isSubscribed = var1;
    }
}
