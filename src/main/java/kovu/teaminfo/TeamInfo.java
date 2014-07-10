package kovu.teaminfo;

import java.util.HashMap;
import java.util.Map;

import kovu.teaminfo.bot.Bot;
import kovu.teaminfo.gui.GuiTeamInfoIngame;
import kovu.teaminfo.handlers.ConfigurationHandler;
import kovu.teaminfo.handlers.KeyInputHandler;
import kovu.teaminfo.handlers.TickHandler;
import kovu.teaminfo.proxies.CommonProxy;
import kovu.teaminfo.util.KeyBindings;
import kovu.teaminfo.util.Util;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;

@Mod(modid = "teaminfo", version = "1.0", guiFactory = "kovu.teaminfo.handlers.TeamInfoGuiFactory")
public class TeamInfo extends Thread 
{	
	@SidedProxy(clientSide = "kovu.teaminfo.proxies.ClientProxy", serverSide = "kovu.teaminfo.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	public static TeamInfo instance;
	
	public static Bot bot;
	
	public static String channel = "#teaminfomod";
	public static String nick;
	public static String password;
	public static String host = "irc.esper.net";
	
	public static Map<String, Object> players = new HashMap<String, Object>();

	public static Map<String, Boolean> showgui = new HashMap<String, Boolean>();

	public static Map<String, Integer> playershealth = new HashMap<String, Integer>();
	public static Map<String, Integer> playersprevhealth = new HashMap<String, Integer>();
	public static Map<String, Integer> playershunger = new HashMap<String, Integer>();
	public static Map<String, Integer> playersarmour = new HashMap<String, Integer>();

	public static Map<String, Boolean> playerpoison = new HashMap<String, Boolean>();
	public static Map<String, Boolean> playerfireresist = new HashMap<String, Boolean>();
	public static Map<String, Boolean> playerweakness = new HashMap<String, Boolean>();
	public static Map<String, Boolean> playerswift = new HashMap<String, Boolean>();
	public static Map<String, Boolean> playerslow = new HashMap<String, Boolean>();
	public static Map<String, Boolean> playerregen = new HashMap<String, Boolean>();
	public static Map<String, Boolean> playerhungerdrain = new HashMap<String, Boolean>();
	public static Map<String, Boolean> playeronfire = new HashMap<String, Boolean>();

	public static Map<String, Integer> playerposX = new HashMap<String, Integer>();
	public static Map<String, Integer> playerposY = new HashMap<String, Integer>();
	public static Map<String, Integer> playerposZ = new HashMap<String, Integer>();

	public static Map<String, Integer> toplefti = new HashMap<String, Integer>();
	public static Map<String, Integer> topleftj = new HashMap<String, Integer>();
	public static Map<String, Boolean> dragable = new HashMap<String, Boolean>();
		
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) 
	{
		KeyBindings keys = new KeyBindings();
		keys.init();
				
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		FMLCommonHandler.instance().bus().register(new TickHandler());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		proxy.setupRemoteTexture();
		
		instance = this;
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if(nick.equalsIgnoreCase("username"))
		{
			nick = Minecraft.getMinecraft().getSession().getUsername();
		}
		this.start();
	}
	
	public static void syncConfig()
	{
	}
	
	public static void requestadd(String to)
	{
		bot.sendMessage(to, " ADDME " + "HP"
			+ Util.mc.thePlayer.getHealth() + "HUNGER"
			+ Util.mc.thePlayer.getFoodStats().getFoodLevel()
			+ "ARMOUR" + Util.mc.thePlayer.getTotalArmorValue()
			+ "POISON"
			+ Util.mc.thePlayer.isPotionActive(Potion.poison)
			+ "FIRERESIST"
			+ Util.mc.thePlayer.isPotionActive(Potion.fireResistance)
			+ "WEAKNESS"
			+ Util.mc.thePlayer.isPotionActive(Potion.weakness)
			+ "SWIFT"
			+ Util.mc.thePlayer.isPotionActive(Potion.moveSpeed)
			+ "SLOW"
			+ Util.mc.thePlayer.isPotionActive(Potion.moveSlowdown)
			+ "REGEN"
			+ Util.mc.thePlayer.isPotionActive(Potion.regeneration)
			+ "HUNGERDRAIN"
			+ Util.mc.thePlayer.isPotionActive(Potion.hunger)
			+ "ONFIRE" + Util.mc.thePlayer.isBurning() + "POSX"
			+ (int) Util.mc.thePlayer.posX + "POSY"
			+ (int) Util.mc.thePlayer.posY + "POSZ"
			+ (int) Util.mc.thePlayer.posZ);
	}

	public static void acceptadd(String to) 
	{
		bot.sendMessage(to, " ADDED " + "HP"
			+ Util.mc.thePlayer.getHealth() + "HUNGER"
			+ Util.mc.thePlayer.getFoodStats().getFoodLevel()
			+ "ARMOUR" + Util.mc.thePlayer.getTotalArmorValue()
			+ "POISON"
			+ Util.mc.thePlayer.isPotionActive(Potion.poison)
			+ "FIRERESIST"
			+ Util.mc.thePlayer.isPotionActive(Potion.fireResistance)
			+ "WEAKNESS"
			+ Util.mc.thePlayer.isPotionActive(Potion.weakness)
			+ "SWIFT"
			+ Util.mc.thePlayer.isPotionActive(Potion.moveSpeed)
			+ "SLOW"
			+ Util.mc.thePlayer.isPotionActive(Potion.moveSlowdown)
			+ "REGEN"
			+ Util.mc.thePlayer.isPotionActive(Potion.regeneration)
			+ "HUNGERDRAIN"
			+ Util.mc.thePlayer.isPotionActive(Potion.hunger)
			+ "ONFIRE" + Util.mc.thePlayer.isBurning() + "POSX"
			+ (int) Util.mc.thePlayer.posX + "POSY"
			+ (int) Util.mc.thePlayer.posY + "POSZ"
			+ (int) Util.mc.thePlayer.posZ);
		add(to);
	}

	public static void rejectadd(String to) 
	{
		bot.sendMessage(to, "REJECT");
	}

	// ADDME HP20.0HUNGER20ARMOUR0POISONfalseFIRERESISTfalseWEAKNESSfalseSWIFTfalseSLOWfalseREGENfalseHUNGERDRAINfalseONFIREfalsePOSX108POSY69POSZ242
	public static void sendUpdate(String to)
	{
		bot.sendMessage(to, " UPDATE " + "HP"
			+ Util.mc.thePlayer.getHealth() + "HUNGER"
			+ Util.mc.thePlayer.getFoodStats().getFoodLevel()
			+ "ARMOUR" + Util.mc.thePlayer.getTotalArmorValue()
			+ "POISON"
			+ Util.mc.thePlayer.isPotionActive(Potion.poison)
			+ "FIRERESIST"
			+ Util.mc.thePlayer.isPotionActive(Potion.fireResistance)
			+ "WEAKNESS"
			+ Util.mc.thePlayer.isPotionActive(Potion.weakness)
			+ "SWIFT"
			+ Util.mc.thePlayer.isPotionActive(Potion.moveSpeed)
			+ "SLOW"
			+ Util.mc.thePlayer.isPotionActive(Potion.moveSlowdown)
			+ "REGEN"
			+ Util.mc.thePlayer.isPotionActive(Potion.regeneration)
			+ "HUNGERDRAIN"
			+ Util.mc.thePlayer.isPotionActive(Potion.hunger)
			+ "ONFIRE" + Util.mc.thePlayer.isBurning() + "POSX"
			+ (int) Util.mc.thePlayer.posX + "POSY"
			+ (int) Util.mc.thePlayer.posY + "POSZ"
			+ (int) Util.mc.thePlayer.posZ);
	}
	
	public static void interpretUpdate(String update, String pName) 
	{
		String name = pName;
		String health = update.substring(update.indexOf("HP") + 2, update.indexOf("HUNGER"));
		String hunger = update.substring(update.indexOf("HUNGER") + 6, update.indexOf("ARMOUR"));
		String armour = update.substring(update.indexOf("ARMOUR") + 6, update.indexOf("POISON"));
		String poison = update.substring(update.indexOf("POISON") + 6, update.indexOf("FIRERESIST"));
		String fireresist = update.substring(update.indexOf("FIRERESIST") + 10, update.indexOf("WEAKNESS"));
		String weakness = update.substring(update.indexOf("WEAKNESS") + 8, update.indexOf("SWIFT"));
		String swift = update.substring(update.indexOf("SWIFT") + 5, update.indexOf("SLOW"));
		String slow = update.substring(update.indexOf("SLOW") + 4, update.indexOf("REGEN"));
		String regen = update.substring(update.indexOf("REGEN") + 5, update.indexOf("HUNGERDRAIN"));
		String hungerdrain = update.substring(update.indexOf("HUNGERDRAIN") + 11, update.indexOf("ONFIRE"));
		String onfire = update.substring(update.indexOf("ONFIRE") + 6, update.indexOf("POSX"));
		String posX = update.substring(update.indexOf("POSX") + 4, update.indexOf("POSY"));
		String posY = update.substring(update.indexOf("POSY") + 4, update.indexOf("POSZ"));
		String posZ = update.substring(update.indexOf("POSZ") + 4);

		if (playershealth.containsKey(name)) 
		{
			playersprevhealth.put(name, playershealth.get(name));
		}

		playershealth.put(name, Integer.valueOf((int)Float.parseFloat(health)));
		playershunger.put(name, Integer.valueOf(Integer.parseInt(hunger)));
		playersarmour.put(name, Integer.valueOf(Integer.parseInt(armour)));

		playeronfire.put(name, Boolean.valueOf(Boolean.parseBoolean(onfire)));
		playerpoison.put(name, Boolean.valueOf(Boolean.parseBoolean(poison)));
		playerfireresist.put(name, Boolean.valueOf(Boolean.parseBoolean(fireresist)));
		playerweakness.put(name, Boolean.valueOf(Boolean.parseBoolean(weakness)));
		playerswift.put(name, Boolean.valueOf(Boolean.parseBoolean(swift)));
		playerslow.put(name, Boolean.valueOf(Boolean.parseBoolean(slow)));
		playerregen.put(name, Boolean.valueOf(Boolean.parseBoolean(regen)));
		playerhungerdrain.put(name, Boolean.valueOf(Boolean.parseBoolean(hungerdrain)));
		playerposX.put(name, Integer.valueOf(Integer.parseInt(posX)));
		playerposY.put(name, Integer.valueOf(Integer.parseInt(posY)));
		playerposZ.put(name, Integer.valueOf(Integer.parseInt(posZ)));
	}

	public static void add(String addedPerson)
	{
		players.put(addedPerson, Boolean.valueOf(true));
		GuiTeamInfoIngame.playerInfo.put(addedPerson, Boolean.valueOf(false));
		showgui.put(addedPerson, Boolean.valueOf(true));

		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft(), Util.mc.displayWidth, Util.mc.displayHeight);
		int k = scaledresolution.getScaledWidth();

		toplefti.put(addedPerson, Integer.valueOf(k - 110));
		topleftj.put(addedPerson, Integer.valueOf(players.size() * 35));
	}
	
	public static void remove(String target) 
	{
		players.remove(target);
		
		bot.sendMessage(target, "REMOVE");
	}
	
	public static void forceremove(String s)
	{
		players.remove(s);
		Minecraft.getMinecraft().thePlayer.sendChatMessage(s + " requested that you stop sharing info.");
	}

	public void run()
	{	
		if(password.equalsIgnoreCase("password") && nick.equalsIgnoreCase("username"))
		{
			bot = new Bot(nick);
		}
		else
		{
			bot = new Bot(nick, password);
		}
		bot.setVerbose(true);
		try 
		{
			bot.connect(host);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		bot.joinChannel(channel);
	}
}