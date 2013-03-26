
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

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.FMLLog;


public class SkinHandler {

	File skinFolder = new File(ModLoader.getMinecraftInstance().getMinecraftDir() + "\\dlskins");
	//Know the params?
    private BufferedImage missingTextureImage = new BufferedImage(64, 64, 2);

    public static Logger log = FMLLog.getLogger();

    private HashMap textureContentsMap = new HashMap();
    
	private BufferedImage img;
	private int imgID = 1000;
	
	public void makeSkinDir()
	{
		skinFolder.mkdirs();
	}
	public void downloadSkin(String playerName)
	{
		String username = "http://skins.minecraft.net/MinecraftSkins/" + playerName + ".png";
		try
		{
			URL skin = new URL(username);
		    ReadableByteChannel rbc = Channels.newChannel(skin.openStream());
		    FileOutputStream fos = new FileOutputStream(skinFolder + "\\" + playerName + ".png");
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
    public int[] getTextureContents(String texture)
    {
            try
            {
                InputStream inputstream = new FileInputStream(texture);
                int[] aint1;

                if (inputstream == null)
                {
                    aint1 = this.getImageContentsAndAllocate(this.missingTextureImage);
                }
                else
                {
                    BufferedImage bufferedimage = this.readTextureImage(inputstream);
                    TextureFXManager.instance().fixTransparency(bufferedimage, texture);
                    aint1 = this.getImageContentsAndAllocate(bufferedimage);
                }

                this.textureContentsMap.put(texture, aint1);
                return aint1;
            }
            catch (Exception ioexception)
            {
                log.log(Level.INFO, String.format("An error occured reading texture file %s (getTexture)", texture), ioexception);
                ioexception.printStackTrace();
                int[] aint2 = this.getImageContentsAndAllocate(this.missingTextureImage);
                this.textureContentsMap.put(texture, aint2);
                return aint2;
            }
        }

    private int[] getImageContentsAndAllocate(BufferedImage par1BufferedImage)
    {
    return this.getImageContents(par1BufferedImage, new int[par1BufferedImage.getWidth() * par1BufferedImage.getHeight()]);
    }
    private int[] getImageContents(BufferedImage par1BufferedImage, int[] par2ArrayOfInteger)
    {
        int i = par1BufferedImage.getWidth();
        int j = par1BufferedImage.getHeight();
        par1BufferedImage.getRGB(0, 0, i, j, par2ArrayOfInteger, 0, i);
        return par2ArrayOfInteger;
    }

  
    private BufferedImage readTextureImage(InputStream par1InputStream) throws IOException
    {
        BufferedImage bufferedimage = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return bufferedimage;
    }

	
	public void cropHeadAndDisplay(Minecraft minecraft, String playerName)
	{
		File playerSkin = new File(skinFolder + "\\" + playerName + ".png");
	
		File[] skins = skinFolder.listFiles();
		for(int i = 0; i < skins.length; i++)
		{
			System.out.println(skins[i]);
		}
		
		try
		{
			img = ModLoader.loadImage(minecraft.renderEngine, playerSkin.toString());
			minecraft.renderEngine.setupTexture(img, imgID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			int tempVar = minecraft.renderEngine.getTexture(playerSkin.toString());
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//	minecraft.renderEngine.(tempVar);
			GuiIngame gig = new GuiIngame(minecraft);
			gig.drawTexturedModalRect(5, 5, 8, 8, 8, 8);
			
		}
		finally
		{
			
		}
		//GuiIngame gig = new GuiIngame(minecraft);
		    
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureContentsMap.hashCode());
		//gig.drawTexturedModalRect(5, 5, 8, 8, 8, 8);

		
	}
}
