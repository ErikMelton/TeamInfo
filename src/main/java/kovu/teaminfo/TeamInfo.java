package kovu.teaminfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kovu.teaminfo.bot.Bot;
import kovu.teaminfo.gui.GuiTeamInfoIngame;
import kovu.teaminfo.handlers.ConfigurationHandler;
import kovu.teaminfo.handlers.KeyInputHandler;
import kovu.teaminfo.handlers.TickHandler;
import kovu.teaminfo.player.Player;
import kovu.teaminfo.proxies.CommonProxy;
import kovu.teaminfo.teamspeak.MSgig;
import kovu.teaminfo.util.KeyBindings;
import kovu.teaminfo.util.Vars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "teaminfo", version = "1.0", guiFactory = "kovu.teaminfo.handlers.TeamInfoGuiFactory")
public class TeamInfo extends Thread 
{	
	@SidedProxy(clientSide = "kovu.teaminfo.proxies.ClientProxy", serverSide = "kovu.teaminfo.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	public static TeamInfo instance;
	
	public static Bot bot;
	
	public static ArrayList<Player> friends = new ArrayList<Player>();
	
	public static String channel = "#teaminfomod";
	public static String nick;
	public static String password;
	public static String host = "tiirc.ajwgeek.com";
			
    public static boolean isConnected = false;
    public static boolean[] cannot_toggle = new boolean[150];
    public static MSgig ingame;
    

    public void sendTSMessage(String var1)
    {
        ingame.sendTSMessage(var1);
    }

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
		
        ingame = new MSgig(Minecraft.getMinecraft(), Minecraft.getMinecraft().fontRenderer, this);

		instance = this;
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if(nick.equalsIgnoreCase("nickname"))
		{
			nick = Minecraft.getMinecraft().getSession().getUsername();
		}
		this.start();
	}
	
	public static void requestadd(String to)
	{
		bot.sendMessage(to, " ADDME " + "HP"
			+ Vars.mc.thePlayer.getHealth() + "HUNGER"
			+ Vars.mc.thePlayer.getFoodStats().getFoodLevel()
			+ "ARMOUR" + Vars.mc.thePlayer.getTotalArmorValue()
			+ "POISON"
			+ Vars.mc.thePlayer.isPotionActive(Potion.poison)
			+ "FIRERESIST"
			+ Vars.mc.thePlayer.isPotionActive(Potion.fireResistance)
			+ "WEAKNESS"
			+ Vars.mc.thePlayer.isPotionActive(Potion.weakness)
			+ "SWIFT"
			+ Vars.mc.thePlayer.isPotionActive(Potion.moveSpeed)
			+ "SLOW"
			+ Vars.mc.thePlayer.isPotionActive(Potion.moveSlowdown)
			+ "REGEN"
			+ Vars.mc.thePlayer.isPotionActive(Potion.regeneration)
			+ "HUNGERDRAIN"
			+ Vars.mc.thePlayer.isPotionActive(Potion.hunger)
			+ "ONFIRE" + Vars.mc.thePlayer.isBurning() + "POSX"
			+ (int) Vars.mc.thePlayer.posX + "POSY"
			+ (int) Vars.mc.thePlayer.posY + "POSZ"
			+ (int) Vars.mc.thePlayer.posZ);
	}

	public static void acceptadd(String to) 
	{
		bot.sendMessage(to, " ADDED " + "HP"
			+ Vars.mc.thePlayer.getHealth() + "HUNGER"
			+ Vars.mc.thePlayer.getFoodStats().getFoodLevel()
			+ "ARMOUR" + Vars.mc.thePlayer.getTotalArmorValue()
			+ "POISON"
			+ Vars.mc.thePlayer.isPotionActive(Potion.poison)
			+ "FIRERESIST"
			+ Vars.mc.thePlayer.isPotionActive(Potion.fireResistance)
			+ "WEAKNESS"
			+ Vars.mc.thePlayer.isPotionActive(Potion.weakness)
			+ "SWIFT"
			+ Vars.mc.thePlayer.isPotionActive(Potion.moveSpeed)
			+ "SLOW"
			+ Vars.mc.thePlayer.isPotionActive(Potion.moveSlowdown)
			+ "REGEN"
			+ Vars.mc.thePlayer.isPotionActive(Potion.regeneration)
			+ "HUNGERDRAIN"
			+ Vars.mc.thePlayer.isPotionActive(Potion.hunger)
			+ "ONFIRE" + Vars.mc.thePlayer.isBurning() + "POSX"
			+ (int) Vars.mc.thePlayer.posX + "POSY"
			+ (int) Vars.mc.thePlayer.posY + "POSZ"
			+ (int) Vars.mc.thePlayer.posZ);
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
			+ Vars.mc.thePlayer.getHealth() + "HUNGER"
			+ Vars.mc.thePlayer.getFoodStats().getFoodLevel()
			+ "ARMOUR" + Vars.mc.thePlayer.getTotalArmorValue()
			+ "POISON"
			+ Vars.mc.thePlayer.isPotionActive(Potion.poison)
			+ "FIRERESIST"
			+ Vars.mc.thePlayer.isPotionActive(Potion.fireResistance)
			+ "WEAKNESS"
			+ Vars.mc.thePlayer.isPotionActive(Potion.weakness)
			+ "SWIFT"
			+ Vars.mc.thePlayer.isPotionActive(Potion.moveSpeed)
			+ "SLOW"
			+ Vars.mc.thePlayer.isPotionActive(Potion.moveSlowdown)
			+ "REGEN"
			+ Vars.mc.thePlayer.isPotionActive(Potion.regeneration)
			+ "HUNGERDRAIN"
			+ Vars.mc.thePlayer.isPotionActive(Potion.hunger)
			+ "ONFIRE" + Vars.mc.thePlayer.isBurning() + "POSX"
			+ (int) Vars.mc.thePlayer.posX + "POSY"
			+ (int) Vars.mc.thePlayer.posY + "POSZ"
			+ (int) Vars.mc.thePlayer.posZ);
	}
	
	public static void interpretUpdate(String update, Player p) 
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

	public static void add(Player addedPerson)
	{
		players.put(addedPerson, Boolean.valueOf(true));
		GuiTeamInfoIngame.playerInfo.put(addedPerson, Boolean.valueOf(false));
		showgui.put(addedPerson, Boolean.valueOf(true));

		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft(), Vars.mc.displayWidth, Vars.mc.displayHeight);
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
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s + " requested that you stop sharing info."));
	}

	public void run()
	{	
		if(password.equalsIgnoreCase("password") && nick.equalsIgnoreCase("nickname"))
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
			bot.connect(host, 6665);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		bot.joinChannel(channel);
	}
}