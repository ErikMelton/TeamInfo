package kovu.teamstats;

import cpw.mods.fml.common.Mod.EventHandler;
import net.ae97.teamstats.api.events.NewFriendEvent;
import net.ae97.teamstats.api.events.NewRequestEvent;
import net.ae97.teamstats.api.events.StatsUpdatedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class TeamStatsHookContainer 
{
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void getNewFriends(NewFriendEvent event)
	{
		
	}
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void getNewRequests(NewRequestEvent event)
	{
		
	}
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void statUpdated(StatsUpdatedEvent event)
	{
		System.out.println("Stats updated");
	}
}