package kovu.teamstats;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;

import net.ae97.teamstats.api.TeamstatsAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import kovu.teamstats.gui.GuiHandler;
import kovu.teamstats.gui.GuiTeamInfoIngame;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "TeamInfo", name = "TeamInfo", version = "1.0.0")
public class TeamInfoMod {

    @SidedProxy(clientSide = "kovu.teamstats.ClientProxy", serverSide = "kovu.teamstats.CommonProxy")
    public static CommonProxy proxy;
    public static TeamInfoMod instance;

    public boolean mcisloaded = false;
    public boolean minusActivated;
    public String rejectRequest;
    public int i = 0;
    public Kovu kovu;
    public Minecraft mc = ModLoader.getMinecraftInstance();
    public EntityClientPlayerMP player;
    private TeamstatsAPI api;

    @EventHandler
    public void load(FMLInitializationEvent evt) {
        proxy.registerRenderInformation();
        MinecraftForge.EVENT_BUS.register(new TeamStatsHookContainer());
        
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
        SkinHandler.makeSkinDir();
        rejectRequest = "NOTACCEPTED";
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
    }
    
    //Was called every tick
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

        try
        {
        	api.updateStats(stats);
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
    }
}