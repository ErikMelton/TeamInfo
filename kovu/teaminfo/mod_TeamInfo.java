package kovu.teaminfo;

import java.io.IOException;


import org.lwjgl.input.Keyboard;

import friendmodapi.FriendModApi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.util.FoodStats;

public class mod_TeamInfo extends BaseMod {
	
	Boolean mcisloaded = false;
	Boolean oneKovu = false;
	
	GuiScreen guiscreen;
	Kovu kovu;
	
	Minecraft mc = ModLoader.getMinecraftInstance();
	
	public String coordstats;
	public String health;
	public String food;
	public String armour;
	public String fireresist;
	public String poison;
	public String weakness;
	public String swiftness;
	public String slowness;
	public String regen;
	public String hungerdrain;
	public String onfire;
	public String stats;
	public String rejectRequest;
	
	EntityClientPlayerMP player;
		
	int i = 0;
	
	public static boolean minusActivated;
	
	public String getVersion() {
		return "For MC version 1.4.4";
	}

	public mod_TeamInfo() {
		
		ModLoader.registerKey(this, new KeyBinding("Team Info", Keyboard.KEY_EQUALS),  false);
		ModLoader.registerKey(this, new KeyBinding("Team Info", Keyboard.KEY_MINUS),  false);
		ModLoader.setInGameHook(this, true, true);
		rejectRequest = "NOTACCEPTED";
	}
	
	public void keyboardEvent(KeyBinding keybinding) {
		if(keybinding.keyCode == Keyboard.KEY_EQUALS) {
			System.out.println("Pressed");
			mcisloaded = true;
			//ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiTeamInfo(guiscreen));
		}
		if(keybinding.keyCode == Keyboard.KEY_MINUS) {
		GuiDraggableElement e = new GuiDraggableElement(1, 1, 100, 30);
			if(minusActivated == false)
			{
				minusActivated = true;
			}
			else if(minusActivated == true)
			{
			minusActivated = false;
			}
		}
	}
	public void load() {
		
		try {
		FriendModApi.setupApi(mc.session.username, mc.session.sessionId);
		
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void sendStats() {
		int var16;
		coordstats = "POSX= " + kovu.mc.thePlayer.posX + " POSY= " + kovu.mc.thePlayer.posY + " POSZ= " + kovu.mc.thePlayer.posZ;
		health = "HP= " + kovu.mc.thePlayer.getHealth();
        FoodStats var15 = kovu.mc.thePlayer.getFoodStats();
        var16 = var15.getFoodLevel();
		food = "FD= " + var16;
		armour = "AR= " + kovu.mc.thePlayer.getTotalArmorValue();
		poison = "PS= " + kovu.mc.thePlayer.isPotionActive(Potion.poison);
		fireresist = "FR= " + kovu.mc.thePlayer.isPotionActive(Potion.fireResistance);
		weakness = "WK= " + kovu.mc.thePlayer.isPotionActive(Potion.weakness);
		swiftness = "SW= " + kovu.mc.thePlayer.isPotionActive(Potion.moveSpeed);
		slowness = "SL= " + kovu.mc.thePlayer.isPotionActive(Potion.moveSlowdown);
		regen = "RG= " + kovu.mc.thePlayer.isPotionActive(Potion.regeneration);
		hungerdrain = "HD= " + kovu.mc.thePlayer.isPotionActive(Potion.hunger);
		onfire = "OF= " + kovu.mc.thePlayer.isBurning();
		stats = coordstats + " " + health + " " + food + " " + armour + " " + poison + " " + fireresist + " " + weakness + " " + swiftness + " " + slowness + " " + regen + " " + hungerdrain + " " + onfire;
		try {
			FriendModApi.updateStats(stats);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}

	}
	
	public boolean onTickInGame(float tick,Minecraft mc){

		if(oneKovu == false)
		{
			Kovu kovu = new Kovu(mc);
			oneKovu = true;
		}
		
		if(mcisloaded == true)
		{
			//String[] s = FriendModApi.getFriendRequests();
			String[] s = new String[5];
			
			if(i == 20) {
				sendStats();
				System.out.println(stats);
				i = 0;
				
				if(s != null)
				{
					//EXPL: These are there for testing purposes
					s[0] = "Rainfur";
					s[1] = "Lord_Ralex";
					s[2] = "charsmud";
					s[3] = "Chicken_nuggster";
					s[4] = "JurassicBerry";
				
				//	System.out.println(s.length);

					for(int i = 0; i < s.length; i++)
					{
						if(s[i] == null)
						{
							break;
						}
						System.out.println(s[i]);
						s[i] = null;
					}
				}
			}
			i++;
		}
		return true;
	}

}
