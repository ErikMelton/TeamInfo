package kovu.teaminfo.gui;

import kovu.teaminfo.util.Vars;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiTeamInfoConfig extends GuiScreen 
{
	private GuiScreen parentScreen;

	public GuiTeamInfoConfig(GuiScreen parentscreen) 
	{
		this.parentScreen = parentscreen;
	}

	public void updateScreen() 
	{
		
	}

	public void drawScreen(int i, int j, float f)
	{
		drawRect(width / 2 - 165, height / 2 - 20, width / 2 + 165, height / 2 + 60, 1610612736);
		drawCenteredString(fontRendererObj, EnumChatFormatting.RED + "Config:", width / 2, height / 2 - 13, 16777215);
		super.drawScreen(i, j, f);
	}

	public void initGui() 
	{
		buttonList.clear();

		buttonList.add(new GuiButton(0, width / 2 - 155, height / 2 + 5, 100, 20, new StringBuilder().append("Scale ").append((int) (Vars.scale * 100.0F)).append("%").toString()));
		buttonList.add(new GuiButton(1, width / 2 - 50, height / 2 + 5, 100, 20, new StringBuilder().append("Armor ").append(Vars.armour ? "On" : "Off").toString()));
		buttonList.add(new GuiButton(2, width / 2 + 55, height / 2 + 5, 100, 20, new StringBuilder().append("EmptyArmor ").append(Vars.noarmour ? "On" : "Off").toString()));

		buttonList.add(new GuiButton(3, width / 2 - 155, height / 2 + 5 + 24, 100, 20, new StringBuilder().append("Co-ords ").append(Vars.coords ? "On" : "Off").toString()));
		buttonList.add(new GuiButton(4, width / 2 - 50, height / 2 + 5 + 24, 100, 20, new StringBuilder().append("Hunger ").append(Vars.hunger ? "On" : "Off").toString()));
		buttonList.add(new GuiButton(5, width / 2 + 55, height / 2 + 5 + 24, 100, 20, "Back"));
	}

	public void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0) 
		{
			if (Vars.scale != 1.5F)
			{
				Vars.scale = (float) (Vars.scale + 0.25D);
			}
			else
			{
				Vars.scale = 0.5F;
			}

			guibutton.displayString = new StringBuilder().append("Scale ").append((int) (Vars.scale * 100.0F)).append("%").toString();
		}
		if (guibutton.id == 1)
		{
			Vars.armour = !Vars.armour;

			guibutton.displayString = new StringBuilder().append("Armor ").append(Vars.armour ? "On" : "Off").toString();
		}
		if (guibutton.id == 2)
		{
			Vars.noarmour = !Vars.noarmour;

			guibutton.displayString = new StringBuilder().append("EmptyArmor ").append(Vars.noarmour ? "On" : "Off").toString();
		}
		if (guibutton.id == 3)
		{
			Vars.coords = !Vars.coords;

			guibutton.displayString = new StringBuilder().append("Co-ords ").append(Vars.coords ? "On" : "Off").toString();
		}
		if (guibutton.id == 4) 
		{
			Vars.hunger = !Vars.hunger;

			guibutton.displayString = new StringBuilder().append("Hunger ").append(Vars.hunger ? "On" : "Off").toString();
		}

		if (guibutton.id == 5) 
		{
			mc.displayGuiScreen(this.parentScreen);
		}
	}
}
