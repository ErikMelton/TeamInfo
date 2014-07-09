package kovu.teaminfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy
{
	public static ResourceLocation kovuHead;
	
	@Override
	public void setupRemoteTexture() 
	{
		ITextureObject textureSkinTwo = new ThreadDownloadImageData(null, "http://s3.amazonaws.com/MinecraftSkins/Rainfur.png", null, new ImageBufferDownload());
		kovuHead = new ResourceLocation("teaminfo/skins");
		Minecraft.getMinecraft().renderEngine.loadTexture(kovuHead, textureSkinTwo);
		Minecraft.getMinecraft().renderEngine.bindTexture(kovuHead);
	}
}
