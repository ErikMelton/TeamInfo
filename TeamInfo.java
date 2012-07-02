package net.minecraft.src;

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
 import java.util.HashMap;
 import java.util.Map;
 import javax.imageio.ImageIO;
 import net.minecraft.client.Minecraft;
 import org.jibble.pircbot.*;

public class TeamInfo extends PircBot implements Runnable
 {
	
   static String channel;
   public String username;
   public String nick;
   
   private boolean isRunning;
   boolean recievingnames;
   public static Map online = new HashMap();
   public static Map players = new HashMap();
 
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
   public static BufferedImage spowney;
   public static BufferedImage jay92;
   
   public static String[] usernames;
   public String[] junkyarray;
   
   public GuiScreen hello;
   
   public GuiAddtoTeam add = new GuiAddtoTeam(hello);
   
   public EntityPlayerSP tp;
   
   public powney p = new powney(ModLoader.getMinecraftInstance());
   
   public TeamInfo(String s)
   {
     this.nick = s;
     this.isRunning = true;
     this.recievingnames = false;
     this.setName(nick);
   }

   public void onMessage(String channel, String sender, String login, String hostname, String message) {
	   
	   if(message.equalsIgnoreCase("PING")) {
		   
		   sendMessage(channel, "PONG");
	       sendRawLine("PRIVMSG " + "Player21" + " :" + "ADDME " + "HP" + powney.mc.thePlayer.health + "HUNGER" + powney.mc.thePlayer.foodStats.getFoodLevel() + "ARMOUR" + powney.mc.thePlayer.getTotalArmorValue() + "POISON" + powney.mc.thePlayer.isPotionActive(Potion.poison) + "FIRERESIST" + powney.mc.thePlayer.isPotionActive(Potion.fireResistance) + "WEAKNESS" + powney.mc.thePlayer.isPotionActive(Potion.weakness) + "SWIFT" + powney.mc.thePlayer.isPotionActive(Potion.digSpeed) + "SLOW" + powney.mc.thePlayer.isPotionActive(Potion.moveSlowdown) + "REGEN" + powney.mc.thePlayer.isPotionActive(Potion.regeneration) + "HUNGERDRAIN" + powney.mc.thePlayer.isPotionActive(Potion.hunger) + "ONFIRE" + powney.mc.thePlayer.isBurning() + "POSX" + (int)powney.mc.thePlayer.posX + "POSY" + (int)powney.mc.thePlayer.posY + "POSZ" + (int)powney.mc.thePlayer.posZ + "\n");

		  // sendRawLine("PRIVMSG "+ "Player21" + " :" + "ADDME " + "HP" + powney.mc.thePlayer.health + "HUNGER" + powney.mc.thePlayer.foodStats.getFoodLevel() + "ARMOUR" + powney.mc.thePlayer.getTotalArmorValue() + "POISON" + powney.mc.thePlayer.isPotionActive(Potion.poison) + "FIRERESIST" + powney.mc.thePlayer.isPotionActive(Potion.fireResistance) + "WEAKNESS" + powney.mc.thePlayer.isPotionActive(Potion.weakness) + "SWIFT" + powney.mc.thePlayer.isPotionActive(Potion.digSpeed) + "SLOW" + powney.mc.thePlayer.isPotionActive(Potion.moveSlowdown) + "REGEN" + powney.mc.thePlayer.isPotionActive(Potion.regeneration) + "HUNGERDRAIN" + powney.mc.thePlayer.isPotionActive(Potion.hunger) + "ONFIRE" + powney.mc.thePlayer.isBurning() + "POSX" + (int)powney.mc.thePlayer.posX + "POSY" + (int)powney.mc.thePlayer.posY + "POSZ" + (int)powney.mc.thePlayer.posZ + "\n");
	   }
	   if(message.contains(("ADDME")) && (message.contains(nick))) {
		   
		   sendRawLine("PRIVMSG "+ add.getServerTextField() + " :" + "ADDME " + "HP" + powney.mc.thePlayer.health + "HUNGER" + powney.mc.thePlayer.foodStats.getFoodLevel() + "ARMOUR" + powney.mc.thePlayer.getTotalArmorValue() + "POISON" + powney.mc.thePlayer.isPotionActive(Potion.poison) + "FIRERESIST" + powney.mc.thePlayer.isPotionActive(Potion.fireResistance) + "WEAKNESS" + powney.mc.thePlayer.isPotionActive(Potion.weakness) + "SWIFT" + powney.mc.thePlayer.isPotionActive(Potion.digSpeed) + "SLOW" + powney.mc.thePlayer.isPotionActive(Potion.moveSlowdown) + "REGEN" + powney.mc.thePlayer.isPotionActive(Potion.regeneration) + "HUNGERDRAIN" + powney.mc.thePlayer.isPotionActive(Potion.hunger) + "ONFIRE" + powney.mc.thePlayer.isBurning() + "POSX" + (int)powney.mc.thePlayer.posX + "POSY" + (int)powney.mc.thePlayer.posY + "POSZ" + (int)powney.mc.thePlayer.posZ + "\n");
	   }
	   if ((message.contains("REMOVE")) && (message.contains(nick)))
       {
         String s4 = message.substring(1, message.indexOf("!"));
         
         forceremove(s4);
         
       }if ((message.contains("REJECT")) && (message.contains(nick)))
       {
         String s4 = message.substring(1, message.indexOf("!"));
         powney.mc.thePlayer.addChatMessage("Your request was rejected by §c" + s4 + "§f");
         
       }if ((message.contains(" PRIVMSG ")) && (message.contains(nick)) && (message.contains("UPDATE")))
       {
         String s3 = message.substring(message.indexOf(":", message.indexOf("PRIVMSG")) + 1);
         String s4 = message.substring(1, message.indexOf("!"));

         interpretUpdate(message);

       }if ((message.contains(" PRIVMSG ")) && (message.contains(nick)) && (message.contains("ADDED")))
       {
         String s3 = message.substring(message.indexOf(":", message.indexOf("PRIVMSG")) + 1);
         String s4 = message.substring(1, message.indexOf("!"));
         powney.mc.thePlayer.addChatMessage("Your request was accepted by §c" + s4 + "§f");
         add(s4);
         interpretUpdate(message);

       }if ((message.contains(" MODE ")) && (message.contains(nick)))
       {
         
           sendMessage(channel, "JOIN " + channel + "\n");
            
       }
      if ((message.contains(" PART ")) || (message.contains(" QUIT ")))
       {
         String s4 = message.substring(1, message.indexOf("!"));

         online.remove(s4);
         players.remove(s4);

        if (players.containsKey(s4))
         {
           players.remove(s4);
         }
       }
       if ((message.contains(" JOIN ")) && (!message.contains(nick)))
       {
         String s4 = message.substring(1, message.indexOf("!"));

         online.put(s4, Boolean.valueOf(false));
       }
    	   
       String s4 = message.substring(message.indexOf(" :"));
       String[] names = s4.split(" ");
       for (int i = 0; i < names.length; i++)
       {
         if (names[i].startsWith("@"))
         {
           names[i] = names[i].substring(names[i].indexOf("@") + 1);
         }
         online.put(names[i], Boolean.valueOf(false));
       }
   }
   
   public void print(String s, String to)
   {
     try
     {
    	 sendRawLine("PRIVMSG " + to + " :" + s + "\n");
     }
     
     catch (Exception exception)
     {
    	 
     }
   }
 
   public void requestadd(String to) {
     try {
  	   System.out.println("Test1");

       sendRawLine("PRIVMSG " + to + " :" + "ADDME " + "HP" + powney.mc.thePlayer.health + "HUNGER" + powney.mc.thePlayer.foodStats.getFoodLevel() + "ARMOUR" + powney.mc.thePlayer.getTotalArmorValue() + "POISON" + powney.mc.thePlayer.isPotionActive(Potion.poison) + "FIRERESIST" + powney.mc.thePlayer.isPotionActive(Potion.fireResistance) + "WEAKNESS" + powney.mc.thePlayer.isPotionActive(Potion.weakness) + "SWIFT" + powney.mc.thePlayer.isPotionActive(Potion.digSpeed) + "SLOW" + powney.mc.thePlayer.isPotionActive(Potion.moveSlowdown) + "REGEN" + powney.mc.thePlayer.isPotionActive(Potion.regeneration) + "HUNGERDRAIN" + powney.mc.thePlayer.isPotionActive(Potion.hunger) + "ONFIRE" + powney.mc.thePlayer.isBurning() + "POSX" + (int)powney.mc.thePlayer.posX + "POSY" + (int)powney.mc.thePlayer.posY + "POSZ" + (int)powney.mc.thePlayer.posZ + "\n");
      }
     catch (Exception exception)
     {
     }
   }
 
   public void acceptadd(String to) {
	   
	   sendRawLine("PRIVMSG " + to + " :" + " ADDME " + "HP" + powney.mc.thePlayer.health + "HUNGER" + powney.mc.thePlayer.foodStats.getFoodLevel() + "ARMOUR" + powney.mc.thePlayer.getTotalArmorValue() + "POISON" + powney.mc.thePlayer.isPotionActive(Potion.poison) + "FIRERESIST" + powney.mc.thePlayer.isPotionActive(Potion.fireResistance) + "WEAKNESS" + powney.mc.thePlayer.isPotionActive(Potion.weakness) + "SWIFT" + powney.mc.thePlayer.isPotionActive(Potion.digSpeed) + "SLOW" + powney.mc.thePlayer.isPotionActive(Potion.moveSlowdown) + "REGEN" + powney.mc.thePlayer.isPotionActive(Potion.regeneration) + "HUNGERDRAIN" + powney.mc.thePlayer.isPotionActive(Potion.hunger) + "ONFIRE" + powney.mc.thePlayer.isBurning() + "POSX" + (int)powney.mc.thePlayer.posX + "POSY" + (int)powney.mc.thePlayer.posY + "POSZ" + (int)powney.mc.thePlayer.posZ + "\n");
   }
 
   public void rejectadd(String to)
   {
     try
     {
    	 sendRawLine("PRIVMSG " + to + " :" + " REJECT" + "\n");
     }
     catch (Exception exception)
     {
     }
   }
 
   public void sendUpdate(String to) {
     try {
    	 sendRawLine("PRIVMSG " + to + " :" + " ADDME " + "HP" + powney.mc.thePlayer.health + "HUNGER" + powney.mc.thePlayer.foodStats.getFoodLevel() + "ARMOUR" + powney.mc.thePlayer.getTotalArmorValue() + "POISON" + powney.mc.thePlayer.isPotionActive(Potion.poison) + "FIRERESIST" + powney.mc.thePlayer.isPotionActive(Potion.fireResistance) + "WEAKNESS" + powney.mc.thePlayer.isPotionActive(Potion.weakness) + "SWIFT" + powney.mc.thePlayer.isPotionActive(Potion.digSpeed) + "SLOW" + powney.mc.thePlayer.isPotionActive(Potion.moveSlowdown) + "REGEN" + powney.mc.thePlayer.isPotionActive(Potion.regeneration) + "HUNGERDRAIN" + powney.mc.thePlayer.isPotionActive(Potion.hunger) + "ONFIRE" + powney.mc.thePlayer.isBurning() + "POSX" + (int)powney.mc.thePlayer.posX + "POSY" + (int)powney.mc.thePlayer.posY + "POSZ" + (int)powney.mc.thePlayer.posZ + "\n");
      }
     catch (Exception exception) {
    }
   }
 
   public static void interpretUpdate(String update) {
     String name = update.substring(1, update.indexOf("!"));
 
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
 
     playershealth.put(name, Integer.valueOf(Integer.parseInt(health)));
     playershunger.put(name, Integer.valueOf(Integer.parseInt(hunger)));
     playersarmour.put(name, Integer.valueOf(Integer.parseInt(armour)));
 
     playerpoison.put(name, Boolean.valueOf(Boolean.parseBoolean(poison)));
     playerfireresist.put(name, Boolean.valueOf(Boolean.parseBoolean(fireresist)));
     playerweakness.put(name, Boolean.valueOf(Boolean.parseBoolean(weakness)));
     playerswift.put(name, Boolean.valueOf(Boolean.parseBoolean(swift)));
     playerslow.put(name, Boolean.valueOf(Boolean.parseBoolean(slow)));
     playerregen.put(name, Boolean.valueOf(Boolean.parseBoolean(regen)));
     playerhungerdrain.put(name, Boolean.valueOf(Boolean.parseBoolean(hungerdrain)));
     playeronfire.put(name, Boolean.valueOf(Boolean.parseBoolean(onfire)));
     playerposX.put(name, Integer.valueOf(Integer.parseInt(posX)));
     playerposY.put(name, Integer.valueOf(Integer.parseInt(posY)));
     playerposZ.put(name, Integer.valueOf(Integer.parseInt(posZ)));
   }
 
   public static void add(String s)
   {
     players.put(s, Boolean.valueOf(true));
     showgui.put(s, Boolean.valueOf(true));
 
     ScaledResolution scaledresolution = new ScaledResolution(powney.mc.gameSettings, powney.mc.displayWidth, powney.mc.displayHeight);
 
     int k = scaledresolution.getScaledWidth();
 
     toplefti.put(s, Integer.valueOf(k - 110));
     topleftj.put(s, Integer.valueOf(players.size() * 35));
 
     URL skinUrl = null;
     try {
       skinUrl = new URL("http://s3.amazonaws.com/MinecraftSkins/" + s + ".png");
     }
     catch (MalformedURLException e) {
       e.printStackTrace();
     }
     BufferedImage bi = null;
     try {
       bi = ImageIO.read(skinUrl);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     playerskin.put(s, bi);
   }
 
   public void remove(String s)
   {
     players.remove(s);
     powney.mc.thePlayer.addChatMessage("§c" + s + "§f was removed from your team info.");
     try
     {
       sendMessage(channel, "PRIVMSG " + s + " :" + " REMOVE " + "\n");
     }
     catch (Exception exception) {
     }
   }
 
     public void forceremove(String s) {
     players.remove(s);
     powney.mc.thePlayer.addChatMessage("§c" + s + "§f requested that you stop sharing info.");
   }
 
   public void run()
   {
     URL skinUrl = null;
     try {
       skinUrl = new URL("http://s3.amazonaws.com/MinecraftSkins/" + "spowney" + ".png");
     }
     catch (MalformedURLException e) {
       e.printStackTrace();
     }
     try {
      spowney = ImageIO.read(skinUrl);
    }
     catch (IOException e) {
       e.printStackTrace();
     }
     try {
       skinUrl = new URL("http://s3.amazonaws.com/MinecraftSkins/" + "Jay_92" + ".png");
     }
     catch (MalformedURLException e) {
       e.printStackTrace();
     }
     try {
       jay92 = ImageIO.read(skinUrl);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
}