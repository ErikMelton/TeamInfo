package kovu.teamstats;

import cpw.mods.fml.common.Mod.EventHandler;
import net.ae97.teamstats.api.events.NewFriendEvent;
import net.ae97.teamstats.api.events.NewRequestEvent;
import net.ae97.teamstats.api.events.StatsUpdatedEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class TeamStatsHookContainer 
{
	@ForgeSubscribe
	public void getNewFriends(NewFriendEvent event)
	{
		
	}
	
	@ForgeSubscribe
	public void getNewRequests(NewRequestEvent event)
	{
		
	}
	
	@ForgeSubscribe
	public void statUpdated(StatsUpdatedEvent event)
	{
		System.out.println("Stats updated");
	}	
}
