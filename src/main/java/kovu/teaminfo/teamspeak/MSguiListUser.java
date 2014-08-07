package kovu.teaminfo.teamspeak;

import kovu.teaminfo.ts3api.TS3User;

public class MSguiListUser extends MSguiButton
{
    private TS3User user;

    public MSguiListUser(TS3User var1)
    {
        this.user = var1;
    }

    public TS3User getUser()
    {
        return this.user;
    }
}
