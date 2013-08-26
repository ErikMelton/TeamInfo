package kovu.teamstats;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import kovu.teamstats.gui.GuiTeamInfo;
import kovu.teamstats.gui.GuiTeamInfoIngame;
import net.ae97.teamstats.api.TeamstatsAPI;

public class mod_TeamInfo extends BaseMod {

    public boolean mcisloaded = false;
    public boolean minusActivated;
    public String rejectRequest;
    public int i = 0;
    public GuiScreen guiscreen;
    public Kovu kovu;
    public Minecraft mc = ModLoader.getMinecraftInstance();
    public EntityClientPlayerMP player;
    public SkinHandler sk;
    public static GuiTeamInfoIngame ingame = new GuiTeamInfoIngame();
    private static final Logger logger = Logger.getLogger(mod_TeamInfo.class.getName());
    private final TeamstatsAPI api;

    @Override
    public String getVersion() {
        return "For MC version 1.6.1";
    }

    public mod_TeamInfo() throws IllegalAccessException {
        sk = new SkinHandler();

        TeamstatsAPI temp;
        try {
            temp = TeamstatsAPI.getAPI();
        } catch (Exception ex) {
            temp = null;
            System.out.println("We had an error on the API");
            System.out.println(ex.getMessage());
            StackTraceElement[] el = ex.getStackTrace();
            for (StackTraceElement e : el) {
                System.out.println(e.toString());
            }
        }
        api = temp;
        ModLoader.registerKey(this, new KeyBinding("TStats", Keyboard.KEY_0), false);
        ModLoader.registerKey(this, new KeyBinding("Change Location", Keyboard.KEY_EQUALS), false);
        ModLoader.setInGameHook(this, true, true);
        sk.makeSkinDir();
        rejectRequest = "NOTACCEPTED";
    }

    @Override
    public void keyboardEvent(KeyBinding keybinding) {

        if (keybinding.keyCode == Keyboard.KEY_0) {
            if (Kovu.isInGUI) {
                ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiTeamInfo(guiscreen));
                sk.downloadSkin("Charsmud");
                sk.downloadSkin("Rainfur");
            }
        }
        if (keybinding.keyCode == Keyboard.KEY_EQUALS) {
            if (Kovu.isInGUI) {
                mc.thePlayer.addChatMessage("TeamStats configuration mode enabled");
                ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, ingame = new GuiTeamInfoIngame());
            }
        }
    }

    @Override
    public void load() {
    }

    public void sendStats() {
        HashMap<String, Object> stats = new HashMap<String, Object>();
        stats.put("POSX", kovu.mc.thePlayer.posX);
        stats.put("POSY", kovu.mc.thePlayer.posY);
        stats.put("POSZ", kovu.mc.thePlayer.posZ);
        stats.put("HP", kovu.mc.thePlayer.func_110143_aJ());
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

        api.updateStats(stats);
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
}
