package kovu.teaminfo.handlers;

import java.io.File;

import kovu.teaminfo.TeamInfo;
import kovu.teaminfo.bot.Bot;
import kovu.teaminfo.util.Vars;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler 
{
	public static Configuration config;
	
	public static boolean testValue = false;
	
	public static void init(File configFile)
	{
		if(config == null)
		{
			config = new Configuration(configFile);		
			loadConfiuration();
		}
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equalsIgnoreCase("teaminfo"))
		{
			loadConfiuration();
		}
	}
		
	public static void loadConfiuration()
	{
		Vars.username = config.getString("IRC Username", Configuration.CATEGORY_GENERAL, "nickname", "Your IRC password for esper.net");
		Vars.password = config.getString("IRC Password", Configuration.CATEGORY_GENERAL, "password", "Your IRC password for esper.net");
		
		TeamInfo.nick = Vars.username;
		TeamInfo.password = Vars.password;
		
		if(config.hasChanged())
		{
			config.save();
		}

		System.out.println("SOMETHING CHANGED!XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		if(TeamInfo.bot != null)
		{
			TeamInfo.bot.disconnect();
			
			TeamInfo.bot = new Bot(Vars.username, Vars.password);
			TeamInfo.bot.setVerbose(true);
			try 
			{
				TeamInfo.bot.connect(TeamInfo.host);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			TeamInfo.bot.joinChannel(TeamInfo.channel);
		}
		
	}
}