package kovu.teamstats;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import kovu.ralex.teamstats.api.TeamStatsAPI;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;

public class mod_TeamInfo extends BaseMod {

    public boolean mcisloaded = false;
    public boolean minusActivated;
    
    public String rejectRequest;

    public int i = 0;

    public GuiScreen guiscreen;
    
    public Kovu kovu;
    
    public Minecraft mc = ModLoader.getMinecraftInstance();
    
    public EntityClientPlayerMP player;
    
    private static mod_TeamInfo instance;
    
    public SkinHandler sk;
    
    public static GuiTeamInfoIngame ingame = new GuiTeamInfoIngame();
    private static final Logger logger = Logger.getLogger(mod_TeamInfo.class.getName());


    public String getVersion() {
        return "For MC version 1.5.2";
    }

    public mod_TeamInfo() throws IllegalAccessException {
		sk = new SkinHandler();

        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalAccessException("Attemped to recreate instance for TeamStats");
        }
        ModLoader.registerKey(instance, new KeyBinding("TStats", Keyboard.KEY_0), false);
        ModLoader.registerKey(instance, new KeyBinding("Change Location", Keyboard.KEY_EQUALS), false);
        ModLoader.setInGameHook(instance, true, true);
        sk.makeSkinDir();
        rejectRequest = "NOTACCEPTED";
    }

    @Override
    public void keyboardEvent(KeyBinding keybinding) {

        if(keybinding.keyCode == Keyboard.KEY_0)
        {
        	if(Kovu.isInGUI)
        	{
        		ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiTeamInfo(guiscreen));
        		sk.downloadSkin("Charsmud");
        		sk.downloadSkin("Rainfur");
        	}
        }
        if(keybinding.keyCode == Keyboard.KEY_EQUALS)
        {
        	if(Kovu.isInGUI)
        	{
        		mc.thePlayer.addChatMessage("TeamStats configuration mode enabled");
        		ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, ingame = new GuiTeamInfoIngame());
        	}
        }
    }

    public void load() {
        try {
            TeamStatsAPI.setupApi(mc.session.username, mc.session.sessionId);
        } catch (UnknownHostException ex) {
            logger.log(Level.SEVERE, "An error has occured", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "An error has occured", ex);
        }
    }

    public void sendStats() {
        HashMap<String, Object> stats = new HashMap<String, Object>();
        stats.put("POSX", kovu.mc.thePlayer.posX);
        stats.put("POSY", kovu.mc.thePlayer.posY);
        stats.put("POSZ", kovu.mc.thePlayer.posZ);
        stats.put("HP", kovu.mc.thePlayer.getHealth());
        stats.put("FD", kovu.mc.thePlayer.getFoodStats().getFoodLevel());
        stats.put("AR", kovu.mc.thePlayer.getTotalArmorValue());
        stats.put("PS", kovu.mc.thePlayer.isPotionActive(Potion.poison));
        stats.put("FR", kovu.mc.thePlayer.isPotionActive(Potion.fireResistance));
        stats.put("WK", kovu.mc.thePlayer.isPotionActive(Potion.weakness));
        stats.put("SW", kovu.mc.thePlayer.isPotionActive(Potion.moveSpeed));
        stats.put("SL", kovu.mc.thePlayer.isPotionActive(Potion.moveSlowdown));
        stats.put("RG", kovu.mc.thePlayer.isPotionActive(Potion.regeneration));
        stats.put("HD", kovu.mc.thePlayer.isPotionActive(Potion.hunger));
        stats.put("OF", kovu.mc.thePlayer.isBurning());

        try {
            TeamStatsAPI.updateStats(stats);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error has occured", e);
        }
    }

    @Override
    public boolean onTickInGame(float tick, Minecraft mc) {

        if (kovu == null) {
            kovu = new Kovu(mc);
        }

        if (mcisloaded == true) {
            //String[] s = FriendModApi.getFriendRequests();
            String[] s = new String[]{
                "Rainfur",
                "Lord_Ralex",
                "charsmud",
                "Chicken_nuggster",
                "JurassicBerry"
            };

            if (i == 20) {
                sendStats();
                i = 0;
                System.out.print("Names in list: ");
                for (String string : s) {
                    System.out.print(string + " ");
                }
            }
            i++;
        }
        return true;
    }

    public static mod_TeamInfo getInstance() {
        return instance;
    }
}
