package kovu.teaminfo;

import net.minecraft.client.gui.GuiChat;

public class GuiTeamInfoChat extends GuiChat
{
	private boolean dragging;
	
	public GuiTeamInfoChat()
	{
		super();
		dragging = false;
	}
	
	public void initGui()
	{
		System.out.println("[TeamInfo] Init'd CHAT");
	}
}
