package kovu.teaminfo.handlers;

import java.util.Set;

import kovu.teaminfo.gui.TeamInfoConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public class TeamInfoGuiFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft arg0)
	{
		
	}
	
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return TeamInfoConfigGui.class;
	}
	
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() 
	{
		return null;
	}
	
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement arg0) 
	{
		return null;
	}
}
