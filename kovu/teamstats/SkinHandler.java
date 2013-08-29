package kovu.teamstats;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.src.ModLoader;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.FMLLog;

public class SkinHandler {

    File skinFolder = new File("." + File.separator + "dlskins");
    //Know the params?
    private FileOutputStream fos;

    public void makeSkinDir() {
        skinFolder.mkdirs();
    }

    public void downloadSkin(String playerName) {
        String username = "http://skins.minecraft.net/MinecraftSkins/" + playerName + ".png";
        try {
            URL skin = new URL(username);
            ReadableByteChannel rbc = Channels.newChannel(skin.openStream());
            fos = new FileOutputStream(skinFolder + "\\" + playerName + ".png");
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void cropHeadAndDisplay(Minecraft minecraft, String playerName) {
        File playerSkin = new File(skinFolder + "\\" + playerName + ".png");

        File[] skins = skinFolder.listFiles();
        for (int i = 0; i < skins.length; i++) {
            System.out.println(skins[i]);
        }

        try {
        //    img = ModLoader.loadImage(minecraft.renderEngine, playerSkin.toString());
        //    minecraft.renderEngine.setupTexture(img, imgID);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

        try {
        //    int tempVar = minecraft.renderEngine.getTexture(playerSkin.toString());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //	minecraft.renderEngine.(tempVar);
            GuiIngame gig = new GuiIngame(minecraft);
            gig.drawTexturedModalRect(5, 5, 8, 8, 8, 8);

        } finally {
        }
        //GuiIngame gig = new GuiIngame(minecraft);

        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureContentsMap.hashCode());
        //gig.drawTexturedModalRect(5, 5, 8, 8, 8, 8);
    }
}
