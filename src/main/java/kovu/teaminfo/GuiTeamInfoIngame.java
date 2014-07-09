package kovu.teaminfo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;

public class GuiTeamInfoIngame extends GuiIngameForge 
{
	int health;
	int hunger;
	int armour;
	int prevhealth;
	int prevhunger;
	int prevarmour;
	boolean prevpoison;
	boolean prevhungerdrain;
	boolean prevfireresist;
	boolean prevweakness;
	boolean prevregen;
	boolean prevswift;
	boolean prevslow;
	boolean poison = false;
	boolean hungerdrain = false;
	boolean fireresist = false;
	boolean weakness = false;
	boolean regen = false;
	boolean swift = false;
	boolean slow = false;
	int posX;
	int posY;
	int posZ;
	int prevposX = 0;
	int prevposY = 65;
	int prevposZ = 0;
	int notequal;
	
	public static Map<String, Boolean> playerInfo = new HashMap();
	ResourceLocation location;
	
	public GuiTeamInfoIngame(Minecraft mc) 
	{
		super(mc);
	}

	@Override
	public void renderGameOverlay(float partialTicks, boolean hasScreen, int mouseX, int mouseY)
	{
		super.renderGameOverlay(partialTicks, hasScreen, mouseX, mouseY);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(2896);
		GL11.glEnable(3008);

		prevhealth = health;
		prevhunger = hunger;
		prevarmour = armour;
		prevpoison = poison;
		prevhungerdrain = hungerdrain;
		prevfireresist = fireresist;
		prevweakness = weakness;
		prevregen = regen;
		prevswift = swift;
		prevslow = slow;

		health = (int) mc.thePlayer.getHealth();
		hunger = mc.thePlayer.getFoodStats().getFoodLevel();
		armour = mc.thePlayer.getTotalArmorValue();
		poison = mc.thePlayer.isPotionActive(Potion.poison);
		hungerdrain = mc.thePlayer.isPotionActive(Potion.hunger);
		fireresist = mc.thePlayer.isPotionActive(Potion.fireResistance);
		weakness = mc.thePlayer.isPotionActive(Potion.weakness);
		regen = mc.thePlayer.isPotionActive(Potion.regeneration);
		swift = mc.thePlayer.isPotionActive(Potion.moveSpeed);
		slow = mc.thePlayer.isPotionActive(Potion.moveSlowdown);
		posX = (int) mc.thePlayer.posX;
		posY = (int) mc.thePlayer.posY;
		posZ = (int) mc.thePlayer.posZ;

		if ((this.health != this.prevhealth)
				|| (this.hunger != this.prevhunger)
				|| (this.armour != this.prevarmour)
				|| (this.prevpoison != this.poison)
				|| (this.prevhungerdrain != this.hungerdrain)
				|| (this.prevfireresist != this.fireresist)
				|| (this.prevweakness != this.weakness)
				|| (this.prevregen != this.regen)
				|| (this.prevswift != this.swift)
				|| (this.prevslow != this.slow)
				|| (Math.abs(this.posX - this.prevposX) > 10)
				|| (Math.abs(this.posZ - this.prevposZ) > 10)) 
		{
			this.prevposX = this.posX;
			this.prevposY = this.posY;
			this.prevposZ = this.posZ;

			for (String player : TeamInfo.players.keySet()) 
			{
				TeamInfo.sendUpdate(player);
			}
		}

		for (String player : TeamInfo.players.keySet()) 
		{
			if (((Boolean) TeamInfo.showgui.get(player)).booleanValue()) 
			{
				GL11.glTranslatef(((Integer) TeamInfo.toplefti.get(player)).intValue(), ((Integer) TeamInfo.topleftj.get(player)).intValue(), 0.0F);

				GL11.glScalef(Util.scale, Util.scale, 1.0F);

				if (TeamInfo.playerfireresist.containsKey(player)) 
				{
					int numeffects = 0;
					boolean[] effects = new boolean[5];

					effects[0] = ((Boolean) TeamInfo.playerfireresist.get(player)).booleanValue();
					effects[1] = ((Boolean) TeamInfo.playerweakness.get(player)).booleanValue();
					effects[2] = ((Boolean) TeamInfo.playerregen.get(player)).booleanValue();
					effects[3] = ((Boolean) TeamInfo.playerswift.get(player)).booleanValue();
					effects[4] = ((Boolean) TeamInfo.playerslow.get(player)).booleanValue();
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft", "textures/gui/container/inventory.png"));
					for (int i = 0; i < effects.length; i++)
					{
						if(!effects[i])
						{
							continue;
						}
						if (i == 0)
						{
							drawTexturedModalRect(-20 - numeffects * 20, 7, 126, 217, 17, 17);
						}
						else if (i == 1)
						{
							drawTexturedModalRect(-20 - numeffects * 20, 7, 90, 199, 17, 17);
						} 
						else if (i == 2)
						{
							drawTexturedModalRect(-20 - numeffects * 20, 7, 126, 198, 17, 17);
						}
						else if (i == 3)
						{
							drawTexturedModalRect(-20 - numeffects * 20, 7, 1, 198, 17, 17);
						} 
						else if (i == 4)
						{
							drawTexturedModalRect(-20 - numeffects * 20, 7, 19, 198, 17, 17);
						}
						numeffects++;
					}
				}
				
				// TODO: 2 people glitch out the heads
				if (TeamInfo.playerskin.containsKey(player)) 
				{
					if(!(Boolean)playerInfo.get(player))
					{
						location = AbstractClientPlayer.locationStevePng;
						String usn = player;
						
						location = AbstractClientPlayer.getLocationSkin(player);
						ITextureObject skinTex = AbstractClientPlayer.getDownloadImageSkin(location, player);
						Minecraft.getMinecraft().renderEngine.loadTexture(location, skinTex);						
					}	
					
					Minecraft.getMinecraft().renderEngine.bindTexture(location);
					GL11.glScalef(1.0F, 0.5F, 1.0F);
					GL11.glScalef(0.75F, 0.75F, 1.0F);
		
					drawTexturedModalRect(0, 5, 32, 64, 32, 64);
					drawTexturedModalRect(0, 5, 160, 64, 32, 64);

					GL11.glScalef(1.333333F, 1.333333F, 1.0F);
					GL11.glScalef(1.0F, 2.0F, 1.0F);
					boolean onfire = ((Boolean)TeamInfo.playeronfire.get(player)).booleanValue();
					if (onfire) 
					{
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft", "textures/particle/particles.png" ));
						drawTexturedModalRect(-7, 12, 0, 48, 16, 16);
						drawTexturedModalRect(4, 12, 0, 48, 16, 16);
						drawTexturedModalRect(15, 12, 0, 48, 16, 16);
					}
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));

					int i10 = ((Integer) TeamInfo.playersarmour.get(player)).intValue();
					int i2 = ((Integer) TeamInfo.playershealth.get(player)).intValue();
					int i3 = -1;

					int i5 = ((Integer) TeamInfo.playershunger.get(player)).intValue();
					if (TeamInfo.playersprevhealth.containsKey(player)) 
					{
						i3 = ((Integer) TeamInfo.playersprevhealth.get(player)).intValue();
					}

					boolean poison = ((Boolean) TeamInfo.playerpoison.get(player)).booleanValue();
					boolean hungerdrain = ((Boolean) TeamInfo.playerhungerdrain.get(player)).booleanValue();

					for (int l10 = 0; l10 < 10; l10++) 
					{
						int l13 = 27 + l10 * 8;
						int l12 = 16;

						if (Util.hunger)
						{
							drawTexturedModalRect(l13, 20, 16, 27, 9, 9);

							if (hungerdrain)
								l12 += 36;

							if (l10 * 2 + 1 < i5) 
							{
								drawTexturedModalRect(l13, 20, l12 + 36, 27, 9, 9);
							}
							if (l10 * 2 + 1 == i5)
							{
								drawTexturedModalRect(l13, 20, l12 + 45, 27, 9, 9);
							}
						}
						int j13 = 27 + l10 * 8;
						int l11 = 16;
						int byte5 = 0;

						if (poison)
							l11 += 36;

						int k13 = 10;

						if (i2 <= 4) 
						{
							k13 += rand.nextInt(2);
						}
						int j10 = -1;

						if (l10 == j10) 
						{
							k13 -= 2;
						}

						int k12 = 0;
						if (i3 != -1) 
						{
							if (i3 != i2) 
							{
								k12 = 1;
								notequal += 1;
							}
						}

						if (this.notequal >= 40) 
						{
							if (TeamInfo.playersprevhealth.containsKey(player))
							{
								TeamInfo.playersprevhealth.put(player, TeamInfo.playershealth.get(player));
							}
							this.notequal = 0;
						}

						drawTexturedModalRect(j13, k13, 16 + k12 * 9, 9 * byte5, 9, 9);

						if (l10 * 2 + 1 < i2)
						{
							drawTexturedModalRect(j13, k13, l11 + 36, 9 * byte5, 9, 9);
						}
						if (l10 * 2 + 1 == i2) 
						{
							drawTexturedModalRect(j13, k13, l11 + 45, 9 * byte5, 9, 9);
						}

						if (Util.armour) 
						{
							if (Util.noarmour) 
							{
								if (i10 >= 0) 
								{
									int k11 = 27 + l10 * 8;

									if (l10 * 2 + 1 < i10)
									{
										drawTexturedModalRect(k11, 0, 34, 9, 9, 9);
									}
									if (l10 * 2 + 1 == i10) 
									{
										drawTexturedModalRect(k11, 0, 25, 9, 9,	9);
									}
									if (l10 * 2 + 1 > i10)
									{
										drawTexturedModalRect(k11, 0, 16, 9, 9,	9);
									}
								}
							}
							else if (i10 > 0)
							{
								int k11 = 27 + l10 * 8;

								if (l10 * 2 + 1 < i10) 
								{
									drawTexturedModalRect(k11, 0, 34, 9, 9, 9);
								}
								if (l10 * 2 + 1 == i10) 
								{
									drawTexturedModalRect(k11, 0, 25, 9, 9, 9);
								}
								if (l10 * 2 + 1 > i10)
								{
									drawTexturedModalRect(k11, 0, 16, 9, 9, 9);
								}
							}
						}
					}
				}

				if (Util.coords)
				{
					int coords = 30;

					if (!Util.hunger)
						coords -= 10;

					drawString(Minecraft.getMinecraft().fontRenderer, "x" + TeamInfo.playerposX.get(player) + " y" + TeamInfo.playerposY.get(player) + " z" + TeamInfo.playerposZ.get(player), 27, coords, 16777215);
				}

				GL11.glScalef(1.0F / Util.scale, 1.0F / Util.scale, 1.0F);

				GL11.glTranslatef(-((Integer) TeamInfo.toplefti.get(player)).intValue(), -((Integer) TeamInfo.topleftj.get(player)).intValue(),	0.0F);
			}
		}
	}
}