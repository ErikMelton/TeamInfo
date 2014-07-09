package kovu.teaminfo;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiAddReject extends GuiScreen
{
	private GuiScreen parentScreen;
	private GuiTextField serverTextField;
	private String playername;
	private String fullMessage;

	public GuiAddReject(GuiScreen parentscreen, String player, String fullmessage) 
	{
		this.parentScreen = parentscreen;
		this.playername = player;
		this.fullMessage = fullmessage;
	}

	public void updateScreen() 
	{

	}

	public void drawScreen(int i, int j, float f)
	{
		drawRect(width / 2 - 120, height / 2 - 20, width / 2 + 120, height / 2 + 30, 1610612736);
		drawCenteredString(fontRendererObj, new StringBuilder().append("§c").append(this.playername).append("§f").toString() + " wants to share team info with you", width / 2, height / 2 - 13, 16777215);
		super.drawScreen(i, j, f);
	}

	public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 66, height / 2 + 5, 60, 20, "Add"));
		buttonList.add(new GuiButton(1, width / 2 + 6, height / 2 + 5, 60, 20, "Reject"));
	}

	public void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0) 
		{
			TeamInfo.acceptadd(playername);
			TeamInfo.interpretUpdate(this.fullMessage, playername);
			mc.displayGuiScreen(this.parentScreen);
		}
		if (guibutton.id == 1)
		{
			TeamInfo.rejectadd(this.playername);
			mc.displayGuiScreen(this.parentScreen);
		}
	}
}