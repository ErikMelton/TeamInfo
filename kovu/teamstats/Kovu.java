package kovu.teamstats;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class Kovu {

    public static Minecraft mc;
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
    public static boolean clickPlayer = false;
    public static boolean clickWorld = false;
    public static boolean clickMenu = false;
    public static World world;

    public Kovu(Minecraft theMinecraft) {
        mc = theMinecraft;
    }
}
