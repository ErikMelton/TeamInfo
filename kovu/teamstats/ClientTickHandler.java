package kovu.teamstats;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import kovu.teamstats.api.TeamStatsAPI;
import kovu.teamstats.gui.GuiHandler;
import kovu.teamstats.gui.GuiTeamInfoIngame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {
	
	private int ticksPassed = 0;
	public static String selectedFriend;
	
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

        if (type.equals(EnumSet.of(TickType.RENDER))) {
            onRenderTick();
        } else if (type.equals(EnumSet.of(TickType.CLIENT))) {
            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
            if (guiscreen != null) {
                onTickInGUI(guiscreen);
            } else {
                onTickInGame();
            }

        }
    }

    @Override
    public EnumSet<TickType> ticks() {

        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
    }

    @Override
    public String getLabel() {

        return null;
    }

    public void onTickInGUI(GuiScreen guiscreen) {
    	
    	Kovu.isInGUI = false;
    }

    public void onTickInGame() {
    	
    	Kovu.isInGUI= true;
    }

    public void onRenderTick() 
    {   	
    	ticksPassed++;
    	
    	if(Kovu.isInGUI == true)
    	{
    		GuiTeamInfoIngame.drawMain();
    	}
    	
    	if(ticksPassed == 200)
    	{
    		System.out.println(ticksPassed + " ticks passed");
    		ticksPassed = 0;
    		try
    		{
    			if(TeamStatsAPI.getAPI().getFriendRequests().length != 0)
    			{
    				System.out.println("Inside of Friend request loop");
    				String[] temp = TeamStatsAPI.getAPI().getFriendRequests();
    				List<String> ftemp = Arrays.asList(temp);
    				selectedFriend = ftemp.get(0);
    				System.out.println(selectedFriend);
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    			System.out.println("Client failed to get friend requests");
    		}
    	}
    }
}
