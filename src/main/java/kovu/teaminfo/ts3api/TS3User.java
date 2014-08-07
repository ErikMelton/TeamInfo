package kovu.teaminfo.ts3api;

public class TS3User
{
    private String clid;
    private String cid;
    private String cdbid;
    private String username;

    public TS3User(String var1, String var2, String var3, String var4)
    {
        this.clid = var1;
        this.cid = var2;
        this.cdbid = var3;
        this.username = var4;
    }

    public String getClid()
    {
        return this.clid;
    }

    public void setClid(String var1)
    {
        this.clid = var1;
    }

    public String getCid()
    {
        return this.cid;
    }

    public void setCid(String var1)
    {
        this.cid = var1;
    }

    public String getCdbid()
    {
        return this.cdbid;
    }

    public void setCdbid(String var1)
    {
        this.cdbid = var1;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String var1)
    {
        this.username = var1;
    }
}
