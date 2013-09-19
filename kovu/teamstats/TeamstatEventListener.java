package kovu.teamstats;

import net.ae97.teamstats.api.TeamstatHandler;
import net.ae97.teamstats.api.TeamstatListener;
import net.ae97.teamstats.api.events.NewFriendEvent;
import net.ae97.teamstats.api.events.NewRequestEvent;
import net.ae97.teamstats.api.events.StatsUpdatedEvent;

public class TeamstatEventListener implements TeamstatListener
{
	@TeamstatHandler
	public void onNewFriend(NewFriendEvent event)
	{
		
	}
	
	@TeamstatHandler
	public void newRequestHandler(NewRequestEvent event)
	{
		
	}
	
	@TeamstatHandler
	public void statsUpdatedEvent(StatsUpdatedEvent event)
	{
		
	}
}
