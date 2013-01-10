package kovu.teaminfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import friendmodapi.FriendModApi;

public class GuiRemoveFromTeam extends GuiScreen {

	private GuiScreen parentScreen;
	int players;
	int extra;
	int i;
	
	String[] friends;
	
	List list;
	
	public GuiAddtoTeam ga = new GuiAddtoTeam(parentScreen);
	
	public GuiRemoveFromTeam(GuiScreen guiscreen)
	{
		players = 0;
		extra = 0;
		parentScreen = guiscreen;
	}
	
	public void updateScreen()
	{
		if(Keyboard.isKeyDown(15))
		{
			Kovu.showlist = true;
		}
		else
		{
			Kovu.showlist = false;
		}
	}
	
	public void drawScreen(int i, int j, float f)
	{	
		try{
			
		i = FriendModApi.getFriends().length;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		byte byte0 = 0;
		if((i % 2!=0) && (i != 0))
		  {
			byte0 = -1;
		  }
		 
		drawRect(width / 2 - 120, height / 2 - 50, width / 2 + 120, height / 2 + 30 + 23 * (players / 2 + byte0), 0x6000000);
		drawCenteredString(fontRenderer, "Remove Player", width / 2, height / 2 - 43, 0xffffff);
		if(i == 0)
		{
			drawCenteredString(fontRenderer, "No players in list", width / 2, height / 2 - 18, 0xffffff);
		}
		
		 super.drawScreen(i, j, f);
	}
	
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		players = 0;
		friends = new String[2];
		friends[0] = "Rainfur";
		friends[1] = "Testing";
		try {
		//friends = FriendModApi.getFriends().clone();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(friends);
		java.util.List list = Arrays.asList(friends);
		
		for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            if(players % 2 == 0)
            {
                controlList.add(new GuiButton(players, width / 2 - 90, (height / 2 - 20) + 23 * (players / 2), 80, 20, s));
            } else
            {
                controlList.add(new GuiButton(players, width / 2 + 10, (height / 2 - 20) + 23 * (players / 2), 80, 20, s));
            }
            players++;
        }
		
		if(players % 2 != 0 || players == 0)
		{
			extra = 1;
		}
		controlList.add(new GuiButton(players, width / 2 - 30, (height / 2 - 20) + 23 * (players / 2 + extra), 60, 20, "Back"));
	}
	
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	public void actionPerformed(GuiButton guibutton)
	{
		if(guibutton.id == players)
		{
			mc.displayGuiScreen(parentScreen);
		}
		
		java.util.List list = Arrays.asList(friends);

        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            String s1 = (String)iterator.next();
            if(guibutton.id == i)
            {           	
                try {
                	System.out.println("Inside of" + i);
					FriendModApi.removeFriend(s1);
					controlList.remove(i);
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
                mc.displayGuiScreen(new GuiRemoveFromTeam(new GuiTeamInfo(null)));
                return;
            }
        i++;
        }
	}
	
	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
	}
}
