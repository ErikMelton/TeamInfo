package kovu.teaminfo.bot;

import java.util.Date;

import kovu.teaminfo.TeamInfo;
import kovu.teaminfo.gui.GuiAddReject;
import kovu.teaminfo.util.Util;
import net.minecraft.client.Minecraft;

import org.jibble.pircbot.PircBot;

public class Bot extends PircBot
{
	public Bot(String name)
	{
		setName(name);
	}
	
	public Bot(String name, String pass)
	{
		System.out.println("[TeamInfo]: USN: " + name + " PAS: " + pass);
		setName(name);
		identify(pass);
	}
	
	public void onMessage(String channel, String sender, String login, String hostName, String message)
	{

	}
	
	public void onPrivateMessage(String sender, String login, String hostname, String message)
	{
		if(message.contains("ADDME"))
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiAddReject(Minecraft.getMinecraft().currentScreen, sender, message));
		}
		else if(message.contains("REJECT"))
		{
			Minecraft.getMinecraft().thePlayer.sendChatMessage("Your request was rejected by " + sender);
		}
		else if(message.contains("REMOVE"))
		{
			TeamInfo.forceremove(sender);
		}
		else if(message.contains("UPDATE"))
		{
			TeamInfo.interpretUpdate(message, sender);
		}
		else if(message.contains("ADDED"))
		{
			Minecraft.getMinecraft().thePlayer.sendChatMessage("Your request was accepted by " + sender);
			TeamInfo.add(sender);
			TeamInfo.interpretUpdate(message, sender);
		}
	}
}