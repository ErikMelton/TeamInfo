package kovu.teaminfo.teamspeak;

import kovu.teaminfo.ts3api.TS3Channel;

public class MSguiListChannel extends MSguiButton
{
    private TS3Channel channel;

    public MSguiListChannel(TS3Channel var1)
    {
        this.channel = var1;
    }

    public TS3Channel getChannel()
    {
        return this.channel;
    }
}
