package kovu.teaminfo;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.src.ModLoader;

public class GuiTeamInfo extends GuiScreen {
	
	private GuiScreen parentScreen;
	
	public GuiTeamInfo(GuiScreen guiscreen) 
	{
		parentScreen = guiscreen;
	}

	public void updateScreen()
	{
		
	}
	
	public void drawScreen(int i, int j, float f) 
	{
		drawRect(width / 2 - 120, height / 2 - 20, width / 2 + 120, height / 2 + 30, 0x60000000);
		drawCenteredString(fontRenderer, "\247cTeamStats by \247bRainfur (Kovu)", width / 2, height / 2 - 13, 0xffffff);
		super.drawScreen(i, j, f);
	}
	
	public void initGui()
	{
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 102, height / 2 + 5, 60, 20, "Add"));
		controlList.add(new GuiButton(1, width / 2 - 30, height / 2 + 5, 60, 20, "Remove"));
		controlList.add(new GuiButton(2, width / 2 + 42, height / 2 + 5, 60, 20, "Config"));
	}
	
	public void actionPerformed(GuiButton guibutton)
	{
		if(guibutton.id == 0)
		{
			mc.displayGuiScreen(new GuiAddtoTeam(parentScreen));
		}
		if(guibutton.id == 1)
		{
			mc.displayGuiScreen(new GuiRemoveFromTeam(parentScreen));
		}
		if(guibutton.id == 2)
		{
			mc.displayGuiScreen(new GuiTeamInfoConfig(this));
		}
	}
}
