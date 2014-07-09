package kovu.teaminfo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.lwjgl.input.Keyboard;

import kovu.teaminfo.bot.Bot;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "teaminfo", version = "1.0")
public class TeamInfo extends Thread 
{	
	@SidedProxy(clientSide = "kovu.teaminfo.ClientProxy", serverSide = "kovu.teaminfo.CommonProxy")
	public static CommonProxy proxy;
	
	public static TeamInfo instance;
	
	private static Bot bot;
	
	private static String channel = "#teaminfomod";
	private static String nick;
	
	private static String host = "irc.esper.net";
	private static int port = 6667;
		
	private boolean isRunning;
	public static Map online = new HashMap();
	public static Map<String, Object> players = new HashMap();

	public static Map showgui = new HashMap();

	public static Map playershealth = new HashMap();
	public static Map playersprevhealth = new HashMap();
	public static Map playershunger = new HashMap();
	public static Map playersarmour = new HashMap();

	public static Map playerpoison = new HashMap();
	public static Map playerfireresist = new HashMap();
	public static Map playerweakness = new HashMap();
	public static Map playerswift = new HashMap();
	public static Map playerslow = new HashMap();
	public static Map playerregen = new HashMap();
	public static Map playerhungerdrain = new HashMap();
	public static Map playeronfire = new HashMap();

	public static Map playerposX = new HashMap();
	public static Map playerposY = new HashMap();
	public static Map playerposZ = new HashMap();

	public static Map playerskin = new HashMap();

	public static Map toplefti = new HashMap();
	public static Map topleftj = new HashMap();
	public static Map dragable = new HashMap();
		
	@EventHandler
	public void init(FMLInitializationEvent event) 
	{
		this.isRunning = true;
		
		KeyBindings keys = new KeyBindings();
		keys.init();
				
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		FMLCommonHandler.instance().bus().register(new TickHandler());
		proxy.setupRemoteTexture();
		
		instance = this;

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		nick = Minecraft.getMinecraft().getSession().getUsername();
		this.start();
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

		URL skinUrl = null;
		try 
		{
			skinUrl = new URL("http://s3.amazonaws.com/MinecraftSkins/" + addedPerson + ".png");
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		BufferedImage bi = null;
		try 
		{
			System.out.println("The skin URL is:" + skinUrl);
			bi = ImageIO.read(skinUrl);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		playerskin.put(addedPerson, bi);
	}
	
	public static void remove(String target) 
	{
		players.remove(target);
		Util.mc.thePlayer.sendChatMessage(target + " was removed from your team info.");
		
		bot.sendMessage(target, "REMOVE");
	}
	
	public static void forceremove(String s)
	{
		players.remove(s);
		Util.mc.thePlayer.sendChatMessage(s + " requested that you stop sharing info.");
	}

	public void run()
	{	
		bot = new Bot(nick);
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