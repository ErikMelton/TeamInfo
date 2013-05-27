package kovu.teamstats;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class Kovu {

    public static Minecraft mc;
    public static float scale = 1.0F;
    public static boolean showlist = false;
    public static boolean hunger = true;
    public static boolean armour = true;
    public static boolean noarmour = true;
    public static boolean coords = false;
    public static boolean isInGUI = false;
    public static boolean isVersion = true;
    
    public static World world;

    public static ArrayList<String> friends = new ArrayList<String>();
    
    public Kovu(Minecraft theMinecraft) {
        mc = theMinecraft;
    }
}
