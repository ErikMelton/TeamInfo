package kovu.teaminfo;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

public class GuiRemoveFromTeam extends GuiScreen 
{
	private GuiScreen parentScreen;
	int players = 0;
	int extra = 0;

	public GuiRemoveFromTeam(GuiScreen parentscreen) 
	{
		this.parentScreen = parentscreen;
	}

	public void updateScreen()
	{
		if (Keyboard.isKeyDown(15))
		{
			Util.showlist = true;
		}
		else
		{
		}
   }

	public void drawScreen(int i, int j, float f)
	{
		int extra2 = 0;

		if ((TeamInfo.players.size() % 2 == 0) && (TeamInfo.players.size() != 0)) 
		{
			extra2 = -1;
		}

		drawRect(width / 2 - 120, height / 2 - 50, width / 2 + 120, height / 2 + 30 + 23 * (this.players / 2 + extra2), 1610612736);

		drawCenteredString(fontRendererObj, "Remove Player", width / 2, height / 2 - 43, 16777215);

		if (TeamInfo.players.size() == 0)
		{
			drawCenteredString(fontRendererObj, "No players in list", width / 2, height / 2 - 18, 16777215);
		}
		super.drawScreen(i, j, f);
	}

	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();

		players = 0;
		for (String player : TeamInfo.players.keySet()) 
		{
			if (this.players % 2 == 0)
			{
				buttonList.add(new GuiButton(this.players, width / 2 - 90, height / 2 - 20 + 23 * (this.players / 2), 80, 20, player));
			} 
			else 
			{
				buttonList.add(new GuiButton(this.players, width / 2 + 10, height / 2 - 20 + 23 * (this.players / 2), 80, 20, player));
			}

			this.players += 1;
		}
		if ((this.players % 2 != 0) || (this.players == 0)) 
		{
			this.extra = 1;
		}
		this.buttonList.add(new GuiButton(this.players, width / 2 - 30, height / 2 - 20 + 23 * (this.players / 2 + this.extra), 60, 20, "Back"));
	}

	public void onGuiClosed() 
	{
		Keyboard.enableRepeatEvents(false);
	}

	public void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == this.players) 
		{
			mc.displayGuiScreen(this.parentScreen);
		}

		int playerscheck = 0;

		for (String s : TeamInfo.players.keySet()) 
		{
			if (guibutton.id == playerscheck) 
			{
				TeamInfo.remove(s);
				mc.displayGuiScreen(new GuiRemoveFromTeam(new GuiTeamInfo(null)));
				return;
			}
			playerscheck++;
		}
	}

	public void mouseCicked(int i, int j, int k) 
	{
		super.mouseClicked(i, j, k);
	}
}