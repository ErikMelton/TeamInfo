package kovu.teaminfo;

import java.util.Map;
import java.util.Set;

import net.minecraft.client.Minecraft;

public class Util 
{
	public static Minecraft mc = Minecraft.getMinecraft();
	public static boolean step = false;
	public static int stepheight = 1;
	public static boolean knockback = false;
	public static boolean blockhack = true;
	public static boolean isBlocking = false;
	public static boolean fraps = false;
	public static float scale = 1.0F;

	public static boolean showlist = false;
	public static boolean hunger = true;
	public static boolean armour = true;
	public static boolean noarmour = true;
	public static boolean coords = false;
	
	public static int getIndex(Map map, Object value)
	{
		int result = 0;
		
		for (Object entry:map.keySet()) 
		{
			if(entry.equals(value))
				return result;
			result++;
		}
		return -1;

	}
	
	public static int getIndex(Set<? extends Object> set, Object value)
	{
		int result = 0;
		
		for(Object entry:set)
		{
			if(entry.equals(value)) 
				return result;
			result++;
		}
		return -1;
	}
}