package kovu.teaminfo.gui;

import kovu.teaminfo.handlers.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class TeamInfoConfigGui extends GuiConfig
{
	public TeamInfoConfigGui(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), "teaminfo", false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
	}
}